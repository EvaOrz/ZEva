package cn.com.zwwl.bayuwen.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.widget.BannerView;

/**
 *
 */
public class MainFrag1 extends Fragment {

    private Activity mActivity;
    private BannerView bannerView;
    private TextView notificationTv;
    private View root;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main1, container, false);
        initView();
        return root;
    }

    private void initView() {
        notificationTv = root.findViewById(R.id.main_notification);
        notificationTv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        notificationTv.setSingleLine(true);
        notificationTv.setSelected(true);
        notificationTv.setFocusable(true);
        notificationTv.setFocusableInTouchMode(true);
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
    public static MainFrag1 newInstance(String ss) {
//        Bundle args = new Bundle();
        MainFrag1 fragment = new MainFrag1();
//        fragment.setArguments(args);
        return fragment;
    }
}
