package cn.com.zwwl.bayuwen.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CourseListAdapter;
import cn.com.zwwl.bayuwen.model.CourseModel;
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
    private ListView listView;
    private PullToRefreshLayout refreshLayout;
    private CourseListAdapter adapter;
    private TextView footView;

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
        return view;
    }

    //初始化view
    private void initView() {
//        refreshLayout = view.findViewById(R.id.refreshLayout);
        listView = view.findViewById(R.id.listView);
//        footView = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.layout_footview, null);
//        listView.addFooterView(view);
        listView.setEnabled(false);

//        refreshLayout.setOnRefreshListener(this);
//        refreshLayout.autoRefresh();
        if (cvList != null) {
            adapter = new CourseListAdapter(getActivity());
            listView.setAdapter(adapter);
        }

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
