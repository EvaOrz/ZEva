package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.RelativeLayout;

import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.AnswerResultAdapter;
import cn.com.zwwl.bayuwen.api.AnswerDetailApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.OptionModel;

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
    @BindView(R.id.star_l)
    AppCompatImageView starL;
    @BindView(R.id.star_m)
    AppCompatImageView starM;
    @BindView(R.id.star_r)
    AppCompatImageView starR;
    @BindView(R.id.top_layout)
    RelativeLayout topLayout;
    AnswerResultAdapter adapter;
    int total;
    List<OptionModel>all;
    List<OptionModel>error;


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
        new AnswerDetailApi(this, getIntent().getStringExtra("sectionId"), new ResponseCallBack<List<OptionModel>>() {
            @Override
            public void result(List<OptionModel> optionModels, ErrorMsg errorMsg) {
                if (optionModels!=null){
                    all=new ArrayList<>();
                    error=new ArrayList<>();
                    for (OptionModel model:optionModels){
                        all.add(model);
                        if (!model.getAnswer().equals(model.getStudentAnswer()))error.add(model);
                    }
                    double i = (double) (all.size() - error.size()) / all.size();
                    if (i < 0.2) {
                        topLayout.setBackgroundResource(R.mipmap.answer_result_fail_bg);
                        starL.setImageResource(R.mipmap.answer_star_m_d);
                        starM.setImageResource(R.mipmap.answer_star_m_d);
                        starR.setImageResource(R.mipmap.answer_star_m_d);
                    } else {
                        topLayout.setBackgroundResource(R.mipmap.answer_result_bg);

                        if (i >= 0.2 && i < 0.4) {
                            starL.setImageResource(R.mipmap.answer_star_m_c);
                            starM.setImageResource(R.mipmap.answer_star_m_d);
                            starR.setImageResource(R.mipmap.answer_star_m_d);
                        } else if (i >= 0.4 && i < 0.8) {
                            starL.setImageResource(R.mipmap.answer_star_m_c);
                            starM.setImageResource(R.mipmap.answer_star_m_c);
                            starR.setImageResource(R.mipmap.answer_star_m_d);
                        } else {
                            starL.setImageResource(R.mipmap.answer_star_m_c);
                            starM.setImageResource(R.mipmap.answer_star_m_c);
                            starR.setImageResource(R.mipmap.answer_star_m_c);
                        }
                    }
                    answerResult.setText(String.format("答对%s/%s道题", all.size() - error.size(), total));
                    adapter = new AnswerResultAdapter(getSupportFragmentManager(), error);
                    viewPager.setAdapter(adapter);
                    pagerIndicator.setCount(error.size());
                }
            }
        });
//        new ErrorListApi(this, getIntent().getStringExtra("sectionId"), new ResponseCallBack<List<OptionModel>>() {
//            @Override
//            public void result(List<OptionModel> answerModels, ErrorMsg errorMsg) {
//                if (answerModels != null) {
//                    double i = (double) (total - answerModels.size()) / total;
//                    if (i < 0.2) {
//                        topLayout.setBackgroundResource(R.mipmap.answer_result_fail_bg);
//                        starL.setImageResource(R.mipmap.answer_star_m_d);
//                        starM.setImageResource(R.mipmap.answer_star_m_d);
//                        starR.setImageResource(R.mipmap.answer_star_m_d);
//                    } else {
//                        topLayout.setBackgroundResource(R.mipmap.answer_result_bg);
//
//                        if (i >= 0.2 && i < 0.4) {
//                            starL.setImageResource(R.mipmap.answer_star_m_c);
//                            starM.setImageResource(R.mipmap.answer_star_m_d);
//                            starR.setImageResource(R.mipmap.answer_star_m_d);
//                        } else if (i >= 0.4 && i < 0.8) {
//                            starL.setImageResource(R.mipmap.answer_star_m_c);
//                            starM.setImageResource(R.mipmap.answer_star_m_c);
//                            starR.setImageResource(R.mipmap.answer_star_m_d);
//                        } else {
//                            starL.setImageResource(R.mipmap.answer_star_m_c);
//                            starM.setImageResource(R.mipmap.answer_star_m_c);
//                            starR.setImageResource(R.mipmap.answer_star_m_c);
//                        }
//                    }
//                    answerResult.setText(String.format("答对%s/%s道题", total - answerModels.size(), total));
//                    adapter = new AnswerResultAdapter(getSupportFragmentManager(), answerModels);
//                    viewPager.setAdapter(adapter);
//                    pagerIndicator.setCount(answerModels.size());
//                }
//            }
//        });
    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.save, R.id.back_home})
    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    public void close() {
        finish();
    }

}
