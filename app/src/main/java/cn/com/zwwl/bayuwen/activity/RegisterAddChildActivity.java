package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.ChildInfoApi;
import cn.com.zwwl.bayuwen.db.UserDataHelper;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ChildModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.view.NianjiPopWindow;

/**
 * 注册添加学员页面
 */
public class RegisterAddChildActivity extends BaseActivity {
    private RadioButton male, female;
    private ChildModel childModel;
    private EditText editText;
    private TextView nianji;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_register_addchild);
        initView();
        childModel = new ChildModel();
    }

    private void initView() {
        editText = findViewById(R.id.register_add_name);
        nianji = findViewById(R.id.register_add_nianji);
        male = findViewById(R.id.register_add_male);
        female = findViewById(R.id.register_add_female);
        nianji.setOnClickListener(this);
        findViewById(R.id.register_a_back).setOnClickListener(this);
        findViewById(R.id.register_add_bt).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.register_a_back:
                finish();
                break;
            case R.id.register_add_bt:
                String name = editText.getText().toString();
                final String grade = nianji.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    showToast("请填写学员姓名");
                } else {
                    childModel.setName(name);
                    childModel.setTel(UserDataHelper.getUserLoginInfo(mContext).getTel());
                    childModel.setGender(male.isChecked() ? 1 : 0);//0女1男2未知
                    childModel.setIsdefault("1");
                    childModel.setGrade(grade);
                    commit();
                }
                break;
            case R.id.register_add_nianji:
                hideJianpan();
                new NianjiPopWindow(mContext, new NianjiPopWindow.MyNianjiPickListener() {
                    @Override
                    public void onNianjiPick(String string) {
                        childModel.setGrade(string);
                        handler.sendEmptyMessage(0);
                    }
                });
                break;
        }

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:// 年级选择
                    nianji.setText(childModel.getGrade());
                    break;

            }
        }
    };

    /**
     * 提交
     */
    private void commit() {
        new ChildInfoApi(this, childModel, false, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                showLoadingDialog(false);
                if (entry != null && entry instanceof ChildModel) {
                    showToast("添加学员成功");
                    MyApplication.loginStatusChange = true;
                    finish();
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error != null)
                    showToast(error.getDesc());
            }
        });
    }

}
