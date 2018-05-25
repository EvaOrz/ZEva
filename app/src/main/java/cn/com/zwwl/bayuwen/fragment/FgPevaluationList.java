package cn.com.zwwl.bayuwen.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.LinkedList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.PevaluationListAdapter;
import cn.com.zwwl.bayuwen.widget.CustomViewPager;

/**
 * Created by lousx
 */
public class FgPevaluationList extends Fragment implements View.OnClickListener {
    private String videoId;
    private List<CommentModel> cvList = new LinkedList<>();
    private int page = 1;
    private View view;
    private RecyclerView listView;
//    private PullToRefreshLayout refreshLayout;
    private PevaluationListAdapter adapter;
    private LinearLayout footView;
    private CustomViewPager customViewPager;

    public static FgPevaluationList newInstance(String videoId) {
        FgPevaluationList fg = new FgPevaluationList();
        Bundle bundle = new Bundle();
        bundle.putString("id", videoId);
        fg.setArguments(bundle);
        return fg;
    }

    public void setCustomViewPager(CustomViewPager customViewPager){
        this.customViewPager = customViewPager;
    }


    // 网络请求获取推荐的视频列表
    private void getCourseList() {
        if (videoId.equals("")) {
            return;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        videoId = bundle != null ? bundle.getString("id") : "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_course_list, container, false);
        customViewPager.setObjectForPosition(view,2);
        initView();
        return view;
    }

    //初始化view
    private void initView() {
        listView = view.findViewById(R.id.listView);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        refreshLayout = view.findViewById(R.id.refreshLayout);
//        footView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_footview, null);
//        listView.addFooterView(view);
        listView.setEnabled(false);

//        refreshLayout.setOnRefreshListener(this);
//        refreshLayout.autoRefresh();

        for (int i = 0;i<4;i++){
            CommentModel commentModel = new CommentModel();
            cvList.add(commentModel);
        }

        if (cvList != null) {
            adapter = new PevaluationListAdapter(getActivity(),cvList);
            listView.setAdapter(adapter);
        }

//        footView.setOnClickListener(this);
    }

//    @Override
//    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
//        page = 1;
//        adapter.clear();
//        getCourseList();
//    }
//
//    @Override
//    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_tv:
//                Intent intent = new Intent();
//                intent.setClass(getActivity(),)
//                startActivity(intent);
                break;
        }
    }
}
