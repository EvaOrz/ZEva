package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.RequestMobileCodeCallback;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.LoginSigninApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.BayuwenTools;
import cn.com.zwwl.bayuwen.util.SmsTools;
import cn.com.zwwl.bayuwen.view.AddressPopWindow;

/**
 * 注册
 */
public class RegisterActivity extends BaseActivity {

    private TextView getVerify;
    private ImageView pwdShow;
    private TextView cityTv;
    private EditText accountEdit, pwdEdit, verifyEdit;
    private boolean canGetVerify = true;// 是否可获取验证码
    private boolean isShowPassword = false;// 是否显示密码
    private String cityTxt = "北京";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_register);
        initView();
    }

    @Override
    protected void initData() {

    }

    private void initView() {
        accountEdit = findViewById(R.id.register_account_edit);
        pwdEdit = findViewById(R.id.register_pwd_edit);
        verifyEdit = findViewById(R.id.register_verify_edit);
        getVerify = findViewById(R.id.register_get_verify);
        pwdShow = findViewById(R.id.register_pwd_show);
        cityTv = findViewById(R.id.register_city_t);

        pwdShow.setOnClickListener(this);
        findViewById(R.id.register_back).setOnClickListener(this);
        findViewById(R.id.register_login).setOnClickListener(this);
        findViewById(R.id.register_get_verify).setOnClickListener(this);
        findViewById(R.id.register_bt).setOnClickListener(this);
        findViewById(R.id.register_city_l).setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    cityTv.setText(cityTxt);
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        final String phone = accountEdit.getText().toString();
        switch (view.getId()) {
            case R.id.register_back:
                finish();
                break;
            case R.id.register_login:// 已有账号
                startActivity(new Intent(mContext, LoginActivity.class));
                finish();
                break;
            case R.id.register_get_verify:

                if (BayuwenTools.checkIsPhone(this, phone)) doGetVerifyCode(phone);
                break;
            case R.id.register_bt:
                final String pwd = pwdEdit.getText().toString();
                final String verifycode = verifyEdit.getText().toString();
                if (BayuwenTools.checkIsPhone(this, phone) && BayuwenTools.checkPwd(this, pwd) && BayuwenTools.checkCode(this, verifycode)) {
                    doRegister(phone, pwd, verifycode);
                }
                break;
            case R.id.register_pwd_show:
                if (isShowPassword) {// 隐藏
                    pwdEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                    pwdImg.setImageResource(R.drawable.password_unshow);
                } else {//选择状态 显示明文--设置为可见的密码
                    pwdEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                    pwdImg.setImageResource(R.drawable.password_show);
                }
                isShowPassword = !isShowPassword;
                break;
            case R.id.register_city_l:// 选择城市
                new AddressPopWindow(mContext, new AddressPopWindow.OnAddressCListener() {
                    @Override
                    public void onClick(String province, String city) {
                        cityTxt = city;
                        handler.sendEmptyMessage(0);
                    }
                });
                break;

        }
    }

    private void doRegister(String phone, String pwd, String code) {
        showLoadingDialog(true);
        new LoginSigninApi(this, phone, pwd, code, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                showLoadingDialog(false);
                if (entry != null && entry instanceof ErrorMsg) {
                    MyApplication.loginStatusChange = true;
                    if (((ErrorMsg) entry).getNo() == 0) {
                        finish();
                    } else {
                        showToast(((ErrorMsg) entry).getDesc());
                    }
                }
            }
        });
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
}