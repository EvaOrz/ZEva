package cn.com.zwwl.bayuwen.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;

import cn.com.zwwl.bayuwen.R;


/**
 * 登录遇到问题的popwindow
 */
public class LoginProblemPopWindow implements OnClickListener {

    private Context mContext;
    private PopupWindow window;
    private ChooseGenderListener listener;

    public LoginProblemPopWindow(Context context, ChooseGenderListener listener) {
        mContext = context;
        this.listener = listener;
        init();
    }

    public interface ChooseGenderListener {
        public void choose(int gender);
    }

    public void init() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.pop_login_problem, null);
        window = new PopupWindow(view, LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setAnimationStyle(R.style.fetch_image_popup_anim);
        window.update();
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        view.findViewById(R.id.login_option_cancle)
                .setOnClickListener(this);
        view.findViewById(R.id.login_option1)
                .setOnClickListener(this);
        view.findViewById(R.id.login_option2).setOnClickListener(
                this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_option1:
                break;
            case R.id.login_option2:
                break;
        }
        window.dismiss();
    }

}
