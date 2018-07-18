package cn.com.zwwl.bayuwen.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.AnswerActivity;
import cn.com.zwwl.bayuwen.adapter.CheckScrollAdapter;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.PintuModel;
import cn.com.zwwl.bayuwen.util.UmengLogUtil;
import cn.com.zwwl.bayuwen.widget.NoScrollListView;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 退费理由option window
 */
public class DatiPopWindow {

    private Context mContext;
    private PopupWindow window;
    private TextView title, content;
    private PintuModel pintuModel;

    public DatiPopWindow(Context context, PintuModel pintuMode) {
        this.mContext = context;
        this.pintuModel = pintuMode;
        init();
    }

    public void init() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.pop_dati, null);
        view.findViewById(R.id.chuangguan_close)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        window.dismiss();
                    }
                });
        view.findViewById(R.id.chuangguan_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UmengLogUtil.logPintuChuangguanClick(mContext);
                Intent intent = new Intent(mContext, AnswerActivity.class);
                intent.putExtra("sectionId", pintuModel.getId());
//                intent.putExtra("sectionId", "2");
                mContext.startActivity(intent);
                window.dismiss();
            }
        });
        title = view.findViewById(R.id.chuangguan_title);
        content = view.findViewById(R.id.chuangguan_list);
        title.setText(pintuModel.getContent().getTitle());
        content.setText(pintuModel.getContent().getContent());

        window = new PopupWindow(view, RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.FILL_PARENT);
        window.setFocusable(true);

        window.setOutsideTouchable(true);
        window.update();
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(view, Gravity.CENTER, 0, 0);

    }


}
