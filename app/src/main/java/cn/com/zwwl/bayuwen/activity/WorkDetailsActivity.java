package cn.com.zwwl.bayuwen.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.Pic1Adapter;
import cn.com.zwwl.bayuwen.adapter.PicAdapter;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.model.WorkDetailModel;
import cn.com.zwwl.bayuwen.model.WorkListModel;
import cn.com.zwwl.bayuwen.widget.photoview.PhotoActivity;

/**
 * 查看作业
 * Created by zhumangmang at 2018/6/2 13:48
 */
public class WorkDetailsActivity extends BaseActivity {


    @BindView(R.id.homework)
    RecyclerView homework;
    @BindView(R.id.text_work)
    AppCompatTextView textWork;
    @BindView(R.id.teacher_eval)
    AppCompatTextView teacherEval;
    Pic1Adapter workAdapter;
    @BindView(R.id.id_back)
    ImageView idBack;
    @BindView(R.id.title_name)
    TextView titleName;
    private WorkListModel.ChildClassInfoBeanX model;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_detail);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        mActivity = this;
        initView1();
        initData1();
        setListener1();
    }

    @Override
    protected void initData() {

    }


    protected void initView1() {
        titleName.setText("查看作业");
        homework.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        homework.setItemAnimator(new DefaultItemAnimator());
        workAdapter = new Pic1Adapter(null);
        homework.setAdapter(workAdapter);
    }

    protected void initData1() {
        model = (WorkListModel.ChildClassInfoBeanX) getIntent().getSerializableExtra("model");
        if (model != null) {
            workAdapter.setNewData(model.getJob().getJob_img());
            if (!TextUtils.isEmpty(model.getJob().getJob_desc()))
                textWork.setText(model.getJob().getJob_desc());
            else
                textWork.setText("无");
            if (model.getJob().getT_desc() != null)
                teacherEval.setText(model.getJob().getT_desc());
            else {
                teacherEval.setText("暂无点评");
            }
        }
    }

    protected void setListener1() {
        workAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (model.getJob().getJob_img() != null && model.getJob().getJob_img().size() > 0) {
                    Intent intent = new Intent(mActivity, PhotoActivity.class);
                    String[] urls = new String[model.getJob().getJob_img().size()];
                    for (int i = 0; i < model.getJob().getJob_img().size(); i++)
                        urls[i] = model.getJob().getJob_img().get(i).getUrl();
                    intent.putExtra("images", urls);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            }
        });
    }

    @OnClick(R.id.id_back)
    @Override
    public void onClick(View view) {
//        if (model.getC_img() != null && model.getC_img().size() > 0) {
//            Intent intent = new Intent(this, InClassStatusActivity.class);
//            String[] urls = new String[model.getC_img().size()];
//            for (int i = 0; i < model.getC_img().size(); i++)
//                urls[i] = model.getC_img().get(i).getUrl();
//            intent.putExtra("urls", urls);
//            startActivity(intent);
//        }
      finish();
    }



}
