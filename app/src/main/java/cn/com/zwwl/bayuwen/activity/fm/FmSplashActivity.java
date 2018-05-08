package cn.com.zwwl.bayuwen.activity.fm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.BaseActivity;
import cn.com.zwwl.bayuwen.activity.MainActivity;

/**
 *
 */
public class FmSplashActivity extends BaseActivity {
    /**
     * splash停留时间
     **/
    public static int SPLASH_DELAY_TIME = 1000;
    private String[] needPermissions = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.CAMERA};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            askPermission(needPermissions, 101);
        } else {
            gotoMainActivity();
        }
    }

    public void gotoMainActivity() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(FmSplashActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
                overridePendingTransition(R.anim.alpha_out_3s, R.anim.alpha_out_3s);
            }
        }, SPLASH_DELAY_TIME);

    }


    @Override
    protected void initData() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101:
                for (int i = 0; i < permissions.length; i++) {

                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Log.e("ppppppppphas", permissions[i]);
                    } else {
                        Log.e("pppppppppno", permissions[i]);
                    }
                }
                gotoMainActivity();


                break;
        }

    }
}
