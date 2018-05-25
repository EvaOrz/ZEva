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
import cn.com.zwwl.bayuwen.model.CourseDetailModel;
import cn.com.zwwl.bayuwen.model.CourseModel;
import cn.com.zwwl.bayuwen.widget.CustomViewPager;
import cn.com.zwwl.bayuwen.widget.newrefresh.PullToRefreshLayout;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Created by lousx
 */
public class FgCourseSyllabus extends Fragment {
    private String videoId;
    private int page = 1;
    private View view;
    private CustomViewPager customViewPager;
    private TextView introduction_tv;
    private TextView grade_tv;
    private TextView teaching_goal_tv;
    private TextView teatures_tv;
    private CourseDetailModel.CourseProgramEntity programEntity;

    public static FgCourseSyllabus newInstance() {
        FgCourseSyllabus fg = new FgCourseSyllabus();
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
        view = inflater.inflate(R.layout.fragment_c_syllabus, container, false);
        initView(view);
        customViewPager.setObjectForPosition(view, 1);
        return view;
    }

    //初始化view
    private void initView(View view) {
        introduction_tv = view.findViewById(R.id.introduction_tv);
        grade_tv = view.findViewById(R.id.grade_tv);
        teaching_goal_tv = view.findViewById(R.id.teaching_goal_tv);
        teatures_tv = view.findViewById(R.id.teatures_tv);
    }

    public void setEntity(CourseDetailModel.CourseProgramEntity programEntity) {
        this.programEntity = programEntity;
        setData();
    }

    public void setData() {
        introduction_tv.setText(programEntity.getDesc());
        grade_tv.setText(programEntity.getUsers());
        teaching_goal_tv.setText(programEntity.getC_target());
        teatures_tv.setText(programEntity.getC_trait());
    }

}
