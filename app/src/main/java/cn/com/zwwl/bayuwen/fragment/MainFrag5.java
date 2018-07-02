package cn.com.zwwl.bayuwen.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.ChildInfoActivity;
import cn.com.zwwl.bayuwen.activity.FeedBackActivity;
import cn.com.zwwl.bayuwen.activity.MainActivity;
import cn.com.zwwl.bayuwen.activity.MyCollectionActivity;
import cn.com.zwwl.bayuwen.activity.MyOrderActivity;
import cn.com.zwwl.bayuwen.activity.MyTuanActivity;
import cn.com.zwwl.bayuwen.activity.OurFmActivity;
import cn.com.zwwl.bayuwen.activity.RegisterAddChildActivity;
import cn.com.zwwl.bayuwen.activity.SettingActivity;
import cn.com.zwwl.bayuwen.activity.TuanCodeUseActivity;
import cn.com.zwwl.bayuwen.activity.fm.FmHistoryActivity;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.ChildModel;
import cn.com.zwwl.bayuwen.model.UserModel;
import cn.com.zwwl.bayuwen.util.Tools;

/**
 * 我的 tab
 */
public class MainFrag5 extends Fragment implements View.OnClickListener {

    private ImageView frag5Avatar;
    private TextView frag5Name;
    private LinearLayout frag5ChildLayout;
    private TextView frag5Code;
    private TextView frag5Level;

    private Activity mActivity;
    private View root;
    private UserModel userModel;

    private List<ChildModel> childModels = new ArrayList<>();// 学员数据

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main5, container, false);
        initView();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.sendEmptyMessage(0);
    }

    private void initView() {
        root.findViewById(R.id.frag5_setting).setOnClickListener(this);
        root.findViewById(R.id.frag5_level_info).setOnClickListener(this);
        root.findViewById(R.id.frag5_order1).setOnClickListener(this);
        root.findViewById(R.id.frag5_order2).setOnClickListener(this);
        root.findViewById(R.id.frag5_order3).setOnClickListener(this);
        root.findViewById(R.id.frag5_order4).setOnClickListener(this);
        root.findViewById(R.id.frag5_banji).setOnClickListener(this);
        root.findViewById(R.id.frag5_tuangou).setOnClickListener(this);
        root.findViewById(R.id.frag5_my_fm).setOnClickListener(this);
        root.findViewById(R.id.frag5_tuan_code).setOnClickListener(this);
        root.findViewById(R.id.frag5_invite).setOnClickListener(this);
        root.findViewById(R.id.frag5_feedback).setOnClickListener(this);
        frag5Avatar = root.findViewById(R.id.frag5_avatar);
        frag5Name = root.findViewById(R.id.frag5_name);
        frag5ChildLayout = root.findViewById(R.id.frag5_child_layout);
        frag5Code = root.findViewById(R.id.frag5_code);
        frag5Level = root.findViewById(R.id.frag5_level);

    }

    public void initData(UserModel userModel) {
        this.userModel = userModel;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (userModel != null) {
                        ImageLoader.display(mActivity, frag5Avatar, userModel.getPic(), R
                                .drawable.avatar_placeholder, R.drawable.avatar_placeholder);
                        frag5Code.setText(userModel.getSignCode());
                        frag5Name.setText(userModel.getName());
                    }
                    if (Tools.listNotNull(childModels)) {
                        frag5ChildLayout.removeAllViews();
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                                (LinearLayout
                                        .LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams
                                        .WRAP_CONTENT);
                        params.bottomMargin = 10;
                        params.rightMargin = 10;
                        for (int i = 0; i < childModels.size(); i++) {
                            frag5ChildLayout.addView(getChildView(childModels.get(i)), params);
                        }
                    }
                    break;
            }
        }
    };

    private View getChildView(final ChildModel childModel) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_child_usercenter,
                null);
        TextView name = view.findViewById(R.id.item_child_name);
        ImageView avat = view.findViewById(R.id.item_child_avatar);
        name.setText(childModel.getName());
        if (!TextUtils.isEmpty(childModel.getPic()))
            ImageLoader.display(mActivity, avat, childModel.getPic(), R.drawable
                    .avatar_placeholder, R.drawable.avatar_placeholder);

        view.setOnClickListener(new View
                .OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity, ChildInfoActivity.class);
                i.putExtra("ChildInfoActivity_data", childModel);
                startActivity(i);
            }
        });
        return view;
    }

    /**
     * 加载学员列表
     *
     * @param childModels
     */
    public void loadChild(List<ChildModel> childModels) {
        this.childModels.clear();
        this.childModels.addAll(childModels);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            Log.e("ssssssss", "frag5 hidde");
            handler.sendEmptyMessage(0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag5_setting:
                startActivity(new Intent(mActivity, SettingActivity.class));
                break;
            case R.id.frag5_level_info:// 功勋等级规则
                ((MainActivity) mActivity).goWeb();
                break;
            case R.id.frag5_order1:
                goMyOrder(0);
                break;
            case R.id.frag5_order2:
                goMyOrder(1);
                break;
            case R.id.frag5_order3:
                goMyOrder(2);
                break;
            case R.id.frag5_order4:
                goMyOrder(3);
                break;
            case R.id.frag5_banji://我关注的班级
                startActivity(new Intent(mActivity, MyCollectionActivity.class));
                break;
            case R.id.frag5_tuan_code:// 填写团购验证码
                startActivity(new Intent(mActivity, TuanCodeUseActivity.class));
                break;
            case R.id.frag5_invite:// 邀请好友加入大语文
                break;
            case R.id.frag5_feedback:// 反馈
                startActivity(new Intent(mActivity, FeedBackActivity.class));
                break;
            case R.id.frag5_tuangou:// 我的团购
                startActivity(new Intent(mActivity, MyTuanActivity.class));
                break;
            case R.id.frag5_my_fm:
                startActivity(new Intent(mActivity, OurFmActivity.class));
                break;
        }
    }


    private void goMyOrder(int type) {
        Intent i = new Intent(mActivity, MyOrderActivity.class);
        i.putExtra("MyOrderActivity_data", type);
        startActivity(i);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    /**
     * 带传参的构造方法
     *
     * @param ss
     * @return
     */
    public static MainFrag5 newInstance(String ss) {
//        Bundle args = new Bundle();
        MainFrag5 fragment = new MainFrag5();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
