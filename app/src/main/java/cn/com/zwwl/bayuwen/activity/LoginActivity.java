package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.ChildInfoApi;
import cn.com.zwwl.bayuwen.api.LoginSigninApi;
import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.UserModel;
import cn.com.zwwl.bayuwen.util.AppValue;
import cn.com.zwwl.bayuwen.util.MyActivityManager;
import cn.com.zwwl.bayuwen.util.Tools;
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
    private long lastClickTime = 0;

    private TencentLocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_login);
        initView();
        registerTencentLocation();
        needCheckLogin = false;
    }

    private void registerTencentLocation() {
        mLocationManager = TencentLocationManager.getInstance(this);
        // 设置坐标系为 gcj-02, 缺省坐标为 gcj-02, 所以通常不必进行如下调用
        mLocationManager.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_GCJ02);
        // 创建定位请求
        TencentLocationRequest request = TencentLocationRequest.create()
                .setInterval(60 * 1000) // 设置定位周期
                .setAllowGPS(true)  //当为false时，设置不启动GPS。默认启动
                .setQQ("10001")
                .setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA); // 设置定位level

        // 开始定位
        mLocationManager.requestLocationUpdates(request, this, getMainLooper());

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
    protected void onStop() {
        super.onStop();
        stopLocation();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.login_back:
                MyActivityManager.getInstance().exit();
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
                    showImg.setImageResource(R.mipmap.icon_hide_psd);
                } else {//选择状态 显示明文--设置为可见的密码
                    pwdEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showImg.setImageResource(R.mipmap.icon_view_psd);
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
                if (entry != null && entry instanceof UserModel) {
                    initChildDta();
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                showToast(error.getDesc());
            }
        });
    }

    private void afterLogin() {
        MyApplication.loginStatusChange = true;
        setResult(LOGIN_SUCCESS);
        finish();
    }


    /**
     * 获取当前用户下的所有学员信息,没有的话需要去添加
     */
    private void initChildDta() {
        new ChildInfoApi(mContext, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                showLoadingDialog(false);
                if (Tools.listNotNull(list)) {
                    afterLogin();
                } else startActivity(new Intent(mContext, RegisterAddChildActivity.class));
            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                showToast(error.getDesc());
            }
        });
    }

    // 响应点击"停止"
    public void stopLocation() {
        mLocationManager.removeUpdates(this);
        Log.e("tencent location", "停止定位");
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int error, String reason) {
        Log.e("tencent location", reason + " __ " + tencentLocation.getCity());
        // 定位成功
        if (error == TencentLocation.ERROR_OK) {
            if (TextUtils.isEmpty(TempDataHelper.getCurrentCity(this))) {
                TempDataHelper.setCurrentCity(mContext, tencentLocation.getCity());
            }
        } else {// 定位失败
            if (TextUtils.isEmpty(TempDataHelper.getCurrentCity(this))) {
                TempDataHelper.setCurrentCity(mContext, "北京市");
                showToast("定位失败，设置当前默认城市为北京市");
            }
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
            MyActivityManager.getInstance().exit();
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }
}
