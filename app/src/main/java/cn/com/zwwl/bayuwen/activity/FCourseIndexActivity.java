package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.RadarAdapter;
import cn.com.zwwl.bayuwen.adapter.UnitTableAdapter;
import cn.com.zwwl.bayuwen.api.HaveReportApi;
import cn.com.zwwl.bayuwen.api.StudyingCourseApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.dialog.FinalEvalDialog;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.CommonModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.ReportModel;
import cn.com.zwwl.bayuwen.model.StudyingModel;
import cn.com.zwwl.bayuwen.util.ToastUtil;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.decoration.HSpacesItemDecoration;

/**
 * 课程跟踪列表点击后进入该处
 * Created by zhumangmang at 2018/5/29 15:55
 */
public class FCourseIndexActivity extends BasicActivityWithTitle {
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.percent)
    AppCompatTextView percent;
    @BindView(R.id.radar)
    RecyclerView radar;
    @BindView(R.id.final_test)
    AppCompatTextView test;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    UnitTableAdapter unitTableAdapter;
    StudyingModel model;
    private String kid;
    RadarAdapter radarAdapter;
    List<CommonModel> models;
    FinalEvalDialog evalDialog;

    @Override
    protected int setContentView() {
        return R.layout.activity_finish_course_index;
    }

    @Override
    protected void initView() {
        evalDialog = new FinalEvalDialog(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new HSpacesItemDecoration(res, R.dimen.dp_5));
        radar.setLayoutManager(new GridLayoutManager(this, 9));
        radar.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData() {
        kid = getIntent().getStringExtra("kid");
        setCustomTitle(getIntent().getStringExtra("title"));
        test.setText(getIntent().getStringExtra("title"));
        models=new ArrayList<>();
        for (int i = 0; i < 54; i++) {
            CommonModel model = new CommonModel();
            model.setContent("" + i);
            models.add(model);
        }
        radarAdapter = new RadarAdapter(models, (int) (MyApplication.width * 0.9));
        radar.setAdapter(radarAdapter);
        getData();
        unitTableAdapter = new UnitTableAdapter(null);
        unitTableAdapter.setType(getIntent().getIntExtra("course_type", -1));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(unitTableAdapter);
    }


    private void getData() {
//        new PuzzleApi(this, kid, new ResponseCallBack<ArrayList<PuzzleModel>>() {
//            @Override
//            public void result(ArrayList<PuzzleModel> puzzleModels, ErrorMsg errorMsg) {
//                if (puzzleModels != null) {
//                    models = puzzleModels.get(0).getSectionList();
//                    radarAdapter.setNewData(models);
//                }
//            }
//        });
        new StudyingCourseApi(this, kid, new ResponseCallBack<StudyingModel>() {
            @Override
            public void result(StudyingModel studyingModel, ErrorMsg errorMsg) {
                if (studyingModel != null) {
                    model = studyingModel;
                    unitTableAdapter.setNewData(model.getCompleteClass());
                }
            }
        }
        );
    }

    @Override
    protected void setListener() {
        evalDialog.setSubmitListener(new FinalEvalDialog.SubmitListener() {
            @Override
            public void show() {
                evalDialog.showAtLocation(percent, Gravity.BOTTOM, 0, 0);
            }

            @Override
            public void ok() {
                ToastUtil.showShortToast("感谢评价");
            }

            @Override
            public void error(int code) {

            }
        });
        unitTableAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity, UnitIndexActivity.class);
                intent.putExtra("online", model.getOnline());
                intent.putExtra("kId", model.getCompleteClass().get(position).getKid());
                intent.putExtra("cId", model.getCompleteClass().get(position).getId());
                intent.putExtra("title", model.getCompleteClass().get(position).getTitle());
                intent.putExtra("video", 1);
                startActivity(intent);
            }
        });
        radarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity, AnswerActivity.class);
//                intent.putExtra("sectionId", models.get(position).getSectionId());
                intent.putExtra("sectionId", "2");
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.evaluate, R.id.final_test})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.evaluate:
                haveReport();
                break;
            case R.id.final_test:
                Intent intent = new Intent(mActivity, ExamDetailsActivity.class);
                intent.putExtra("kid", kid);
                intent.putExtra("title", Tools.getText(test));
                startActivity(intent);
                break;
        }

    }

    private void haveReport() {
        map.clear();
        map.put("kid", kid);
        map.put("type", "1");
        new HaveReportApi(this, map, new ResponseCallBack<ReportModel.ReportBean>() {
            @Override
            public void result(ReportModel.ReportBean reportBean, ErrorMsg errorMsg) {
                if (reportBean != null && !TextUtils.isEmpty(reportBean.getUrl())) {
                    if (reportBean.getComment_id() != null) {
                        ToastUtil.showShortToast("您已经评价过该课程");
                    } else {
                        evalDialog.setData(1, kid);
                    }
                } else {
                    ToastUtil.showShortToast("您还不能评价该课程哦！");
                }
            }
        });
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public boolean setParentScrollable() {
        return true;
    }
}
