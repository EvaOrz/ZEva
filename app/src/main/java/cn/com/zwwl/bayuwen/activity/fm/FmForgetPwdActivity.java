package cn.com.zwwl.bayuwen.activity.fm;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.RequestMobileCodeCallback;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.BaseActivity;
import cn.com.zwwl.bayuwen.api.ActionApi;
import cn.com.zwwl.bayuwen.util.BayuwenTools;
import cn.com.zwwl.bayuwen.util.SmsTools;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;

/**
 * 忘记密码页面
 */
public class FmForgetPwdActivity extends BaseActivity {
    private LinearLayout firstStep, nextStep;
    private EditText accountEdit, verifyEdit, pwdEdit;
    private TextView next, commit, getVerify, phoneTip;
    private boolean canGetVerify = true;// 是否可获取验证码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        initView();
    }

    @Override
    protected void initData() {
    }

    private void initView() {
        findViewById(R.id.forget_back).setOnClickListener(this);
        firstStep = findViewById(R.id.forget_first);
        nextStep = findViewById(R.id.forget_second);
        accountEdit = findViewById(R.id.forget_account_edit);
        verifyEdit = findViewById(R.id.forget_verify_edit);
        pwdEdit = findViewById(R.id.forget_pwd_edit);
        next = findViewById(R.id.forget_next);
        commit = findViewById(R.id.forget_commit);
        getVerify = findViewById(R.id.forget_get_verify);
        phoneTip = findViewById(R.id.forget_phone_tip);
        next.setOnClickListener(this);
        getVerify.setOnClickListener(this);
        commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String phone = accountEdit.getText().toString();
        switch (view.getId()) {
            case R.id.forget_back:
                finish();
                break;

            case R.id.forget_get_verify:

                if (BayuwenTools.checkIsPhone(this, phone)) doGetVerifyCode(phone);
                break;
            case R.id.forget_next:// 下一步
                phoneTip.setText(String.format(mContext.getResources().getString(R.string.forget_pwd_tip2), phone));
                firstStep.setVisibility(View.GONE);
                nextStep.setVisibility(View.VISIBLE);
                break;
            case R.id.forget_commit:// 提交
                String pwd = pwdEdit.getText().toString();
                final String code = verifyEdit.getText().toString();
                if (BayuwenTools.checkIsPhone(this, phone) && BayuwenTools.checkPwd(this, pwd)) {
                    showLoadingDialog(true);
                    new ActionApi(this, phone, pwd, code, new FetchEntryListener() {
                        @Override
                        public void setData(Entry entry) {
                            showLoadingDialog(false);
                            if (entry == null) {
                                showToast(R.string.forget_pwd_success);
                                finish();
                            } else
                                showToast(R.string.forget_pwd_faild);
                        }
                    });
                }
                break;


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
}
