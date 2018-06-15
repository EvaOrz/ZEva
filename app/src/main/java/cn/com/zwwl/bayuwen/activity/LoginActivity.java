package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.LoginSigninApi;
import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.UserModel;
import cn.com.zwwl.bayuwen.util.AppValue;
import cn.com.zwwl.bayuwen.view.LoginProblemPopWindow;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity implements TencentLocationListener {
    private EditText accountEdit, pwdEdit;
    private ImageView showImg;
    private boolean isShowPassword = false;// 是否显示密码
    public static int LOGIN_SUCCESS = 0;
    public static int LOGIN_CANCLE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_login);
        initView();
        needCheckLogin = false;
    }

    private void initView() {
        accountEdit = findViewById(R.id.login_account_edit);
        pwdEdit = findViewById(R.id.login_pwd_edit);
        showImg = findViewById(R.id.login_pwd_show);

        findViewById(R.id.login_back).setOnClickListener(this);
        findViewById(R.id.login_register).setOnClickListener(this);
        findViewById(R.id.login_forget).setOnClickListener(this);
        findViewById(R.id.login_problem).setOnClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);
        showImg.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.login_back:
                finish();
                break;
            case R.id.login_register:
                startActivity(new Intent(mContext, RegisterActivity.class));
                finish();
                break;
            case R.id.login_forget:// 忘记密码
                startActivity(new Intent(mContext, ForgetPwdActivity.class));
                break;
            case R.id.login_problem:// 遇到问题
                new LoginProblemPopWindow(mContext, new LoginProblemPopWindow
                        .ChooseGenderListener() {
                    @Override
                    public void choose(int gender) {

                    }
                });
                break;
            case R.id.login:// 登录
                final String username = accountEdit.getText().toString();
                String pwd = pwdEdit.getText().toString();
                // 密码登录
                if (AppValue.checkIsPhone(LoginActivity.this, username) && AppValue
                        .checkPwd(LoginActivity.this, pwd)) {
                    doLogin(LoginSigninApi.GetUserType.LOGIN, username, pwd);
                }
                break;

            case R.id.login_pwd_show:
                if (isShowPassword) {// 隐藏
                    pwdEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType
                            .TYPE_TEXT_VARIATION_PASSWORD);
//                    pwdImg.setImageResource(R.drawable.password_unshow);
                } else {//选择状态 显示明文--设置为可见的密码
                    pwdEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                    pwdImg.setImageResource(R.drawable.password_show);
                }
                isShowPassword = !isShowPassword;
                break;
        }

    }

    private void doLogin(LoginSigninApi.GetUserType userType, String pama1, String pama2) {
        showLoadingDialog(true);
        new LoginSigninApi(this, userType, pama1, pama2, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                showLoadingDialog(false);
                if (entry != null && entry instanceof UserModel) {
                    MyApplication.loginStatusChange = true;
                    setResult(LOGIN_SUCCESS);
                    finish();
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                showToast(error.getDesc());
            }
        });
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        Log.e("sssssss", s + " __ " + tencentLocation.getCity());
        if (TextUtils.isEmpty(TempDataHelper.getCurrentCity(this))) {
            TempDataHelper.setCurrentCity(mContext, tencentLocation.getCity());
        }
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }
}
