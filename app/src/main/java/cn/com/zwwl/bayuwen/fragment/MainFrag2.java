package cn.com.zwwl.bayuwen.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.CourseDetailActivity;
import cn.com.zwwl.bayuwen.activity.TeacherDetailActivity;
import cn.com.zwwl.bayuwen.adapter.EleCourseGridAdapter;
import cn.com.zwwl.bayuwen.model.EleCourseData;
import cn.com.zwwl.bayuwen.model.EleCourseModel;
import cn.com.zwwl.bayuwen.widget.BannerView;

/**
 * 选课
 */
public class MainFrag2 extends Fragment
        implements View.OnClickListener, ObservableScrollViewCallbacks {
    private Activity mActivity;
    private BannerView bannerView;
    private View root;
    private View mImageView;
    private RelativeLayout mToolbarView;
    private ObservableScrollView mScrollView;
    private GridView mGridView;
    private TextView teacherPraiseTv;
    private TextView academicAdvisoPraiseTv;
    private TextView assistantPraiseTv;

    private int mParallaxImageHeight;
    private EleCourseGridAdapter gridAdapter;

    private List<EleCourseModel> mItemList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main2, container, false);
        initView();
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
    public static MainFrag2 newInstance(String ss) {
//        Bundle args = new Bundle();
        MainFrag2 fragment = new MainFrag2();
//        fragment.setArguments(args);
        return fragment;
    }

    private void initView() {
        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);
        mImageView = root.findViewById(R.id.image);
        mToolbarView = root.findViewById(R.id.main2_toolbar);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.transparent)));

        mGridView = root.findViewById(R.id.gridView);

        mScrollView = root.findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);
        mScrollView.setZoomView(mImageView);

        View view1 = root.findViewById(R.id.teacher_layout);
        View view2 = root.findViewById(R.id.academicAdviso_layout);
        View view3 = root.findViewById(R.id.assistant_layout);
        teacherPraiseTv = view1.findViewById(R.id.praiseTv);
        academicAdvisoPraiseTv = view2.findViewById(R.id.praiseTv);
        assistantPraiseTv = view3.findViewById(R.id.praiseTv);
        teacherPraiseTv.setText("教师点赞排行榜");
        academicAdvisoPraiseTv.setText("学业顾问点赞排行榜");
        assistantPraiseTv.setText("校区助手点赞排行榜");

        gridAdapter = new EleCourseGridAdapter(getActivity(), mItemList);
        mGridView.setAdapter(gridAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(mActivity, CourseDetailActivity.class);
                startActivity(intent);
            }
        });
        view1.findViewById(R.id.praise_re).setOnClickListener(this);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.body_gray);
        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight * 1.4f);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.praise_re:
                Intent intent = new Intent();
                intent.setClass(mActivity, TeacherDetailActivity.class);
                startActivity(intent);
                break;
        }
    }

    private List<EleCourseModel> setData() {
        for (int i = 0; i < EleCourseData.names.length; i++) {
            EleCourseModel courseModel = new EleCourseModel();
            courseModel.setId(String.valueOf(i + 1));
            courseModel.setName(EleCourseData.names[i]);
            courseModel.setUrl(EleCourseData.urls[i]);
            mItemList.add(courseModel);
        }
        return mItemList;
    }
}
