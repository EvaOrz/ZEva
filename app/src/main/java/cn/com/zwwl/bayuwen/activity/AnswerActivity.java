package cn.com.zwwl.bayuwen.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.OptionsAdapter;
import cn.com.zwwl.bayuwen.api.QuestionListApi;
import cn.com.zwwl.bayuwen.api.SubmitAnswerApi;
import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.dialog.AnswerDialog;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.CommonModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.OptionModel;
import cn.com.zwwl.bayuwen.model.QuestionModel;
import cn.com.zwwl.bayuwen.util.TimeUtil;
import cn.com.zwwl.bayuwen.util.ToastUtil;

import static cn.com.zwwl.bayuwen.util.TimeUtil.FORMATTER_DEFAULT;

/**
 * 闯关答题
 * Created by zhumangmang at 2018/6/14 18:34
 */
public class AnswerActivity extends BaseActivity {
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
    AnswerDialog dialog;
    QuestionModel model;
    List<OptionModel> choiceBeans;
    List<OptionModel> answerBeans;
    int index = 1;
    @BindView(R.id.id_back)
    ImageView idBack;
    @BindView(R.id.title_name)
    TextView titleName;
    private String sectionId;
    private Activity mActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
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
        progress.setEnabled(false);
        dialog = new AnswerDialog(this);
        adapter = new OptionsAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    protected void initData1() {
        sectionId = getIntent().getStringExtra("sectionId");
        new QuestionListApi(this, sectionId, new ResponseCallBack<QuestionModel>() {
            @Override
            public void result(QuestionModel questionModel, ErrorMsg errorMsg) {
                if (questionModel != null && questionModel.getQuestion() != null) {
                    model = questionModel;
                    answerBeans = new ArrayList<>();
                    titleName.setText(model.getSectionName());
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
        if (choiceBeans.size() < index) return;
        progress.setProgress(index);
        current.setText(String.valueOf(index));
        question.setText(String.format("%s. %s", index, choiceBeans.get(index - 1).getTitle()));
        adapter.setOptions(choiceBeans.get(index - 1).getSelect(), choiceBeans.get(index - 1)
                .getAnswer());
    }


    protected void setListener1() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter quickAdapter, View view, int position) {
                adapter.setChoose(choiceBeans.get(index - 1).getSelect().get(position).getOption());
                OptionModel bean = new OptionModel();
                bean.setAnswer(choiceBeans.get(index - 1).getSelect().get(position).getOption());
                bean.setId(choiceBeans.get(index - 1).getId());
                bean.setChoseTime(TimeUtil.getCurrentDate("yyyy-MM-dd hh:mm:ss"));
                answerBeans.add(bean);
                if (index == choiceBeans.size())
                    dialog.end();

                progress.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (adapter.isRight()) {
                            if (index + 1 == choiceBeans.size()) {// 最后一题直接跳转结果页面
                                submit();
                            } else {
                                ++index;
                                bindData();
                            }
                        } else {
                            dialog.setData(choiceBeans.get(index - 1).getRemark());
                            dialog.showAtLocation(progress, Gravity.BOTTOM, 0, 0);
                        }

                    }
                }, 1000);

            }
        });
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("答题结束".equals(v.getTag())) {
                    submit();
                } else {
                    ++index;
                    bindData();
                }
                dialog.dismiss();
            }
        });
    }

    private void submit() {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();

        try {
            for (OptionModel bean : answerBeans
                    ) {
                JSONObject obj = new JSONObject();
                obj.put("questionId", bean.getId());
                obj.put("answer", bean.getAnswer());
                obj.put("answerTime", bean.getChoseTime());
                array.put(obj);
            }
            jsonObject.put("studentId", Long.parseLong(TempDataHelper.getCurrentChildNo(this)));
            jsonObject.put("list", array);
            new SubmitAnswerApi(mActivity, jsonObject.toString(), new
                    ResponseCallBack<CommonModel>() {
                        @Override
                        public void result(CommonModel commonModel, ErrorMsg errorMsg) {
                            if (errorMsg == null) {
                                ToastUtil.showShortToast("上传成功");
                                Intent intent = new Intent(mActivity, AnswerResultActivity.class);
                                intent.putExtra("total", choiceBeans.size());
                                intent.putExtra("sectionId", sectionId);
                                startActivity(intent);
                                finish();
                            } else {
                                ToastUtil.showShortToast(errorMsg.getDesc());
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.id_back})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_back:
                finish();
                break;
        }
    }


}
