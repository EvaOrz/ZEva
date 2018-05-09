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
 * 选择性别
 */
public class GenderPopWindow implements OnClickListener {

    private Context mContext;
    private PopupWindow window;
    private ChooseGenderListener listener;

    public GenderPopWindow(Context context, ChooseGenderListener listener) {
        mContext = context;
        this.listener = listener;
        init();
    }

    public interface ChooseGenderListener {
        public void choose(int gender);
    }

    public void init() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.pop_gender, null);
        window = new PopupWindow(view, LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setAnimationStyle(R.style.fetch_image_popup_anim);
        window.update();
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        view.findViewById(R.id.gender_cancle)
                .setOnClickListener(this);
        view.findViewById(R.id.gender_female)
                .setOnClickListener(this);
        view.findViewById(R.id.gender_male).setOnClickListener(
                this);
        view.findViewById(R.id.gender_nomale).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.gender_female:
                listener.choose(2);
                break;
            case R.id.gender_male:
                listener.choose(1);
                break;
            case R.id.gender_nomale:
                listener.choose(0);
                break;
        }
        window.dismiss();
    }

}
