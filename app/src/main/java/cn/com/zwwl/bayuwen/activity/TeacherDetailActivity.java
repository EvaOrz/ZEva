package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ScrollUtils;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.EleCourseGridAdapter;

/**
 * Created by lousx on 2018/5/15.
 */

public class TeacherDetailActivity extends BaseActivity {
    private TextView fcNameTv;
    private TextView fcnoTv;
    private TextView fcplaceTv;
    private TextView fctimeTv;
    private TextView fcpriceTv;
    private TextView scNameTv;
    private TextView scnoTv;
    private TextView scplaceTv;
    private TextView sctimeTv;
    private TextView scpriceTv;
    private TextView tcNameTv;
    private TextView tcnoTv;
    private TextView tcplaceTv;
    private TextView tctimeTv;
    private TextView tcpriceTv;
    private TextView tv_name;
    private TextView lecture_tv;
    private TextView label_tv;
    private TextView educal_background_tv;
    private TextView teaching_idea_tv;
    private TextView titleTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);
        initView();
    }

    @Override
    protected void initData() {

    }

    private void initView() {
        tv_name = findViewById(R.id.tv_name);
        lecture_tv = findViewById(R.id.lecture_tv);
        label_tv = findViewById(R.id.label_tv);
        educal_background_tv = findViewById(R.id.educal_background_tv);
        teaching_idea_tv = findViewById(R.id.teaching_idea_tv);
        titleTv = findViewById(R.id.title_tv);

        View view1 = findViewById(R.id.first_view);
        View view2 = findViewById(R.id.seconde_view);
        View view3 = findViewById(R.id.three_view);
        fcNameTv = view1.findViewById(R.id.course_tv);
        fcnoTv = view1.findViewById(R.id.classno_tv);
        fcplaceTv = view1.findViewById(R.id.place_tv);
        fctimeTv = view1.findViewById(R.id.time_tv);
        fcpriceTv = view1.findViewById(R.id.price_tv);
        scNameTv = view2.findViewById(R.id.course_tv);
        scnoTv = view2.findViewById(R.id.classno_tv);
        scplaceTv = view2.findViewById(R.id.place_tv);
        sctimeTv = view2.findViewById(R.id.time_tv);
        scpriceTv = view2.findViewById(R.id.price_tv);
        tcNameTv = view3.findViewById(R.id.course_tv);
        tcnoTv = view3.findViewById(R.id.classno_tv);
        tcplaceTv = view3.findViewById(R.id.place_tv);
        tctimeTv = view3.findViewById(R.id.time_tv);
        tcpriceTv = view3.findViewById(R.id.price_tv);

        titleTv.setText("教师详情");
        fcNameTv.setText("文脉传承《古文观止》第二季");
        scNameTv.setText("思晨写作二阶暑");
        tcNameTv.setText("文脉传承《古文观止》第二季");

        findViewById(R.id.back_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.back_btn:
                finish();
                break;
        }
    }
}
