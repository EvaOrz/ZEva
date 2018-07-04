package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.PicAdapter;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.model.WorkDetailModel;

/**
 * 查看作业
 * Created by zhumangmang at 2018/6/2 13:48
 */
public class WorkDetailsActivity extends BasicActivityWithTitle {


    @BindView(R.id.homework)
    RecyclerView homework;
    @BindView(R.id.text_work)
    AppCompatTextView textWork;
    @BindView(R.id.teacher_eval)
    AppCompatTextView teacherEval;
    PicAdapter workAdapter;
    private WorkDetailModel model;

    @Override
    protected int setContentView() {
        return R.layout.activity_work_detail;
    }

    @Override
    protected void initView() {
        setCustomTitle("查看作业");
        homework.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        homework.setItemAnimator(new DefaultItemAnimator());
        workAdapter = new PicAdapter(null);
        homework.setAdapter(workAdapter);
    }

    @Override
    protected void initData() {
        model = getIntent().getParcelableExtra("model");
        if (model != null) {
            workAdapter.setNewData(model.getC_img());
            if (!TextUtils.isEmpty(model.getC_desc()))
                textWork.setText(model.getC_desc());
            else
                textWork.setText("无");
            if (model.getT_desc() != null)
                teacherEval.setText(model.getT_desc().getContent());
            else {
                teacherEval.setText("暂无点评");
            }
        }
    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.work)
    @Override
    public void onClick(View view) {
        if (model.getC_img() != null && model.getC_img().size() > 0) {
            Intent intent = new Intent(this, InClassStatusActivity.class);
            String[] urls = new String[model.getC_img().size()];
            for (int i = 0; i < model.getC_img().size(); i++)
                urls[i] = model.getC_img().get(i).getUrl();
            intent.putExtra("urls", urls);
            startActivity(intent);
        }

    }

    @Override
    public boolean setParentScrollable() {
        return true;
    }

    @Override
    public void close() {
        finish();
    }

}
