package cn.com.zwwl.bayuwen.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Script;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CompleteCourseAdapter;
import cn.com.zwwl.bayuwen.adapter.WorkListAdapter;
import cn.com.zwwl.bayuwen.api.CourseWorkListApi;
import cn.com.zwwl.bayuwen.api.MyCourseApi;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.CalendarEventModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.MyCourseModel;
import cn.com.zwwl.bayuwen.model.WorkListModel;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.decoration.DividerItemDecoration;

import static cn.com.zwwl.bayuwen.MyApplication.mContext;

/**
 * 作业列表
 */
public class WorkListActivity extends BaseActivity {

    @BindView(R.id.id_back)
    ImageView idBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.course_name)
    AppCompatTextView courseName;
    @BindView(R.id.course_code)
    AppCompatTextView courseCode;
    @BindView(R.id.top_layout)
    ConstraintLayout topLayout;
    @BindView(R.id.school_name)
    AppCompatTextView schoolName;
    @BindView(R.id.date)
    AppCompatTextView date;
    @BindView(R.id.teacher_name)
    LinearLayout teacherName;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private WorkListAdapter workListAdapter;
    private WorkListModel workListModel1;
    private WorkListModel.BigClassInfoBean bigClassInfoBean;
    private List<WorkListModel.ChildClassInfoBeanX> childClassInfoBeanx;
    private Activity activity;
    private String Kid;
    private HashMap<String,String> map=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_list);
        ButterKnife.bind(this);
        activity= this;
        Intent intent =getIntent();
        Kid = intent.getStringExtra("kid");
        map.put("kid", Kid);
        initData1();
        bindData();
    }

    private void bindData() {
        new CourseWorkListApi(activity, map,new ResponseCallBack<WorkListModel>() {
            @Override
            public void result(WorkListModel workListModel, ErrorMsg errorMsg) {

                if (workListModel != null) {
                    workListModel1 = workListModel;
                    bigClassInfoBean = workListModel1.getBigClassInfo();
                    childClassInfoBeanx = workListModel1.getChildClassInfo();
                    bindView();
                }
            }
        });
    }

    private void bindView() {

        workListAdapter.setNewData(childClassInfoBeanx);
        titleName.setText("作业列表");
        courseName.setText(bigClassInfoBean.getTitle());
        courseCode.setText(bigClassInfoBean.getModel());
        schoolName.setText(bigClassInfoBean.getSchool());
        date.setText(bigClassInfoBean.getClass_start_at());
//        schoolName.setText(bigClassInfoBean.getSchool());

        if (Tools.listNotNull(bigClassInfoBean.getTeacherInfo())) {
            for (WorkListModel.BigClassInfoBean.TeacherInfoBean teacherBean : bigClassInfoBean.getTeacherInfo()) {
                teacherName.addView(getChildView(teacherBean), LinearLayout
                        .LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        }
    }

    private View getChildView(final WorkListModel.BigClassInfoBean.TeacherInfoBean teacherBean ) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_teacher_calendar_list,
                null);
        TextView name = view.findViewById(R.id.item_t_c_name);
        ImageView avat = view.findViewById(R.id.item_t_c_avatar);
        name.setText(teacherBean.getName());
        if (!TextUtils.isEmpty(teacherBean.getPic()))
            ImageLoader.display(activity, avat, teacherBean.getPic());

        view.setOnClickListener(new View
                .OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, TeacherDetailActivity.class);
                i.putExtra("tid", teacherBean.getTid());
                activity.startActivity(i);
            }
        });
        return view;
    }
    private void initData1() {

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources(), R.color.body_bg, R
                .dimen.dp_5, OrientationHelper.VERTICAL));
        workListAdapter = new WorkListAdapter(childClassInfoBeanx);
        workListAdapter.setEmptyView(R.layout.empty_message_view, (ViewGroup) recyclerView.getParent());
        recyclerView.setAdapter(workListAdapter);

        workListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                if (childClassInfoBeanx.get(position).getChildClassInfo().getIslook() == 0) {

                    intent.putExtra("kid", childClassInfoBeanx.get(position).getChildClassInfo().getKid());
                    intent.putExtra("cid", childClassInfoBeanx.get(position).getChildClassInfo().getId());
                    intent.putExtra("titleName", bigClassInfoBean.getTitle());
                    intent.putExtra("courseName", childClassInfoBeanx.get(position).getChildClassInfo().getTitle());
                    intent.setClass(activity, UploadPicActivity.class);
                    startActivity(intent);
                } else {

                    intent.setClass(activity, WorkDetailsActivity.class);
                    intent.putExtra("model", childClassInfoBeanx.get(position));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }
  @OnClick({R.id.id_back})
    @Override
    public void onClick(View view) {
      switch (view.getId()) {
          case R.id.id_back:
              finish();
              break;
      }
  }

}
