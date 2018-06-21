package cn.com.zwwl.bayuwen.activity;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SeekBar;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.OptionsAdapter;
import cn.com.zwwl.bayuwen.api.QuestionListApi;
import cn.com.zwwl.bayuwen.api.SubmitAnswerApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.CommonModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.QuestionModel;
import cn.com.zwwl.bayuwen.util.TimeUtil;
import cn.com.zwwl.bayuwen.util.ToastUtil;

import static cn.com.zwwl.bayuwen.util.TimeUtil.FORMATTER_DEFAULT;

/**
 * 闯关答题
 * Created by zhumangmang at 2018/6/14 18:34
 */
public class AnswerActivity extends BasicActivityWithTitle {
    @BindView(R.id.progress)
    SeekBar progress;
    @BindView(R.id.current)
    AppCompatTextView current;
    @BindView(R.id.total)
    AppCompatTextView total;
    @BindView(R.id.question)
    AppCompatTextView question;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    OptionsAdapter adapter;
    List<CommonModel> options;
    QuestionModel model;
    List<QuestionModel.QuestionBean.ChoiceBean> choiceBeans;
    List<QuestionModel.QuestionBean.ChoiceBean> answerBeans;
    int index = 1;

    @Override
    protected int setContentView() {
        return R.layout.activity_answer;
    }

    @Override
    protected void initView() {
        progress.setEnabled(false);
        adapter = new OptionsAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        new QuestionListApi(this, "2", new ResponseCallBack<QuestionModel>() {
            @Override
            public void result(QuestionModel questionModel, ErrorMsg errorMsg) {
                if (questionModel != null && questionModel.getQuestion() != null) {
                    model = questionModel;
                    answerBeans = new ArrayList<>();
                    setCustomTitle(model.getSectionName());
                    choiceBeans = model.getQuestion().getChoice();
                    if (choiceBeans != null && choiceBeans.size() > 0) {
                        progress.setMax(choiceBeans.size());
                        total.setText(String.valueOf(choiceBeans.size()));
                        bindData();
                    }
                }
            }
        });
    }

    private void bindData() {
        progress.setProgress(index);
        current.setText(String.valueOf(index));
        question.setText(String.format("%s. %s", index, choiceBeans.get(index - 1).getTitle()));
        adapter.setOptions(choiceBeans.get(index - 1).getSelect(), choiceBeans.get(index - 1).getAnswer());
    }

    @Override
    protected void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter quickAdapter, View view, int position) {
                adapter.setChoose(choiceBeans.get(index - 1).getSelect().get(position).getOption());
//                if (answerBeans.size() == choiceBeans.size()) {
//                    submit();
//                } else {
                    QuestionModel.QuestionBean.ChoiceBean bean = new QuestionModel.QuestionBean.ChoiceBean();
                    bean.setAnswer(choiceBeans.get(index - 1).getSelect().get(position).getOption());
                    bean.setId(choiceBeans.get(index - 1).getId());
                    bean.setChoseTime(TimeUtil.getCurrentDate(FORMATTER_DEFAULT));
                    answerBeans.add(bean);
                    if (answerBeans.size() != choiceBeans.size()){
                        String a=choiceBeans.get(index-1).getSelect().get(position).getContent();
                        String b=answerBeans.get(index-1).getAnswer();
                        if (choiceBeans.get(index-1).getAnswer().equals( answerBeans.get(index-1).getAnswer())){   //判断所选择的答案和正确答案的却别

                            progress.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ++index;
                                    String a=choiceBeans.get(index-1).getSelect().get(0).getContent();
                                    bindData();

                                }
                            }, 1000);
                        }else{
                            ToastUtil.showShortToast("AAAAA");

                        }

                    }else{
                        submit();

            }
            }
        });

    }

    private void submit() {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();

        try {
            for (QuestionModel.QuestionBean.ChoiceBean bean : answerBeans
                    ) {
                JSONObject object = new JSONObject();
                object.put("questionId", bean.getId());
                object.put("answer", bean.getAnswer());
                object.put("answerTime", bean.getChoseTime());
                array.put(object);
            }
            jsonObject.put("studentId", "");
            jsonObject.put("list", array);
            new SubmitAnswerApi(mActivity, jsonObject.toString(), new ResponseCallBack<CommonModel>() {
                @Override
                public void result(CommonModel commonModel, ErrorMsg errorMsg) {
                    if (errorMsg==null) ToastUtil.showShortToast("上传成功");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void close() {
        finish();
    }
}
