package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.ChildInfoApi;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.model.ChildModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.CircleImageView;
import cn.com.zwwl.bayuwen.widget.RoundAngleLayout;

/**
 * 我的账户页面
 */
public class MyAccountActivity extends BaseActivity {

    private LinearLayout contain;
    private List<ChildModel> childModels = new ArrayList<>();// 学员数据

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        findViewById(R.id.account_back).setOnClickListener(this);
        contain = findViewById(R.id.account_container);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.account_back:
                finish();
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    contain.removeAllViews();
                    for (int i = 0; i < childModels.size(); i++) {
                        contain.addView(getChildView(childModels.get(i), i));
                    }
                    break;
            }

        }
    };

    /**
     * 获取当前用户下的所有学员信息
     */
    @Override
    protected void initData() {

        new ChildInfoApi(mContext, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                if (Tools.listNotNull(list)) {
                    childModels.clear();
                    childModels.addAll(list);
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                if (error != null)
                    showToast(error.getDesc());

            }
        });
    }

    private View getChildView(final ChildModel childModel, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_child_account, null);
        CircleImageView avatar = view.findViewById(R.id.child_account_img);
        RoundAngleLayout bg = view.findViewById(R.id.child_account_bg);
        TextView name = view.findViewById(R.id.child_account_name);
        TextView grade = view.findViewById(R.id.child_account_grade);
        TextView no = view.findViewById(R.id.child_account_no);
        TextView location = view.findViewById(R.id.child_account_location);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MyApplication.width -
                80, (MyApplication.width - 80) / 2);
        params.setMargins(40, 40, 40, 0);
        bg.setLayoutParams(params);
        if (i == 0)
            bg.setBackground(mContext.getResources().getDrawable(R.drawable.child_card_bg1));
        else if (i == 1)
            bg.setBackground(mContext.getResources().getDrawable(R.drawable.child_card_bg2));
        else if (i == 2)
            bg.setBackground(mContext.getResources().getDrawable(R.drawable.child_card_bg3));

        ImageLoader.display(mContext, avatar, childModel.getPic(), R.drawable.avatar_placeholder,
                R.drawable.avatar_placeholder);
        name.setText(childModel.getName());
        grade.setText(childModel.getGrade());
        no.setText("NO：" + childModel.getNo());
        location.setText(childModel.getCity());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ChildInfoActivity.class);
                i.putExtra("ChildInfoActivity_data", childModel);
                startActivity(i);
            }
        });
        return view;
    }
}
