package cn.com.zwwl.bayuwen.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.rd.PageIndicatorView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.AnswerResultAdapter;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;

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
    String[] data;

    @Override
    protected int setContentView() {
        return R.layout.activity_answer_result;
    }

    @Override
    protected void initView() {
        answerResult.setText("5/6");
    }

    @Override
    protected void initData() {
        data = new String[5];
        for (int i = 0; i < 5; i++) {
            data[i] = "6月11日17时51分，李芳在随队护送学生通过红绿灯十字路口时，" +
                    "一辆装满西瓜的无牌照摩托三轮车突然向护路队急速驶来，毫无刹车迹象。千钧一发之际，" +
                    "李芳一边大声呼叫学生避让，一边冲上前去用自己的身体挡住学生，并奋力将学生推开。不幸的是，" +
                    "她被摩托三轮车严重撞击，倒地昏迷，另有4名学生受伤。\n" +
                    "\n" +
                    "事故发生后，受伤的李芳及受伤学生被送往医院进行救治。经当地医院检查诊断，" +
                    "4名被李芳救下的学生经治疗后暂无大碍。经专家会诊，李芳被确诊为脑部颅骨骨折，" +
                    "脑干出血，情况十分危险，且不宜长途转院接受手术治疗。";
        }
        adapter = new AnswerResultAdapter(getSupportFragmentManager(), data);
        viewPager.setAdapter(adapter);
        pagerIndicator.setViewPager(viewPager);
    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.save, R.id.back_home})
    @Override
    public void onClick(View view) {

    }

    @Override
    public void close() {
        finish();
    }
}
