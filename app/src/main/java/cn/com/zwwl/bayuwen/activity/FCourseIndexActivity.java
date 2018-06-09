package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.RadarAdapter;
import cn.com.zwwl.bayuwen.adapter.UnitTableAdapter;
import cn.com.zwwl.bayuwen.api.EvalLabelApi;
import cn.com.zwwl.bayuwen.api.StudyingCourseApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.dialog.FinalEvalDialog;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.CommonModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.EvalContentModel;
import cn.com.zwwl.bayuwen.model.StudyingModel;
import cn.com.zwwl.bayuwen.util.DensityUtil;
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
    EvalContentModel evalContentModel;

    @Override
    protected int setContentView() {
        return R.layout.activity_finish_course_index;
    }

    @Override
    protected void initView() {
        setCustomTitle("大语文");
        evalDialog = new FinalEvalDialog(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new HSpacesItemDecoration(res, R.dimen.dp_5));
        radar.setLayoutManager(new GridLayoutManager(this, 10));
        radar.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int m = parent.getChildAdapterPosition(view) % 10;
                if (m != 0)
                    outRect.left = -DensityUtil.dip2px(res, R.dimen.line_height) * m;
                outRect.bottom = -DensityUtil.dip2px(res, R.dimen.dp_5);
            }
        });
        radar.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData() {
        models = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            CommonModel model = new CommonModel();
            model.setContent("");
            models.add(model);
        }
        radarAdapter = new RadarAdapter(models);
        radar.setAdapter(radarAdapter);
        kid = getIntent().getStringExtra("kid");
        setCustomTitle(getIntent().getStringExtra("title"));
        test.setText(getIntent().getStringExtra("title"));
        getData();
        getEvalContent();
        unitTableAdapter = new UnitTableAdapter(null);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(unitTableAdapter);
    }

    /**
     * 获取评论内容
     */
    private void getEvalContent() {
        map.put("type", "1");
        map.put("kid", kid);
        new EvalLabelApi(this, map, new ResponseCallBack<EvalContentModel>() {
            @Override
            public void result(EvalContentModel model, ErrorMsg errorMsg) {
                evalContentModel = model;
            }
        });
    }

    private void getData() {
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
            public void ok() {
                ToastUtil.showShortToast("感谢评价");
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
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.evaluate, R.id.final_test})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.evaluate:
                if (evalDialog == null) evalDialog = new FinalEvalDialog(this);
                evalDialog.setData(1,kid, evalContentModel);
                evalDialog.showAtLocation(percent, Gravity.BOTTOM, 0, 0);

                break;
            case R.id.final_test:
                Intent intent = new Intent(mActivity, ExamDetailsActivity.class);
                intent.putExtra("kid", kid);
                intent.putExtra("title", Tools.getText(test));
                startActivity(intent);
                break;
        }

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
