package cn.com.zwwl.bayuwen.activity.fm;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.RequestMobileCodeCallback;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.BaseActivity;
import cn.com.zwwl.bayuwen.api.LoginSigninApi;
import cn.com.zwwl.bayuwen.model.UserModel;
import cn.com.zwwl.bayuwen.util.BayuwenTools;
import cn.com.zwwl.bayuwen.util.SmsTools;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 注册页面
 */
public class FmRegisterActivity extends BaseActivity {
    private TextView getVerify;
    private EditText accountEdit, pwdEdit, verifyEdit;
    private boolean canGetVerify = true;// 是否可获取验证码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

        findViewById(R.id.register_back).setOnClickListener(this);
        findViewById(R.id.register_get_verify).setOnClickListener(this);
        findViewById(R.id.register_bt).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        final String phone = accountEdit.getText().toString();
        switch (view.getId()) {
            case R.id.register_back:
                finish();
                break;
            case R.id.register_get_verify:

                if (BayuwenTools.checkIsPhone(this, phone)) doGetVerifyCode(phone);
                break;
            case R.id.register_bt:
                final String pwd = pwdEdit.getText().toString();
                final String verifycode = verifyEdit.getText().toString();
                if (BayuwenTools.checkIsPhone(this, phone) && BayuwenTools.checkPwd(this, pwd) &&
                        BayuwenTools.checkCode(this, verifycode)) {
                    doRegister(phone, pwd, verifycode);
                }
                break;


        }
    }

    private void doRegister(String phone, String pwd, String code) {
        showLoadingDialog(true);
        new LoginSigninApi(this, phone, pwd, code, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                showLoadingDialog(false);
                if (entry != null && entry instanceof UserModel) {
                    MyApplication.loginStatusChange = true;
                    finish();
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                showToast(error.getDesc());
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
