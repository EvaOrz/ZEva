package cn.com.zwwl.bayuwen.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;


/**
 * 答案解析
 * Created by zhumangmang at 2018/6/15 10:18
 */

public class AnswerDialog extends PopupWindow {
    @BindView(R.id.content)
    AppCompatTextView content;
    @BindView(R.id.next)
    AppCompatTextView next;
    private Activity activity;
    private WindowManager.LayoutParams lp;

    public void setOnClickListener(View.OnClickListener itemsOnClick) {
        next.setOnClickListener(itemsOnClick);
    }

    public AnswerDialog(Context context) {
        super(context);
        this.activity = (Activity) context;
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_answer_parse, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        setWidth((int) (MyApplication.width * 0.9));
        setHeight((int) (MyApplication.height * 0.6));
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

    public void end() {
        next.setText("答题结束");
        next.setTag("答题结束");
    }

    public void setData(String remark) {
        content.setText(remark);
    }
}
