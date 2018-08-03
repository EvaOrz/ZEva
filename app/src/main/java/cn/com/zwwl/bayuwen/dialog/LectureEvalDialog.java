package cn.com.zwwl.bayuwen.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.LectureEvalApi;
import cn.com.zwwl.bayuwen.api.LectureEvalCommitApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.AppValue;

/**
 * 课节报告评价popwindow
 */
public class LectureEvalDialog implements View.OnClickListener {

    private Context mContext;
    private PopupWindow window;
    private TextView ttv, htv, stv;
    private RadioGroup tGroup, hGroup, sGroup;

    private LectureEvalApi.LectureEvalModel model;
    private String kid, lid;
    private int tsa = -1, hsa = -1, ssa = -1;

    /**
     * @param context
     */
    public LectureEvalDialog(Context context, String kid, String lid) {
        mContext = context;
        this.kid = kid;
        this.lid = lid;

        init();
        new LectureEvalApi(context, kid, lid, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof LectureEvalApi.LectureEvalModel) {
                    model = (LectureEvalApi.LectureEvalModel) entry;
                    handler.sendEmptyMessage(0);
                }

            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    ttv.setText(model.getTeacher().getName());
                    htv.setText(model.getHeadteacher().getName());
                    stv.setText(model.getTeacher().getName());

                    break;
                case 1:
                    window.dismiss();
                    break;
            }
        }
    };


    public void init() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.pop_lecture_eval, null);
        view.findViewById(R.id.eval_commit)
                .setOnClickListener(this);
        window = new PopupWindow(view, RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setAnimationStyle(R.style.fetch_image_popup_anim);
        window.update();
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        ttv = view.findViewById(R.id.t_tv);
        htv = view.findViewById(R.id.h_tv);
        stv = view.findViewById(R.id.s_tv);
        tGroup = view.findViewById(R.id.t_group);
        tGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_t_1) {
                    tsa = 1;
                } else if (checkedId == R.id.radio_t_2) {
                    tsa = 0;
                }
            }
        });

        hGroup = view.findViewById(R.id.h_group);
        hGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_h_1) {
                    hsa = 1;
                } else if (checkedId == R.id.radio_h_2) {
                    hsa = 0;
                }
            }
        });

        sGroup = view.findViewById(R.id.s_group);
        sGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_s_1) {
                    ssa = 1;
                } else if (checkedId == R.id.radio_s_2) {
                    ssa = 0;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.eval_commit:
                if (tsa >= 0 && hsa >= 0 && ssa >= 0) {
                    new LectureEvalCommitApi(mContext, kid, lid, model.getTeacher().getTid(), tsa + "", model.getHeadteacher().getTid(), hsa + "", model.getSchool().getTid(), ssa + "", new FetchEntryListener() {
                        @Override
                        public void setData(Entry entry) {

                        }

                        @Override
                        public void setError(ErrorMsg error) {
                            if (error == null) {
                                AppValue.showToast(mContext, "提交成功");
                            } else {
                                AppValue.showToast(mContext, error.getDesc());
                            }
                            handler.sendEmptyMessage(1);
                        }


                    });
                } else
                    AppValue.showToast(mContext, "请选择满意或者不满意");
                break;

        }
    }
}
