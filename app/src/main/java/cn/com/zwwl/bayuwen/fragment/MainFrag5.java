package cn.com.zwwl.bayuwen.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.BaseActivity;
import cn.com.zwwl.bayuwen.activity.FeedBackActivity;
import cn.com.zwwl.bayuwen.activity.MainActivity;
import cn.com.zwwl.bayuwen.activity.MyAccountActivity;
import cn.com.zwwl.bayuwen.activity.MyCollectionActivity;
import cn.com.zwwl.bayuwen.activity.MyOrderActivity;
import cn.com.zwwl.bayuwen.activity.MyTuanActivity;
import cn.com.zwwl.bayuwen.activity.MyYueActivity;
import cn.com.zwwl.bayuwen.activity.OurFmActivity;
import cn.com.zwwl.bayuwen.activity.ParentInfoActivity;
import cn.com.zwwl.bayuwen.activity.SettingActivity;
import cn.com.zwwl.bayuwen.activity.TuanCodeUseActivity;
import cn.com.zwwl.bayuwen.activity.WebReportActivity;
import cn.com.zwwl.bayuwen.api.order.GetYueApi;
import cn.com.zwwl.bayuwen.api.order.OrderCancleNumApi;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ChildModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.UserModel;
import cn.com.zwwl.bayuwen.util.AppValue;
import cn.com.zwwl.bayuwen.util.ShareTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.util.UmengLogUtil;

/**
 * 我的 tab
 */
public class MainFrag5 extends Fragment implements View.OnClickListener {

    private ImageView frag5Avatar;
    private TextView frag5Name;
    private LinearLayout frag5ChildLayout;
    private TextView frag5Code;
    private TextView frag5Level;
    private TextView cart_num;
    private TextView my_yue;

    private Activity mActivity;
    private View root;
    private UserModel userModel;
    private TextView lookLevelInfo;

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
        checkCartNum();
        getAsstes();
        handler.sendEmptyMessage(0);
    }

    /**
     * 检查购课单数量
     */
    private void checkCartNum() {
        new OrderCancleNumApi(mActivity, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof ErrorMsg) {
                    Message message = new Message();
                    message.what = 1;
                    message.arg1 = ((ErrorMsg) entry).getNo();
                    handler.sendMessage(message);
                }

            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }

    private void getAsstes() {
        // 获取账户余额
        new GetYueApi(mActivity, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof ErrorMsg) {
                    Message message = new Message();
                    message.what = 2;
                    message.obj = ((ErrorMsg) entry).getDesc();
                    handler.sendMessage(message);
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }

    private void initView() {
        root.findViewById(R.id.frag5_setting).setOnClickListener(this);
        lookLevelInfo = root.findViewById(R.id.frag5_level_info);
        lookLevelInfo.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        lookLevelInfo.setOnClickListener(this);
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
        root.findViewById(R.id.frag5_account).setOnClickListener(this);
        root.findViewById(R.id.frag5_yue).setOnClickListener(this);
        my_yue = root.findViewById(R.id.frag5_yue_txt);
        cart_num = root.findViewById(R.id.cart_num);
        frag5Avatar = root.findViewById(R.id.frag5_avatar);
        frag5Avatar.setOnClickListener(this);
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
                        frag5Level.setText(userModel.getLevel() + "");
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
                case 1:// 显示购课单数量
                    if (msg.arg1 > 0) {
                        cart_num.setVisibility(View.VISIBLE);
                        cart_num.setText(msg.arg1 + "");
                    } else cart_num.setVisibility(View.GONE);
                    break;
                case 2:// 显示余额
                    my_yue.setText("￥" + Tools.getTwoDecimal(Double.valueOf((String) msg.obj)/100));
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

//        view.setOnClickListener(new View
//                .OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(mActivity, ChildInfoActivity.class);
//                i.putExtra("ChildInfoActivity_data", childModel);
//                startActivity(i);
//            }
//        });
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
            checkCartNum();
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
                UmengLogUtil.logInviteClick(mActivity);
                ShareTools.doShareWeb((BaseActivity) mActivity, "邀请好友加入大语文", AppValue.inviteUrl+"?uid="+userModel.getSignCode(),
                        "邀请好友加入大语文");
                break;
            case R.id.frag5_feedback:// 反馈
                UmengLogUtil.logFeedBackClick(mActivity);
                startActivity(new Intent(mActivity, FeedBackActivity.class));
                break;
            case R.id.frag5_tuangou:// 我的团购
                startActivity(new Intent(mActivity, MyTuanActivity.class));
                break;
            case R.id.frag5_my_fm:
                startActivity(new Intent(mActivity, OurFmActivity.class));
                break;
            case R.id.frag5_account:// 我的账户
                Intent i = new Intent(mActivity, MyAccountActivity.class);
                startActivity(i);
                break;
            case R.id.frag5_avatar://会员资料
                startActivity(new Intent(mActivity, ParentInfoActivity.class));
                break;
            case R.id.frag5_yue:// 我的余额
                startActivity(new Intent(mActivity, MyYueActivity.class));
                break;
        }
    }


    private void goMyOrder(int type) {
        UmengLogUtil.logOrderBtnClick(mActivity, type);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
    }
}
