package cn.com.zwwl.bayuwen.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RatingBar;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.FinalEvalLabelAdapter;
import cn.com.zwwl.bayuwen.adapter.FinalEvalVoteAdapter;
import cn.com.zwwl.bayuwen.api.EvalApi;
import cn.com.zwwl.bayuwen.api.EvalLabelApi;
import cn.com.zwwl.bayuwen.api.VoteApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.CommonModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.EvalContentModel;
import cn.com.zwwl.bayuwen.util.ToastUtil;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.decoration.GridItemDecoration;

/**
 * 月度评价对话框
 * Created by zhumangmang at 2018/6/5 10:22
 */
public class FinalEvalDialog extends PopupWindow {
    @BindView(R.id.title)
    AppCompatTextView title;
    @BindView(R.id.layout_1)
    LinearLayout layout1;
    @BindView(R.id.layout_2)
    LinearLayout layout2;
    @BindView(R.id.teacher)
    RecyclerView teacher;
    @BindView(R.id.adviser)
    RecyclerView adviser;
    @BindView(R.id.tutor)
    RecyclerView tutor;
    @BindView(R.id.star_level)
    AppCompatRatingBar starLevel;
    @BindView(R.id.star_eval)
    AppCompatTextView starEval;
    @BindView(R.id.star_label)
    RecyclerView starLabel;
    @BindView(R.id.other)
    AppCompatEditText other;
    @BindView(R.id.teacher_empty)
    AppCompatTextView teacherEmpty;
    @BindView(R.id.adviser_empty)
    AppCompatTextView adviserEmpty;
    @BindView(R.id.tutor_empty)
    AppCompatTextView tutorEmpty;
    @BindView(R.id.label_1)
    AppCompatTextView label1;
    @BindView(R.id.yes)
    RadioButton yes;
    @BindView(R.id.yes_layout)
    LinearLayout yesLayout;
    @BindView(R.id.no)
    RadioButton no;
    @BindView(R.id.no_layout)
    LinearLayout noLayout;
    @BindView(R.id.submit)
    AppCompatTextView submit;
    private FinalEvalLabelAdapter labelAdapter;
    private FinalEvalVoteAdapter teacherAdapter, tutorAdapter, adviserAdapter;
    private List<EvalContentModel.ScoreRuleBean> score_rule;
    private Activity activity;
    private WindowManager.LayoutParams lp;
    private Animation animation;
    private HashMap<String, String> para;
    private int type;//  1：课节报告 2：期中 3：期末
    private String kid, lecture_id;
    private SubmitListener listener;
    private EvalContentModel contentModel;

