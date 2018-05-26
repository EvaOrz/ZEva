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
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.MainActivity;
import cn.com.zwwl.bayuwen.activity.SearchCourseActivity;
import cn.com.zwwl.bayuwen.adapter.DianzanAdapter;
import cn.com.zwwl.bayuwen.adapter.EleCourseGridAdapter;
import cn.com.zwwl.bayuwen.api.EleCourseListApi;
import cn.com.zwwl.bayuwen.api.EleCourseListApi.*;
import cn.com.zwwl.bayuwen.api.PraiseListApi;
import cn.com.zwwl.bayuwen.api.PraiseListApi.*;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.widget.BannerView;
import cn.com.zwwl.bayuwen.widget.NoScrollListView;
import cn.com.zwwl.bayuwen.widget.StopLinearLayoutManager;
import cn.com.zwwl.bayuwen.widget.decoration.DividerItemDecoration;

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

    private NoScrollListView tListView,gListView,zListView;
    private DianzanAdapter tAdapter, gAdapter, zAdapter;

    private int mParallaxImageHeight;
    private EleCourseGridAdapter gridAdapter;
    private List<TagCourseModel> tagList = new ArrayList<>();
    private PraiseModel praiseModel;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    gridAdapter.notifyDataSetChanged();
                    break;
                case 1:
//                    setAdapterData();
                    break;
            }
        }
    };


    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main2, container, false);
        initView();
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getEleCourseList();
        getPraiseList();
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
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor
                (R.color.transparent)));

        mGridView = root.findViewById(R.id.gridView);

        mScrollView = root.findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);
        mScrollView.setZoomView(mImageView);

        tListView = root.findViewById(R.id.jiaoshi_layout);
zListView = root.findViewById(R.id.zhujiao_layout);

        gridAdapter = new EleCourseGridAdapter(getActivity(), tagList);
        mGridView.setAdapter(gridAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("id", tagList.get(position).getId());
                intent.setClass(mActivity, SearchCourseActivity.class);
                startActivity(intent);
            }
        });

        mToolbarView.findViewById(R.id.left_more_iv).setOnClickListener(this);
        mToolbarView.findViewById(R.id.frame_msg).setOnClickListener(this);
        mToolbarView.findViewById(R.id.school_iv).setOnClickListener(this);
    }

    private void setRecyclerView(RecyclerView view) {
        view.setHasFixedSize(true);
        StopLinearLayoutManager linearLayoutManager = new StopLinearLayoutManager(getActivity());
        linearLayoutManager.setScrollEnabled(false);
        view.setLayoutManager(linearLayoutManager);
        view.addItemDecoration(new DividerItemDecoration(getResources(), R.color.white, R.dimen
                .dp_10, OrientationHelper.VERTICAL));
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
            case R.id.frame_msg:
                Intent i = new Intent();
                i.setClass(mActivity, SearchCourseActivity.class);
                startActivity(i);
                break;
            case R.id.left_more_iv:
                ((MainActivity) getActivity()).openDrawer();
                break;
        }
    }

    private void getEleCourseList() {
        new EleCourseListApi(getActivity(), new FetchEntryListListener() {

            @Override
            public void setData(List list) {
                if (list != null && list.size() > 0) {
                    gridAdapter.addData(list);
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                Toast.makeText(getActivity(), error.getDesc(), Toast.LENGTH_LONG);
            }
        });
    }

    private void getPraiseList() {
        new PraiseListApi(getActivity(), new FetchEntryListener() {

            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof PraiseModel) {
                    praiseModel = (PraiseModel) entry;
                    handler.sendEmptyMessage(1);
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                Toast.makeText(getActivity(), error.getDesc(), Toast.LENGTH_LONG);
            }
        });
    }
}
