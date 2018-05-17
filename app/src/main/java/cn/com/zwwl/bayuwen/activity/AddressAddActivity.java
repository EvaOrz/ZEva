package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.widget.AutoTextGroupView;

/**
 * 添加地址页面
 */
public class AddressAddActivity extends BaseActivity {

    private AutoTextGroupView tagView;
    private List<AddressTag> tagDatas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_add);
        initData();
        initView();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.add_address_back:
                finish();
                break;
            case R.id.add_address_save:
                break;
        }

    }


    @Override
    protected void initData() {
        if (tagDatas.size() == 0){
            tagDatas.add(new AddressTag("家", false));
            tagDatas.add(new AddressTag("公司", false));
            tagDatas.add(new AddressTag("学校", false));
        }
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 10, 10, 10);
        for (AddressTag addressTag : tagDatas) {
            tagView.addView(getTextView(addressTag), lp);
        }
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


    private void initView() {
        findViewById(R.id.add_address_back).setOnClickListener(this);
        findViewById(R.id.add_address_save).setOnClickListener(this);
        tagView = findViewById(R.id.tag_view);


    }


    /**
     * 初始化标签textview
     *
     * @return
     */
    private TextView getTextView(AddressTag addressTag) {
        TextView view = new TextView(this);
        view.setTag(addressTag);
        view.setText(addressTag.getTagTxt());

        if (addressTag.isCheck()){
            view.setTextColor(getResources().getColor(R.color.white));
            view.setBackgroundResource(R.drawable.gold_circle);
        }else{
            view.setTextColor(getResources().getColor(R.color.gray_dark));
            view.setBackgroundResource(R.drawable.gray_xiankuang);
        }
        view.setTextSize(R.dimen.text_size_14);
        view.setGravity(Gravity.CENTER);
        view.setPadding(20, 10, 20, 10);

        // 点击跳转
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AddressTag o = (AddressTag) v.getTag();
                for (int i = 0; i < tagDatas.size(); i++) {
                    if (tagDatas.get(i).getTagTxt().equals(o.getTagTxt())) {
                        boolean origin = tagDatas.get(i).isCheck;
                        tagDatas.get(i).setCheck(!origin);
                    }else
                        tagDatas.get(i).setCheck(false);
                }
                handler.sendEmptyMessage(0);
            }
        });
        return view;
    }

    public class AddressTag extends Entry {
        private String tagTxt = "";
        private boolean isCheck = false;

        public AddressTag(String tagTxt, boolean isCheck) {
            this.tagTxt = tagTxt;
            this.isCheck = isCheck;
        }

        public String getTagTxt() {
            return tagTxt;
        }

        public void setTagTxt(String tagTxt) {
            this.tagTxt = tagTxt;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }
    }


}
