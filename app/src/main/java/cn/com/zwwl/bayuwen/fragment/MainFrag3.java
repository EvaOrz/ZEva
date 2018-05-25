package cn.com.zwwl.bayuwen.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CompleteCourseAdapter;
import cn.com.zwwl.bayuwen.adapter.CoursePageAdapter;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.view.PagerSlidingTabStrip;
import cn.com.zwwl.bayuwen.widget.BannerView;
import cn.com.zwwl.bayuwen.widget.decoration.DividerItemDecoration;

import static cn.com.zwwl.bayuwen.MyApplication.mContext;

/**
 *
 */
public class MainFrag3 extends Fragment {

    private Activity mActivity;
    private BannerView bannerView;
    private PagerSlidingTabStrip tabLayout;
    private ViewPager mViewPager;
    private RecyclerView recyclerView;
    private CompleteCourseAdapter adapter;
    private CoursePageAdapter mViewPagerAdapter;
    private List<Fragment> list = new ArrayList<>();
    private List<KeModel> mItemList = new ArrayList<>();
    private List<String> mItemTitleList = new ArrayList<>();

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
        tabLayout = view.findViewById(R.id.tablayout);
        mItemTitleList.add("顾问对学生的评价");
        mItemTitleList.add("顾问对家长的评价");
        FgEvaluate evaluate1 = FgEvaluate.newInstance("ASJDFOISDJFIJFDODJOIJASF");
        list.add(evaluate1);
        FgEvaluate evaluate2 = FgEvaluate.newInstance("ASJDFOISDJFIJFDasdfdsODJOIJASF");
        list.add(evaluate2);
        mViewPager = view.findViewById(R.id.mViewPager);
        mViewPagerAdapter = new CoursePageAdapter(getFragmentManager(), list, mItemTitleList);
        mViewPager.setAdapter(mViewPagerAdapter);
        tabLayout.setViewPager(mViewPager);
        for (int i = 0; i < 6; i++) {
            KeModel model = new KeModel();
            mItemList.add(model);
        }

        recyclerView = view.findViewById(R.id.completed_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources(), R.color.white, R.dimen.dp_5, OrientationHelper.VERTICAL));
        adapter = new CompleteCourseAdapter(getActivity(), mItemList);
        recyclerView.setAdapter(adapter);

    }

}
