package cn.com.zwwl.bayuwen.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.FinalEvalAdapter;
import cn.com.zwwl.bayuwen.widget.decoration.GridItemDecoration;

/**
 * 月度评价对话框
 * Created by zhumangmang at 2018/6/5 10:22
 */
public class FinalEvalDialog extends PopupWindow {
    @BindView(R.id.title)
    AppCompatTextView title;
    @BindView(R.id.yes)
    AppCompatRadioButton yes;
    @BindView(R.id.no)
    AppCompatRadioButton no;
    @BindView(R.id.layout_1)
    LinearLayout layout1;
    @BindView(R.id.layout_2)
    LinearLayout layout2;
    @BindView(R.id.label)
    RecyclerView label;
    @BindView(R.id.teacher)
    RecyclerView teacher;
    @BindView(R.id.adviser)
    RecyclerView adviser;
    @BindView(R.id.tutor)
    RecyclerView tutor;
    private Activity activity;
    private WindowManager.LayoutParams lp;
    private Animation animation;

    public FinalEvalDialog(Context context) {
        super(context);
        this.activity = (Activity) context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_final_eval, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        initView();
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

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        lp.alpha = 0.4f;
        activity.getWindow().setAttributes(lp);
    }

    private void initView() {
        animation = AnimationUtils.loadAnimation(activity, R.anim.animation_up);
        initRecycler(label, 2, Arrays.asList("上课需要更幽默", "备课需要更充分", "下课需要更提前"), 0);
        initRecycler(teacher, 3, Arrays.asList("张老师", "李老师", "王老师", "孙老师"), 1);
        initRecycler(tutor, 3, Arrays.asList("张老师", "李老师"), 1);
        initRecycler(adviser, 3, Arrays.asList("张老师"), 1);
    }

    private void initRecycler(RecyclerView recyclerView, int spanCount, List<String> data, int type) {
        recyclerView.setLayoutManager(new GridLayoutManager(activity, spanCount));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new GridItemDecoration(activity));
        FinalEvalAdapter adapter;
        if (type == 0)
            adapter = new FinalEvalAdapter(R.layout.item_final_label, data);
        else
            adapter = new FinalEvalAdapter(R.layout.item_final_vote, data);
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.star_eval, R.id.submit})
    public void onClick(View view) {
        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.VISIBLE);
        layout2.startAnimation(animation);
    }
}
