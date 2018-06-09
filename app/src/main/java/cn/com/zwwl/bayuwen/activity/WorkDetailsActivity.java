package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.PicAdapter;
import cn.com.zwwl.bayuwen.api.FinalApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.FinalModel;

/**
 * 查看作业
 * Created by zhumangmang at 2018/6/2 13:48
 */
public class WorkDetailsActivity extends BasicActivityWithTitle {

    PicAdapter workAdapter;
    @BindView(R.id.homework)
    RecyclerView homework;
    @BindView(R.id.text_work)
    AppCompatTextView textWork;
    @BindView(R.id.teacher_eval)
    AppCompatTextView teacherEval;
    private FinalModel model;

    @Override
    protected int setContentView() {
        return R.layout.activity_work_detail;
    }

    @Override
    protected void initView() {
        homework.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        homework.setItemAnimator(new DefaultItemAnimator());
        workAdapter = new PicAdapter(null);
        homework.setAdapter(workAdapter);
    }

    @Override
    protected void initData() {
        setCustomTitle(getIntent().getStringExtra("title"));
        String kId = getIntent().getStringExtra("kid");
        getData(kId);
    }

    private void getData(String kId) {
        new FinalApi(this, kId, new ResponseCallBack<FinalModel>() {
            @Override
            public void result(FinalModel finalModel, ErrorMsg errorMsg) {
                if (finalModel != null) {
                    model = finalModel;
                    workAdapter.setNewData(model.getExam());
                    if (model.getTeachers() == null) {
                        teacherEval.setText("无");
                    } else {
                        teacherEval.setText(model.getTeachers().getContent());
                    }

                }
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.work)
    @Override
    public void onClick(View view) {
        if (model.getExam() != null && model.getExam().size() > 0) {
            Intent intent = new Intent(this, InClassStatusActivity.class);
            String[] urls = new String[model.getExam().size()];
            for (int i = 0; i < model.getExam().size(); i++)
                urls[i] = model.getExam().get(i).getUrl();
            intent.putExtra("urls", urls);
            startActivity(intent);
        }

    }

    @Override
    public void close() {
        finish();
    }

}
