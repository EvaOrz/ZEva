package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.rd.PageIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.AnswerResultAdapter;
import cn.com.zwwl.bayuwen.api.ErrorListApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.AnswerModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 闯关结果
 * Created by zhumangmang at 2018/6/15 11:16
 */
public class AnswerResultActivity extends BasicActivityWithTitle {
    @BindView(R.id.answer_result)
    AppCompatTextView answerResult;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.pager_indicator)
    PageIndicatorView pagerIndicator;
    AnswerResultAdapter adapter;
    int total;

    @Override
    protected int setContentView() {
        return R.layout.activity_answer_result;
    }

    @Override
    protected void initView() {
        total = getIntent().getIntExtra("total", 0);
        pagerIndicator.setViewPager(viewPager);
    }

    @Override
    protected void initData() {
        new ErrorListApi(this, getIntent().getStringExtra("sectionId"), new ResponseCallBack<List<AnswerModel>>() {
            @Override
            public void result(List<AnswerModel> answerModels, ErrorMsg errorMsg) {
                if (answerModels != null) {
                    answerResult.setText(String.format("答对%s/%s道题", total - answerModels.size(), total));
                    adapter = new AnswerResultAdapter(getSupportFragmentManager(), answerModels);
                    viewPager.setAdapter(adapter);
                    pagerIndicator.setCount(answerModels.size());
                }
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.save, R.id.back_home})
    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, FCourseIndexActivity.class));
    }

    @Override
    public void close() {
        finish();
    }
}
