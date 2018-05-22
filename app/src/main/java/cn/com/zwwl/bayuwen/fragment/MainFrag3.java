package cn.com.zwwl.bayuwen.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.widget.BannerView;

/**
 *
 */
public class MainFrag3 extends Fragment {

    private Activity mActivity;
    private BannerView bannerView;
    private RelativeLayout title_layout;
    private TabLayout tabLayout;
    private ViewPager viewPager;

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
        View root = inflater.inflate(R.layout.fragment_main3, container, false);
        initView(root);
        return root;
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
    public static MainFrag3 newInstance(String ss) {
        MainFrag3 fragment = new MainFrag3();
        return fragment;
    }

    private void initView(View view) {
        View v = view.findViewById(R.id.main3_title);
        title_layout = v.findViewById(R.id.main2_toolbar);
        title_layout.setBackgroundColor(getActivity().getResources().getColor(R.color.body_gray));

        tabLayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.viewPager);
//        viewPager.setOffscreenPageLimit(1);
    }
}
