package cn.com.zwwl.bayuwen.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CheckScrollAdapter;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.widget.NoScrollListView;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 退费理由option window
 */
public class TuifeeReasinPopWindow {

    private Context mContext;
    private PopupWindow window;
    private EditText editText;
    private RadioButton yuanluBt, accountBt;
    private OnReasonPickListener onReasonPickListener;
    private List<CheckStatusModel> datas = new ArrayList<>();
    private TuifeeReasonAdapter adapter;
    private String detail_id;// 退费id

    public interface OnReasonPickListener {
        // type:0原路 1账户
        public void onReasonPick(String reason, String detail_id, int type);
    }

    public TuifeeReasinPopWindow(Context context, List<String> datas, String detail_id,
                                 OnReasonPickListener
                                         onReasonPickListener) {
        this.mContext = context;
        this.detail_id = detail_id;
        this.onReasonPickListener = onReasonPickListener;
        for (String name : datas) {
            this.datas.add(new CheckStatusModel(name));
        }
        init();
    }

    public void init() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.pop_tuifei_option, null);
        view.findViewById(R.id.pop_tuifee_close)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        window.dismiss();
                    }
                });
        yuanluBt = view.findViewById(R.id.tuifee_bt1);
        accountBt = view.findViewById(R.id.tuifee_bt2);
        editText = view.findViewById(R.id.pop_tuifee_ev);
        view.findViewById(R.id.pop_tuifee_commit)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String resons = "";
                        for (CheckStatusModel c : datas) {
                            if (c.isCheckStatus()) {
                                resons += c.getName() + ",";
                            }
                        }
                        if (!TextUtils.isEmpty(editText.getText().toString())) {
                            resons += editText.getText().toString();
                        }
                        int type = yuanluBt.isChecked() ? 0 : 1;
                        onReasonPickListener.onReasonPick(resons, detail_id, type);
                        window.dismiss();
                    }
                });


        NoScrollListView listView = view.findViewById(R.id.pop_tuifee_list);
        adapter = new TuifeeReasonAdapter(mContext, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.getTag() != null && buttonView.getTag() instanceof String) {
                    String tag = (String) buttonView.getTag();
                    for (int i = 0; i < datas.size(); i++) {
                        if (tag.equals(datas.get(i).getName())) {
                            datas.get(i).setCheckStatus(isChecked);
                        }
                    }
                }
            }
        });
        listView.setAdapter(adapter);
        adapter.setData(datas);

        window = new PopupWindow(view, RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.FILL_PARENT);
        window.setFocusable(true);

        window.setOutsideTouchable(true);
        window.update();
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(view, Gravity.BOTTOM, 0, 0);

    }

    public class TuifeeReasonAdapter extends CheckScrollAdapter<CheckStatusModel> {
        protected Context mContext;
        private CompoundButton.OnCheckedChangeListener listener;

        public TuifeeReasonAdapter(Context context, CompoundButton.OnCheckedChangeListener
                listener) {
            super(context);
            this.listener = listener;
            mContext = context;
        }

        public void setData(List<CheckStatusModel> mItemList) {
            clear();
            isScroll = false;
            synchronized (mItemList) {
                for (CheckStatusModel item : mItemList) {
                    add(item);
                }
                notifyDataSetChanged();
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final CheckStatusModel item = getItem(position);

            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout
                    .item_tuifee_reason);
            TextView title = viewHolder.getView(R.id.tuifee_reson);
            CheckBox checkBox = viewHolder.getView(R.id.tuifee_reson_check);
            title.setText(item.getName());
            checkBox.setTag(item.getName());
            checkBox.setOnCheckedChangeListener(listener);

            return viewHolder.getConvertView();
        }

        public boolean isScroll() {
            return isScroll;
        }

    }

    public class CheckStatusModel extends Entry {
        private String name;
        private boolean checkStatus = false;

        public CheckStatusModel(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(boolean checkStatus) {
            this.checkStatus = checkStatus;
        }
    }

}
