package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.RequestMobileCodeCallback;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.ActionApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.AppValue;
import cn.com.zwwl.bayuwen.util.SmsTools;
import cn.com.zwwl.bayuwen.util.UmengLogUtil;

public class ForgetPwdActivity extends BaseActivity {
    private EditText accountEv, codeEv, pwdEv;
    private TextView getVerify;
    private boolean canGetVerify = true;// 是否可获取验证码
    private boolean isShowPassword = false;// 是否显示密码


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd_new);
        initView();
        needCheckLogin = false;
    }

    private void initView() {
        accountEv = findViewById(R.id.forget_account);
        codeEv = findViewById(R.id.forget_verify);
        pwdEv = findViewById(R.id.forget_pwd);
        getVerify = findViewById(R.id.forget_get_verify);
        getVerify.setOnClickListener(this);
        findViewById(R.id.forget_pwd_show).setOnClickListener(this);
        findViewById(R.id.forget_back).setOnClickListener(this);
        findViewById(R.id.forget_sure).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        final String phone = accountEv.getText().toString();
        switch (view.getId()) {
            case R.id.forget_back:
                finish();
                break;
            case R.id.forget_get_verify:
                if (AppValue.checkIsPhone(this, phone)) doGetVerifyCode(phone);
                break;

            case R.id.forget_sure:
                UmengLogUtil.logForgetPwd(mContext);
                final String pwd = pwdEv.getText().toString();
                final String verifycode = codeEv.getText().toString();
                if (AppValue.checkIsPhone(this, phone) && AppValue.checkPwd(this, pwd) &&
                        AppValue.checkCode(this, verifycode)) {
                    doReset(phone, pwd, verifycode);
                }
                break;
            case R.id.forget_pwd_show:
                if (isShowPassword) {// 隐藏
                    pwdEv.setInputType(InputType.TYPE_CLASS_TEXT | InputType
                            .TYPE_TEXT_VARIATION_PASSWORD);
//                    pwdImg.setImageResource(R.drawable.password_unshow);
                } else {//选择状态 显示明文--设置为可见的密码
                    pwdEv.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                    pwdImg.setImageResource(R.drawable.password_show);
                }
                isShowPassword = !isShowPassword;
                break;
        }

    }


    private void doReset(String phone, String password, String code) {
        showLoadingDialog(true);
        new ActionApi(mContext, phone, password, code, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {

            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error != null)
                    showToast(TextUtils.isEmpty(error.getDesc()) ? mContext
                            .getResources()
                            .getString(R.string.forget_pwd_faild) : error
                            .getDesc());
                else {
                    showToast(R.string.forget_pwd_success);
                    finish();
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
                        showToast(e.getMessage());
                    }
                }
            });

        }
    }

    @Override
    protected void initData() {

    }
}

