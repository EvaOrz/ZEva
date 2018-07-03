package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.final_test)
    AppCompatTextView test;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    UnitTableAdapter unitTableAdapter;
    StudyingModel model;
    @BindView(R.id.leida_layout)
    LinearLayout leidaLayout;
    @BindView(R.id.evaluate)
    AppCompatTextView evaluate;
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
    }

    @Override
    protected void initData() {
        kid = getIntent().getStringExtra("kid");
        setCustomTitle(getIntent().getStringExtra("title"));
        test.setText(getIntent().getStringExtra("title"));
        models = new ArrayList<>();
        initPintu();
        getData();
        unitTableAdapter = new UnitTableAdapter(null);
        unitTableAdapter.setType(getIntent().getIntExtra("course_type", -1));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(unitTableAdapter);
    }

    private void initPintu() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_pingtu, null);

        int pintuWid = MyApplication.width * 1018 / (1018 + 50 * 2);
        int pintuHei = pintuWid * 6 / 9;

        int paddingLeft = pintuWid * 50 / 1018;
        int paddingRight = pintuWid * 50 / 1018;
        int paddingTop = pintuHei * 72 / 676;
        int paddingBottom = pintuHei * 112 / 676;
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(pintuWid +
                paddingLeft + paddingRight,
                pintuHei +
                        paddingTop + paddingBottom);
        view.setLayoutParams(params1);
        view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        RecyclerView recyclerView = view.findViewById(R.id.radar_fragmain1);
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(pintuWid, pintuHei));


        List<CommonModel> models = new ArrayList<>();
        for (int j = 0; j < 54; j++) {
            CommonModel model = new CommonModel();
            model.setContent("");
            models.add(model);
        }
        radarAdapter = new RadarAdapter(models, pintuWid);
        recyclerView.setAdapter(radarAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 9));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        leidaLayout.removeAllViews();
        leidaLayout.addView(view);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
