package cn.com.zwwl.bayuwen.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CourseListAdapter;
import cn.com.zwwl.bayuwen.model.CommentModel;
import cn.com.zwwl.bayuwen.model.CourseModel;
import cn.com.zwwl.bayuwen.widget.CustomViewPager;
import cn.com.zwwl.bayuwen.widget.NoScrollListView;
import cn.com.zwwl.bayuwen.widget.newrefresh.PullToRefreshLayout;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Created by lousx
 */
public class FgCourseList extends Fragment implements View.OnClickListener {
    private String videoId;
    private List<CourseModel> cvList = new LinkedList<>();
    private int page = 1;
    private View view;
    private RecyclerView listView;
    private PullToRefreshLayout refreshLayout;
    private CourseListAdapter adapter;
    private LinearLayout footView;
    private CustomViewPager customViewPager;

    public static FgCourseList newInstance(String videoId) {
        FgCourseList fg = new FgCourseList();
        Bundle bundle = new Bundle();
        bundle.putString("id", videoId);
        fg.setArguments(bundle);
        return fg;
    }

    // 网络请求获取推荐的视频列表
    private void getCourseList() {
        if (videoId.equals("")) {
            return;
        }
    }

    public void setCustomViewPager(CustomViewPager customViewPager) {
        this.customViewPager = customViewPager;
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
        initView();
        customViewPager.setObjectForPosition(view, 0);
        return view;
    }

    //初始化view
    private void initView() {
//        refreshLayout = view.findViewById(R.id.refreshLayout);
        listView = view.findViewById(R.id.listView);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));

        for (int i = 0; i < 4; i++) {
            CourseModel courseModel = new CourseModel();
            cvList.add(courseModel);
        }

        adapter = new CourseListAdapter(getActivity(), cvList);
        listView.setAdapter(adapter);

//        refreshLayout.setOnRefreshListener(this);
//        refreshLayout.autoRefresh();
//        footView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_footview, null);
//        listView.addFooterView(view);
//        footView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_tv:
                page++;
                getCourseList();
                break;
        }
    }
}
