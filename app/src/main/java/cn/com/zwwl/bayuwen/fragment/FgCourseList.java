package cn.com.zwwl.bayuwen.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CourseListAdapter;
import cn.com.zwwl.bayuwen.api.CourseListlApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.CommentModel;
import cn.com.zwwl.bayuwen.model.CourseDetailModel;
import cn.com.zwwl.bayuwen.model.CourseModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.widget.CustomViewPager;
import cn.com.zwwl.bayuwen.widget.NoScrollListView;
import cn.com.zwwl.bayuwen.widget.StopLinearLayoutManager;
import cn.com.zwwl.bayuwen.widget.newrefresh.PullToRefreshLayout;

import static cn.com.zwwl.bayuwen.MyApplication.mContext;
import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Created by lousx
 */
public class FgCourseList extends Fragment implements View.OnClickListener {
    private String videoId;
    private List<CourseModel.LecturesEntity> cvList = new LinkedList<>();
    private int page = 1;
    private View view;
    private RecyclerView listView;
    private PullToRefreshLayout refreshLayout;
    private CourseListAdapter adapter;
    private LinearLayout footView;
    private CustomViewPager customViewPager;
    private CourseModel courseModel;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    setData();
                    break;
                case 500:
                    ErrorMsg errorMsg = (ErrorMsg) msg.getData().getSerializable("error");
                    Toast.makeText(getActivity(), errorMsg.getDesc(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public static FgCourseList newInstance(String cid) {
        FgCourseList fg = new FgCourseList();
        Bundle bundle = new Bundle();
        bundle.putString("id", cid);
        fg.setArguments(bundle);
        return fg;
    }

    // 网络请求获取推荐的视频列表
    private void getCourseList() {
        if (videoId.equals("")) {
            return;
        }
        new CourseListlApi(mContext, videoId, page, new FetchEntryListener() {

            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof CourseModel) {
                    courseModel = (CourseModel) entry;
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void setError(final ErrorMsg error) {
                if (error != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("error", error);
                    Message message = new Message();
                    message.setData(bundle);
                    message.what = 500;
                    handler.sendMessage(message);
                }
            }
        });
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCourseList();
    }

    //初始化view
    private void initView() {
        listView = view.findViewById(R.id.listView);
        StopLinearLayoutManager linearLayoutManager = new StopLinearLayoutManager(getActivity());
        linearLayoutManager.setScrollEnabled(false);
        listView.setLayoutManager(linearLayoutManager);

        adapter = new CourseListAdapter(getActivity(), cvList);
        listView.setAdapter(adapter);

//        refreshLayout.setOnRefreshListener(this);
//        refreshLayout.autoRefresh();
//        footView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_footview, null);
//        listView.addFooterView(view);
//        footView.setOnClickListener(this);
    }

    private void setData() {
        adapter.appendData(courseModel.getLectures());
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
