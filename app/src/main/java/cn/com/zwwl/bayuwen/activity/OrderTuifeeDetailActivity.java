package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.order.TuifeeApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.TuifeeModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;

/**
 * 退费的详情页面
 */
public class OrderTuifeeDetailActivity extends BaseActivity {

    @BindView(R.id.tui_detail_op1)
    TextView tuiDetailOp1;
    @BindView(R.id.tui_detail_price)
    TextView tuiDetailPrice;
    @BindView(R.id.tui_d_seekbar)
    SeekBar seekBar;
    @BindView(R.id.tui_type)
    TextView tui_type;
    @BindView(R.id.tui_d_thumb1)
    View seekBarThumb1;
    @BindView(R.id.tui_d_thumb2)
    View seekBarThumb2;
    @BindView(R.id.tui_text)
    TextView tuiTxt;
    @BindView(R.id.item_order_tag)
    TextView itemOrderTag;
    @BindView(R.id.item_order_title)
    TextView itemOrderTitle;
    @BindView(R.id.item_order_pic)
    ImageView itemOrderPic;
    @BindView(R.id.item_order_teacher)
    TextView itemOrderTeacher;
    @BindView(R.id.item_order_xiaoqu)
    TextView itemOrderXiaoqu;
    @BindView(R.id.item_order_date)
    TextView itemOrderDate;
    @BindView(R.id.item_order_time)
    TextView itemOrderTime;
    @BindView(R.id.tui_detail_op2)
    TextView tuiDetailOp2;
    @BindView(R.id.tui_detail_op3)
    TextView tuiDetailOp3;
    @BindView(R.id.tui_detail_op4)
    TextView tuiDetailOp4;
    @BindView(R.id.tui_detail_op5)
    TextView tuiDetailOp5;

    private String id, oid, kid;
    private TuifeeModel tuifeeModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuifee_detail);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("OrderTuifeeDetailActivity_id");
        oid = getIntent().getStringExtra("OrderTuifeeDetailActivity_oid");
        kid = getIntent().getStringExtra("OrderTuifeeDetailActivity_kid");
        initView();
        initData();

    }

    private void initView() {
        findViewById(R.id.tui_d_back).setOnClickListener(this);
        findViewById(R.id.bodadianhua).setOnClickListener(this);

        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        seekBar.setMax(100);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.order_d_back:
                finish();
                break;
            case R.id.bodadianhua:
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    tuiDetailOp1.setText("￥" + Double.valueOf(tuifeeModel.getRefund_fee()) / 100);
                    tuiDetailPrice.setText("￥" + Double.valueOf(tuifeeModel.getRefund_fee()) / 100);
                    tui_type.setText(tuifeeModel.getType() == 1 ? "原路退回" : "账户退回");
                    KeModel model = tuifeeModel.getKeModel();
                    itemOrderTag.setText(model.getTagTxt());
                    itemOrderTitle.setText(model.getTitle());
                    itemOrderTeacher.setText(model.getTname());
                    itemOrderDate.setText(CalendarTools.format(Long.valueOf(model.getStartPtime()),
                            "yyyy-MM-dd") + " 至 " + CalendarTools.format(Long.valueOf(model
                                    .getEndPtime()),
                            "yyyy-MM-dd"));
                    itemOrderTime.setText(model.getClass_start_at() + " - " + model.getClass_end_at
                            ());
                    itemOrderXiaoqu.setText(model.getSchool());
                    tuiDetailOp2.setText(tuifeeModel.getReason());
                    tuiDetailOp3.setText("￥" + Double.valueOf(tuifeeModel.getRefund_fee()) / 100);
                    tuiDetailOp4.setText(tuifeeModel.getCreated_at());
                    tuiDetailOp5.setText(tuifeeModel.getRefund_no());

                    if (tuifeeModel.getState() == 0) {// 待审核
                        seekBarThumb1.setBackgroundResource(R.drawable.dot_gray);
                        seekBar.setProgressDrawable(getResources().getDrawable(R.drawable
                                .seekbar_gray));
                        seekBar.setThumb(getResources().getDrawable(R.drawable
                                .dot_gray));
                        seekBar.setPadding(16, 16, 16, 16);
                    } else if (tuifeeModel.getState() == 1) {// 退款中


                    } else if (tuifeeModel.getState() == 2) {// 完成
                        seekBarThumb2.setBackgroundResource(R.drawable.dot_gold);
                        seekBar.setProgressDrawable(getResources().getDrawable(R.drawable
                                .seekbar_gold));
                        seekBar.setPadding(16, 16, 16, 16);
                    } else if (tuifeeModel.getState() == 3) {// 拒绝

                        tuiTxt.setText(R.string.tui_detail_status4);
                    }
                    break;
            }
        }
    };


    @Override
    protected void initData() {
        showLoadingDialog(true);
        new TuifeeApi(mContext, id, oid, kid, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                showLoadingDialog(false);
                if (entry != null && entry instanceof TuifeeModel) {
                    tuifeeModel = (TuifeeModel) entry;
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
            }
        });
    }

}
