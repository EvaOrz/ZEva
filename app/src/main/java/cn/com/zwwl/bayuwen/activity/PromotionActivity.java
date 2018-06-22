package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.KeSelectAdapter;
import cn.com.zwwl.bayuwen.listener.OnItemClickListener;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.PromotionModel;

/**
 * 课程的组合优惠页面
 */
public class PromotionActivity extends BaseActivity {

    private LinearLayout menuView;
    private List<LinearLayout> menuItems = new ArrayList<>();
    private LinearLayout recordItem;
    private RecyclerView recyclerView;
    private KeSelectAdapter keSelectAdapter;
    private TextView price1, price2;

    private KeModel keModel;
    private int position = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);
        keModel = (KeModel) getIntent().getSerializableExtra("PromotionActivity_data");
        position = getIntent().getIntExtra("PromotionActivity_position", 0);
        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.promotion_back).setOnClickListener(this);
        findViewById(R.id.promotion_buy).setOnClickListener(this);
        menuView = findViewById(R.id.promotion_menu);
        recyclerView = findViewById(R.id.promotion_listview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper
                .VERTICAL, false));
        menuView = findViewById(R.id.promotion_menu);
        price1 = findViewById(R.id.promotion_price);
        price2 = findViewById(R.id.promotion_price1);


        LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(MyApplication.width,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        int lineWid1 = MyApplication.width - 40;
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(MyApplication.width / 2,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        int lineWid2 = MyApplication.width / 2 - 40;
        int size = keModel.getPromotionModels().size();
        for (int i = 0; i < size; i++) {
            LinearLayout ll = null;
            if (size == 1) {
                ll = getMenuItemView(i, lineWid1);
                menuView.addView(ll, p1);
            } else {
                ll = getMenuItemView(i, lineWid2);
                menuView.addView(ll, p2);
            }
            menuItems.add(ll);
        }
        setCheck();
    }

    private void setCheck() {
        if (recordItem != null) {
            ((TextView) recordItem.getChildAt(0)).setTextColor(mContext.getResources()
                    .getColor(R.color.gray_dark));
            recordItem.getChildAt(1).setBackgroundColor(Color.TRANSPARENT);
        }
        recordItem = menuItems.get(position);
        ((TextView) recordItem.getChildAt(0)).setTextColor(mContext.getResources()
                .getColor(R.color.gold));
        recordItem.getChildAt(1).setBackgroundColor(mContext.getResources()
                .getColor(R.color.gold));


    }

    private LinearLayout getMenuItemView(final int pp, int lineWid) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setTag(pp);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView textView = new TextView(mContext);
        textView.setText("组合课程套餐" + (pp + 1));
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(0, 30, 0, 30);
        textView.setTextSize(14);
        textView.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
        linearLayout.addView(textView);
        View line = new View(mContext);
        line.setLayoutParams(new LinearLayout.LayoutParams(lineWid, 8));
        linearLayout.addView(line);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = (int) v.getTag();
                setCheck();
                initData();
            }
        });
        return linearLayout;

    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    initData();
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.promotion_back:
                finish();
                break;
            case R.id.promotion_buy:
                Intent i = new Intent(mContext, PayActivity.class);
                i.putExtra("TuanPayActivity_promo", keModel.getPromotionModels().get(position));
                i.putExtra("TuanPayActivity_type", 4);
                startActivity(i);
                break;
        }
    }

    @Override
    protected void initData() {
        if (keModel.getPromotionModels().size() > position) {
            final PromotionModel p = keModel.getPromotionModels().get(position);
            price1.setText("套餐合计：￥" + p.getOriginal_price());
            price2.setText("已优惠：￥" + p.getDiscount_price());

            keSelectAdapter = new KeSelectAdapter(mContext, 1, p.getKeModels());
            recyclerView.setAdapter(keSelectAdapter);
            keSelectAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void setOnItemClickListener(View view, int position) {
                    Intent intent = new Intent();
                    intent.putExtra("CourseDetailActivity_id", p.getKeModels().get
                            (position).getKid());
                    intent.setClass(mContext, CourseDetailActivity.class);
                    startActivity(intent);
                }

                @Override
                public void setOnLongItemClickListener(View view, int position) {

                }
            });
        }
    }
}
