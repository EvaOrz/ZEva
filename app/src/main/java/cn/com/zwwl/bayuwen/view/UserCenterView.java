package cn.com.zwwl.bayuwen.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.fm.AlbumListActivity;
import cn.com.zwwl.bayuwen.activity.fm.FmChangeUserInfoActivity;
import cn.com.zwwl.bayuwen.activity.MainActivity;
import cn.com.zwwl.bayuwen.activity.fm.FmHistoryActivity;
import cn.com.zwwl.bayuwen.activity.fm.FmLoginActivity;
import cn.com.zwwl.bayuwen.activity.WebActivity;
import cn.com.zwwl.bayuwen.api.UserApi;
import cn.com.zwwl.bayuwen.db.UserDataHelper;
import cn.com.zwwl.bayuwen.model.UserModel;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;

public class UserCenterView implements View.OnClickListener {
    private Context context;
    private View view;
    private ImageView avatar;
    private TextView name, logout;
    private UserModel userModel;

    public UserCenterView(Context context) {
        this.context = context;
        initView();
        loadData();
    }

    private void initView() {
        view = LayoutInflater.from(context).inflate(R.layout.view_user_center, null);
        avatar = view.findViewById(R.id.user_avatar);
        name = view.findViewById(R.id.user_title);
        avatar.setOnClickListener(this);

        view.findViewById(R.id.test).setOnClickListener(this);
        view.findViewById(R.id.user_info_layout).setOnClickListener(this);
        view.findViewById(R.id.user_history_layout).setOnClickListener(this);
        view.findViewById(R.id.user_collect_layout).setOnClickListener(this);
        view.findViewById(R.id.user_about_layout).setOnClickListener(this);
        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(this);

    }

    public void loadData() {
        userModel = UserDataHelper.getUserLoginInfo(context);
        if (userModel == null) {
            avatar.setImageResource(R.drawable.avatar_placeholder);
            name.setText(R.string.un_login);
            logout.setVisibility(View.GONE);
        } else {
            if (!TextUtils.isEmpty(userModel.getPic()))
                Glide.with(context).load(userModel.getPic()).into(avatar);
            name.setText(userModel.getName());
            logout.setVisibility(View.VISIBLE);
        }

    }

    public void getUserinfo() {
        new UserApi(context, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                // 获取用户信息，不需要监听返回
                handler.sendEmptyMessage(0);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_avatar:
                if (userModel == null) goLogin();
                break;
            case R.id.user_info_layout:// 修改资料
                context.startActivity(new Intent(context, FmChangeUserInfoActivity.class));
                break;
            case R.id.user_history_layout:// 历史
                context.startActivity(new Intent(context, FmHistoryActivity.class));
                break;
            case R.id.user_collect_layout:// 我的收藏
                goListActivity(1);
                break;
            case R.id.user_about_layout:// 关于
                Intent i = new Intent(context, WebActivity.class);
                i.putExtra("WebActivity_data", "http://www.zhugexuetang.com");
                context.startActivity(i);
                break;
            case R.id.logout:
                UserDataHelper.clearLoginInfo(context);
                loadData();
                break;
            case R.id.test:
                context.startActivity(new Intent(context, MainActivity.class));
                break;

        }
    }

    private void goListActivity(int type) {
        Intent i = new Intent(context, AlbumListActivity.class);
        i.putExtra("AlbumListActivity_type", type);
        context.startActivity(i);
    }

    private void goLogin() {
        context.startActivity(new Intent(context, FmLoginActivity.class));
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    loadData();
                    break;

                case 1:
                    break;
            }
        }
    };

    public View getView() {
        return view;
    }


}
