package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CheckScrollAdapter;
import cn.com.zwwl.bayuwen.adapter.MyViewPagerAdapter;
import cn.com.zwwl.bayuwen.api.order.CheckCanTuanApi;
import cn.com.zwwl.bayuwen.api.order.GetTuanDiancodesApi;
import cn.com.zwwl.bayuwen.api.order.MyTuanApi;
import cn.com.zwwl.bayuwen.dialog.DuihuanCodeListDialog;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.AddressModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.GroupBuyModel;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.OrderForMyListModel;
import cn.com.zwwl.bayuwen.model.TuanDianModel;
import cn.com.zwwl.bayuwen.model.TuanForMyListModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 我的团购页面
 */
public class MyTuanActivity extends BaseActivity {
    private ViewPager viewPager;
    private ListView view1, view2;
    private View line1, line2;
    private RadioButton button1, button2;
    private List<View> views = new ArrayList<>();
    private MyViewPagerAdapter adapter;
    private MyTuanAdapter adapter1, adapter2;
    private List<TuanForMyListModel> data1 = new ArrayList<>(), data2 = new ArrayList<>();
    private List<TuanDianModel> tuanDianModelList = new ArrayList<>();// 团购码列表

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tuan);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter1.setData(data1);
                    adapter1.notifyDataSetChanged();
                    adapter2.setData(data2);
                    adapter2.notifyDataSetChanged();
                    break;
                case 1:
                    new DuihuanCodeListDialog(mContext, tuanDianModelList);
                    break;
            }
        }
    };

    private void initView() {
        viewPager = findViewById(R.id.my_tuan_viewpager);
        line1 = findViewById(R.id.my_tuan_line1);
        line2 = findViewById(R.id.my_tuan_line2);
        ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
        view1 = new ListView(mContext);
        view1.setDivider(null);
        view1.setSelector(colorDrawable);
        view2 = new ListView(mContext);
        view2.setDivider(null);
        view2.setSelector(colorDrawable);
        views.add(view1);
        views.add(view2);
        adapter1 = new MyTuanAdapter(mContext);
        adapter2 = new MyTuanAdapter(mContext);
        view1.setAdapter(adapter1);
        view1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goOrderDetail(data1.get(position));
            }
        });
        view2.setAdapter(adapter2);
        view2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goOrderDetail(data2.get(position));
            }
        });

        adapter = new MyViewPagerAdapter(views);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0)
                    button1.setChecked(true);
                else if (position == 1)
                    button2.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        button1 = findViewById(R.id.my_tuan_bt1);
        button2 = findViewById(R.id.my_tuan_bt2);
        button1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    changeRadio(0);

            }
        });
        button2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    changeRadio(1);
            }
        });

        findViewById(R.id.my_tuan_back).setOnClickListener(this);
    }

    /**
     * type 1:未完成 2:已完成
     */
    private void goOrderDetail(TuanForMyListModel tuan) {
        if (TextUtils.isEmpty(tuan.getValid()) || tuan.getValid().equals("0")) {
            showToast("无效的团购码");
            return;
        }
        if (tuan.getState() == 0 && !TextUtils.isEmpty(tuan.getPurchase_code())) {
            if (tuan.getType().equals("1")) {// 拼团
                goPay(tuan.getKeModel(), tuan.getPurchase_code(), 0);
            } else if (tuan.getType().equals("2")) {//垫付
                goPay(tuan.getKeModel(), tuan.getPurchase_code(), 1);
            }
        } else if ((tuan.getState() == 1 || tuan.getState() == 2) && !TextUtils.isEmpty(tuan
                .getOid())) {
            Intent i = new Intent(mContext, OrderDetailActivity.class);
            i.putExtra("OrderDetailActivity_data", tuan.getOid());
            i.putExtra("OrderDetailActivity_type", tuan.getState());
            startActivity(i);
        }
    }

    /**
     * @param keModel
     * @param pcode
     * @param type    0:单独参团 1：垫付参团
     */
    private void goPay(KeModel keModel, String pcode, int type) {
        Intent i = new Intent();
        i.setClass(mContext, PayActivity.class);
        i.putExtra("TuanPayActivity_data", keModel);
        i.putExtra("TuanPayActivity_code", pcode);
        i.putExtra("TuanPayActivity_type", type);
        startActivity(i);
    }


    private void changeRadio(int position) {
        viewPager.setCurrentItem(position);
        if (position == 0) {
            line1.setBackgroundColor(getResources().getColor(R.color.gold));
        } else {
            line1.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
        if (position == 1) {
            line2.setBackgroundColor(getResources().getColor(R.color.gold));
        } else {
            line2.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.my_tuan_back:
                finish();
                break;
        }
    }

    @Override
    protected void initData() {
        showLoadingDialog(true);
        new MyTuanApi(mContext, new MyTuanApi.FetchMyTuanListListener() {
            @Override
            public void setData(List sponsor, List join) {
                showLoadingDialog(false);
                data1.clear();
                data2.clear();
                if (Tools.listNotNull(sponsor)) {
                    data1.addAll(sponsor);
                }
                if (Tools.listNotNull(join)) {
                    data2.addAll(sponsor);
                }
                handler.sendEmptyMessage(0);
            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error != null)
                    showToast(error.getDesc());
            }
        });
    }


    public class MyTuanAdapter extends CheckScrollAdapter<TuanForMyListModel> {
        protected Context mContext;

        public MyTuanAdapter(Context context) {
            super(context);
            mContext = context;
        }


        public void setData(List<TuanForMyListModel> mItemList) {
            clear();
            isScroll = false;
            synchronized (mItemList) {
                for (TuanForMyListModel item : mItemList) {
                    add(item);
                }
            }
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout
                    .item_course_for_tuanlist);
            final TuanForMyListModel model = getItem(position);
            ImageView item_tuan_tag = viewHolder.getView(R.id.item_tuan_tag);
            TextView item_tuan_title = viewHolder.getView(R.id.item_tuan_title);
            TextView item_tuan_code = viewHolder.getView(R.id.item_tuan_code);
            ImageView item_tuan_pic = viewHolder.getView(R.id.item_tuan_pic);
            TextView item_tuan_teacher = viewHolder.getView(R.id.item_tuan_teacher);
            TextView item_tuan_xiaoqu = viewHolder.getView(R.id.item_tuan_xiaoqu);
            TextView item_tuan_date = viewHolder.getView(R.id.item_tuan_date);
            TextView item_tuan_time = viewHolder.getView(R.id.item_tuan_time);
            TextView item_tuan_duihuan = viewHolder.getView(R.id.item_tuan_duihuan);

            TextView yuanjia = viewHolder.getView(R.id.item_tuan_price1);
            TextView tuanjia = viewHolder.getView(R.id.item_tuan_price2);
            TextView dianjia = viewHolder.getView(R.id.item_tuan_price3);

            if (model.getKeModel() != null) {
                item_tuan_tag.setImageResource(model.getKeModel().getTagImg());
                item_tuan_title.setText(model.getKeModel().getTitle());
                ImageLoader.display(mContext, item_tuan_pic, model.getKeModel().getPic());
                item_tuan_teacher.setText(model.getKeModel().getTname());
                item_tuan_xiaoqu.setText(model.getKeModel().getSchool());
                item_tuan_date.setText(CalendarTools.format(Long.valueOf(model.getKeModel()
                                .getStartPtime()),
                        "yyyy-MM-dd") + " 至 " + CalendarTools.format(Long.valueOf(model
                                .getKeModel().getEndPtime()),
                        "yyyy-MM-dd"));
                item_tuan_time.setText(model.getKeModel().getClass_start_at() + " - " + model
                        .getKeModel().getClass_end_at());
                yuanjia.setText("原价：￥" + Tools.getTwoDecimal(Double.valueOf(model.getKeModel()
                        .getBuyPrice())));
            }
            if (model.getType().equals("1")) {
                item_tuan_code.setText("拼团码：" + model.getPurchase_code());
                item_tuan_duihuan.setVisibility(View.GONE);
            } else {
                item_tuan_duihuan.setVisibility(View.VISIBLE);
                item_tuan_code.setText("垫付的团购");
            }
            if (model.getDiscount() != null) {
                tuanjia.setText("团购价：￥" + Tools.getTwoDecimal(model.getDiscount()
                        .getDiscount_price()));
                dianjia.setText("垫付金额：￥" + Tools.getTwoDecimal(model
                        .getDianfu()));
            }
            item_tuan_duihuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDuihuanCodes(model.getKeModel().getKid(), model.getPurchase_code());
                }
            });

            return viewHolder.getConvertView();
        }

        public boolean isScroll() {
            return isScroll;
        }
    }

    private void getDuihuanCodes(String kid, String tuanCode) {
        showLoadingDialog(true);
        new GetTuanDiancodesApi(mContext, kid, tuanCode, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                showLoadingDialog(false);
                tuanDianModelList.clear();
                if (Tools.listNotNull(list)) {
                    tuanDianModelList.addAll(list);
                    handler.sendEmptyMessage(1);
                } else {
                    showToast("兑换码列表为空");
                }

            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error != null)
                    showToast(error.getDesc());

            }
        });
    }
}
