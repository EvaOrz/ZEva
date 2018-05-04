package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.RequestMobileCodeCallback;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.LoginSigninApi;

import cn.com.zwwl.bayuwen.db.DataHelper;
import cn.com.zwwl.bayuwen.util.BayuwenTools;
import cn.com.zwwl.bayuwen.util.SmsTools;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 登录页面
 * Created by Eva. on 2018/3/23.
 */

public class LoginActivity extends BaseActivity {

    private TextView loginAccount, loginFast, forgetFwd, getVerify;
    private View accountLine, fastLine;
    private long lastClickTime = 0;
    private LinearLayout accountLayout, fastLayout;
    private EditText accountEdit, pwdEdit, verifyEdit;
    private boolean canGetVerify = true;// 是否可获取验证码
    public static int LOGIN_SUCCESS = 0;
    public static int LOGIN_CANCLE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DataHelper.getUserLoginInfo(this) != null) finish();
    }

    @Override
    protected void initData() {
    }

    private void initView() {
        loginAccount = findViewById(R.id.login_account);
        loginFast = findViewById(R.id.login_fast);
        accountLine = findViewById(R.id.login_account_line);
        fastLine = findViewById(R.id.login_fast_line);
        accountLayout = findViewById(R.id.login_account_layout);
        fastLayout = findViewById(R.id.login_fast_layout);
        forgetFwd = findViewById(R.id.forget_pwd);
        accountEdit = findViewById(R.id.login_account_edit);
        pwdEdit = findViewById(R.id.login_pwd_edit);
        verifyEdit = findViewById(R.id.login_verify_edit);
        getVerify = findViewById(R.id.login_get_verify);

//        findViewById(R.id.login_back).setOnClickListener(this);
        findViewById(R.id.login_register).setOnClickListener(this);
        getVerify.setOnClickListener(this);
        findViewById(R.id.login_bt).setOnClickListener(this);
        findViewById(R.id.forget_pwd).setOnClickListener(this);
        loginAccount.setOnClickListener(this);
        loginFast.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 没有退出登录
            case R.id.login_back:
                finish();
                break;
            case R.id.login_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.login_get_verify:
                String phone = accountEdit.getText().toString();
                if (BayuwenTools.checkIsPhone(this, phone)) doGetVerifyCode(phone);
                break;
            case R.id.login_bt:
                final String username = accountEdit.getText().toString();
                String pwd = pwdEdit.getText().toString();
                final String code = verifyEdit.getText().toString();
                if (accountLayout.isShown()) {
                    // 密码登录
                    if (BayuwenTools.checkIsPhone(LoginActivity.this, username) && BayuwenTools.checkPwd(LoginActivity.this, pwd)) {
                        doLogin(LoginSigninApi.GetUserType.LOGIN, username, pwd);
                    }
                } else if (fastLayout.isShown()) {// 快捷登录，需要验证验证码
                    if (BayuwenTools.checkIsPhone(LoginActivity.this, username) && BayuwenTools.checkCode(LoginActivity.this, code)) {

                        doLogin(LoginSigninApi.GetUserType.FAST_LOGIN, username, code);


                    }
                }
                break;
            case R.id.forget_pwd:
                startActivity(new Intent(LoginActivity.this, ForgetPwdActivity.class));
                break;
            case R.id.login_account:
                changeTab(0);
                break;
            case R.id.login_fast:
                changeTab(1);
                break;
        }
    }


    private void doLogin(LoginSigninApi.GetUserType userType, String pama1, String pama2) {
        showLoadingDialog(true);
        new LoginSigninApi(this, userType, pama1, pama2, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                showLoadingDialog(false);
                if (entry != null && entry instanceof ErrorMsg) {
                    MyApplication.loginStatusChange = true;
                    if (((ErrorMsg) entry).getNo() == 0) {
                        setResult(LOGIN_SUCCESS);
                        finish();
                    } else {
                        showToast(((ErrorMsg) entry).getDesc());
                    }
                }
            }
        });
    }


    private void changeTab(int type) {
        if (type == 0) {// 账号密码登录
            loginAccount.setTextColor(getResources().getColor(R.color.oringe));
            loginFast.setTextColor(getResources().getColor(R.color.black));
            accountLine.setVisibility(View.VISIBLE);
            fastLine.setVisibility(View.INVISIBLE);
            accountLayout.setVisibility(View.VISIBLE);
            fastLayout.setVisibility(View.GONE);
            forgetFwd.setVisibility(View.VISIBLE);
        } else {// 快捷免密登录
            loginAccount.setTextColor(getResources().getColor(R.color.black));
            loginFast.setTextColor(getResources().getColor(R.color.oringe));
            accountLine.setVisibility(View.INVISIBLE);
            fastLine.setVisibility(View.VISIBLE);
            accountLayout.setVisibility(View.GONE);
            fastLayout.setVisibility(View.VISIBLE);
            forgetFwd.setVisibility(View.GONE);
        }
    }

    /**
     * 获取手机验证码
     */
    protected void doGetVerifyCode(final String phone) {
        if (canGetVerify) {
            SmsTools.sendLoginSms(phone, new RequestMobileCodeCallback() {
                @Override
                public void done(AVException e) {
                    if (null == e) {
                        /* 请求成功 */
                        Log.e("smsssssss", phone);
                        canGetVerify = false;
                        // 开启倒计时器
                        new CountDownTimer(60000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                getVerify.setText(millisUntilFinished / 1000 + "s重新获取");
                            }

                            public void onFinish() {
                                getVerify.setText(R.string.get_verifycode);
                                canGetVerify = true;
                            }
                        }.start();
                    } else {
                        /* 请求失败 */
                        Log.e("smsssssss", e.getMessage());
                        showToast(e.getMessage());
                    }
                }
            });

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            long clickTime = System.currentTimeMillis() / 1000;
            if (clickTime - lastClickTime >= 3) {
                lastClickTime = clickTime;
                showToast(R.string.exit_app);
                return true;
            }
        }
        setResult(LOGIN_CANCLE);
        return super.onKeyDown(keyCode, event);
    }


}