    public FinalEvalDialog(Context context) {
        super(context);
        this.activity = (Activity) context;
        para = new HashMap<>();
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_final_eval, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        initView();
        setListener();
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setAnimationStyle(R.style.Popup_Style);
        ColorDrawable dw = new ColorDrawable(0x80000000);
        setBackgroundDrawable(dw);
        lp = activity.getWindow()
                .getAttributes();
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha = 1f;
                activity.getWindow().setAttributes(lp);
            }
        });
    }

    private void setListener() {
        starLevel.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int index = (int) (rating - 1);
                other.setVisibility(View.VISIBLE);
                if (score_rule != null) {
                    starEval.setText(score_rule.get(index).getTitle());
                    labelAdapter.setNewData(score_rule.get(index).getCond());
                    para.put("score", score_rule.get(index).getScore());
                }
            }
        });
        teacherAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                vote("1", position, contentModel.getTeacher().get(position).getTid(),
                        contentModel.getTeacher().get(position).getIsPraised() == 0);
            }
        });
        tutorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                vote("3", position, contentModel.getTutors().get(position).getTid(), contentModel
                        .getTutors().get(position).getIsPraised() == 0);
            }
        });
        adviserAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                vote("2", position, contentModel.getStu_advisors().get(position).getTid(),
                        contentModel.getStu_advisors().get(position).getIsPraised() == 0);
            }
        });
    }

    private void vote(final String theme, final int position, String tId, final boolean isZan) {
        para.clear();

        para.put("type", isZan ? "1" : "2");// 1添加2取消
        para.put("tid", tId);
        new VoteApi(activity, para, new ResponseCallBack<ErrorMsg>() {
            @Override
            public void result(ErrorMsg commentModel, ErrorMsg errorMsg) {
                if (errorMsg == null) {
                    switch (theme) {
                        case "1":
                            contentModel.getTeacher().get(position).setIsPraised(isZan ? 1 : 0);
                            teacherAdapter.setNewData(contentModel.getTeacher());
                            break;
                        case "2":
                            contentModel.getStu_advisors().get(position).setIsPraised(isZan ? 1 :
                                    0);
                            adviserAdapter.setNewData(contentModel.getStu_advisors());
                            break;
                        case "3":
                            contentModel.getTutors().get(position).setIsPraised(isZan ? 1 : 0);
                            tutorAdapter.setNewData(contentModel.getTutors());
                            break;
                    }
                } else ToastUtil.showShortToast(errorMsg.getDesc());
            }
        });
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        lp.alpha = 0.4f;
        activity.getWindow().setAttributes(lp);
    }

    private void initView() {
        animation = AnimationUtils.loadAnimation(activity, R.anim.animation_up);
        labelAdapter = new FinalEvalLabelAdapter(null);
        teacherAdapter = new FinalEvalVoteAdapter(null);
        tutorAdapter = new FinalEvalVoteAdapter(null);
        adviserAdapter = new FinalEvalVoteAdapter(null);
        initRecycler(starLabel, 2, labelAdapter);
        initRecycler(teacher, 3, teacherAdapter);
        initRecycler(tutor, 3, tutorAdapter);
        initRecycler(adviser, 3, adviserAdapter);
        yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setCheck(true);
                    showNext();
                }
            }
        });
        no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setCheck(false);
                    showNext();
                }
            }
        });
    }

    private void setCheck(boolean isYes) {
        if (isYes) {
            yesLayout.setBackground(activity.getResources().getDrawable(R.drawable
                    .drawable_checked__corner));
            noLayout.setBackground(activity.getResources().getDrawable(R.drawable
                    .drawable_uncheck__corner));
            yes.setChecked(true);
            no.setChecked(false);
        } else {
            yesLayout.setBackground(activity.getResources().getDrawable(R.drawable
                    .drawable_uncheck__corner));
            noLayout.setBackground(activity.getResources().getDrawable(R.drawable
                    .drawable_checked__corner));
            yes.setChecked(false);
            no.setChecked(true);
        }

    }

    private void initRecycler(RecyclerView recyclerView, int spanCount, BaseQuickAdapter adapter) {
        recyclerView.setLayoutManager(new GridLayoutManager(activity, spanCount));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new GridItemDecoration(activity));
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.next, R.id.submit, R.id.yes_layout, R.id.no_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next:
                showNext();
                break;
            case R.id.submit:
                submit();
                break;
            case R.id.yes_layout:
                setCheck(true);
                break;
            case R.id.no_layout:
                setCheck(false);
                break;
        }
    }

    private void showNext() {
        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.VISIBLE);
        layout2.startAnimation(animation);
    }

    private void submit() {
        StringBuilder builder = new StringBuilder();
        if (yes.isChecked()) para.put("show", "0");
        if (no.isChecked()) para.put("show", "1");
        for (EvalContentModel.DataBean dataBean : labelAdapter.getSelectedItem()) {
            builder.append(dataBean.getValue()).append(",");
        }
        para.put("cond", builder.toString());
        para.put("content", Tools.getText(other).isEmpty() ? "" : Tools.getText(other));
        para.put("kid", kid);
        //1课程2子课3老师4月报5话题6助教7班主任8期中9期末
        if (type == 1) {
            para.put("type", "2");
        } else if (type == 2) {
            para.put("type", "8");
        } else if (type == 3) {
            para.put("type", "9");
        }
        if (type == 1) {
            para.put("cid", lecture_id);
        }
        new EvalApi(activity, para, new ResponseCallBack<CommonModel>() {
            @Override
            public void result(CommonModel commonModel, ErrorMsg errorMsg) {
                if (commonModel != null) listener.ok();
                else listener.error(1);
            }
        });

    }

    public void setData(int type, String kid, String lectureId) {
        this.kid = kid;
        this.type = type;
        this.lecture_id = lectureId;
        para.clear();
        para.put("kid", kid);
        if (!TextUtils.isEmpty(lectureId))
            para.put("lecture_id", lectureId);
        getContent();
    }

    private void getContent() {
        new EvalLabelApi(activity, para, new ResponseCallBack<EvalContentModel>() {
            @Override
            public void result(EvalContentModel model, ErrorMsg errorMsg) {
                contentModel = model;
                if (model != null) {
                    listener.show();
                    if (type == 1)
                        title.setText("课节评价");
                    else if (type == 2)
                        title.setText("期中评价");
                    else if (type == 3)
                        title.setText("期末评价");
                    //一级界面
                    label1.setText(model.getShow().getTitle());
                    yes.setText(model.getShow().getData().get(0).getName());
                    no.setText(model.getShow().getData().get(1).getName());
                    //二级界面
                    setListData(teacher, teacherEmpty, model.getTeacher(), teacherAdapter);
                    setListData(tutor, tutorEmpty, model.getTutors(), tutorAdapter);
                    setListData(adviser, adviserEmpty, model.getStu_advisors(), adviserAdapter);
                    score_rule = model.getScore_rule();
                } else {
                    listener.error(0);
                }
            }
        });
    }

    private void setListData(RecyclerView recyclerView, AppCompatTextView empty,
                             List<EvalContentModel.DataBean> dataBeans, FinalEvalVoteAdapter
                                     adapter) {
        if (dataBeans == null || dataBeans.size() == 0) {
            recyclerView.setVisibility(View.GONE);
        } else {
            empty.setVisibility(View.GONE);
            adapter.setNewData(dataBeans);
        }
    }

    public void setSubmitListener(SubmitListener listener) {
        this.listener = listener;
    }

    public interface SubmitListener {
        void ok();

        void show();

        void error(int code);
    }
}
