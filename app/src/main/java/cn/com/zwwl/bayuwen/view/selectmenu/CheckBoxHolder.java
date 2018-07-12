package cn.com.zwwl.bayuwen.view.selectmenu;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.util.Tools;

/**
 * 科目
 */
public class CheckBoxHolder extends BaseWidgetHolder<List<List<SelectTempModel>>> {

    private Context context;
    private List<List<SelectTempModel>> mDataList;

    private ListView mLeftListView;
    private ListView mRightListView;

    private LeftAdapter mLeftAdapter;
    private RightAdapter mRightAdapter;

    private int mLeftSelectedIndex = 0;
    private List<SelectTempModel> mCheckedRecord = new ArrayList<>();// 记录右边选中的所有item

    /**
     * 记录左侧条目背景位置
     */
    private View mLeftRecordView = null;

    //用于首次测量时赋值标志
    private boolean mIsFirstMeasureLeft = true;
    private boolean mIsFirstMeasureRight = true;

    public CheckBoxHolder(Context context) {
        super(context);
        this.context = context;
    }

    public List<SelectTempModel> getCheckedData() {
        return mCheckedRecord;
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.layout_holder_subject, null);
        mLeftListView = view.findViewById(R.id.listView1);
        mRightListView = view.findViewById(R.id.listView2);

        mLeftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mLeftSelectedIndex = position;
                if (mLeftRecordView != null) {// 还原之前选项
                    mLeftRecordView.setBackgroundResource(R.color.body_bg);
                    ((LeftViewHolder) mLeftRecordView.getTag()).leftText.setTextColor(context
                            .getResources().getColor(R.color.gray_dark));
                }
                view.setBackgroundResource(R.color.white);
                ((LeftViewHolder) view.getTag()).leftText.setTextColor(context.getResources()
                        .getColor(R.color.gold));
                mLeftRecordView = view;

                if (mDataList.get(0).get(position).getText().equals("全部")) {// 如果点击全部，直接返回
                    mCheckedRecord.clear();
                    SelectTempModel s = mDataList.get(0).get(position);
                    s.setCheck(true);
                    mCheckedRecord.add(s);
                }
                mRightAdapter.setDataList(mDataList.get(position + 1));
                mRightAdapter.notifyDataSetChanged();

            }
        });

        return view;
    }

    @Override
    public void refreshView(List<List<SelectTempModel>> data) {

    }

    public void refreshData(List<List<SelectTempModel>> data) {
        this.mDataList = data;
        mLeftAdapter = new LeftAdapter(data.get(0));
        mRightAdapter = new RightAdapter(data.get(1));
        mLeftListView.setAdapter(mLeftAdapter);
        mRightListView.setAdapter(mRightAdapter);
        mLeftSelectedIndex = -1;
        mCheckedRecord.clear();
        // 全部的二级选项全部置空....我的天哪...我还要处理多少异常数据
        if (mDataList.get(0).get(0).getText().equals("全部")) {
            mDataList.get(1).clear();
        }
    }

    /**
     * 左边adapter
     */
    private class LeftAdapter extends BaseAdapter {

        private List<SelectTempModel> mLeftDataList;

        public LeftAdapter(List<SelectTempModel> list) {
            this.mLeftDataList = list;
        }

        @Override
        public int getCount() {
            return mLeftDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mLeftDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LeftViewHolder holder;
            if (convertView == null) {
                holder = new LeftViewHolder();
                convertView = View.inflate(mContext, R.layout.layout_normal_menu_item, null);
                holder.leftText = convertView.findViewById(R.id.group_textView);
                holder.backgroundView = convertView.findViewById(R.id.ll_main);
                convertView.setTag(holder);
            } else {
                holder = (LeftViewHolder) convertView.getTag();
            }

            holder.leftText.setText(mLeftDataList.get(position).getText());
            if (mLeftSelectedIndex == position) {
                holder.backgroundView.setBackgroundResource(R.color.white);  //选中项背景
                holder.leftText.setTextColor(context.getResources().getColor(R.color.gold));
                if (position == 0 && mIsFirstMeasureLeft) {
                    mIsFirstMeasureLeft = false;
                    mLeftRecordView = convertView;
                }
            } else {
                holder.leftText.setTextColor(context.getResources().getColor(R.color.gray_dark));
                holder.backgroundView.setBackgroundResource(R.color.body_bg);  //其他项背景
            }

            return convertView;
        }
    }

    /**
     * 右边adapter
     */
    private class RightAdapter extends BaseAdapter {

        private List<SelectTempModel> mRightDataList;

        public RightAdapter(List<SelectTempModel> list) {
            this.mRightDataList = list;

        }

        public void setDataList(List<SelectTempModel> list) {
            this.mRightDataList = list;
        }

        @Override
        public int getCount() {
            return mRightDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mRightDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final RightViewHolder holder;
            holder = new RightViewHolder();
            convertView = View.inflate(mContext, R.layout.layout_child_menu_item, null);
            holder.rightText = convertView.findViewById(R.id.child_textView);
            convertView.setTag(holder);

            final SelectTempModel selectTempModel = mRightDataList.get(position);
            holder.rightText.setText(selectTempModel.getText());
            for (int i = 0; i < mCheckedRecord.size(); i++) {
                if (mCheckedRecord.get(i).getId().equals
                        (selectTempModel.getId())) {
                    if (mCheckedRecord.get(i).isCheck())
                        holder.rightText.setTextColor(context.getResources().getColor(R.color
                                .gold));
                    else {
                        holder.rightText.setTextColor(context.getResources().getColor(R.color
                                .gray_dark));
                    }
                }
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 操作的是当前记录的list，就不需要替换记录
                    if (Tools.listNotNull(mCheckedRecord) && mCheckedRecord.get(0).getId().equals
                            (mRightDataList.get(0).getId())) {

                    } else {
                        mCheckedRecord.clear();
                        mCheckedRecord.addAll(mRightDataList);
                    }
                    boolean originCheck = mCheckedRecord.get(position).isCheck();
                    setCheck(holder, !originCheck, position);
                }
            });
            return convertView;
        }

        private void setCheck(RightViewHolder holder, boolean ischeck, int position) {
            if (ischeck) {
                holder.rightText.setTextColor(context.getResources().getColor(R.color.gold));
            } else {
                holder.rightText.setTextColor(context.getResources().getColor(R.color.gray_dark));
            }
            mCheckedRecord.get(position).setCheck(ischeck);// 更新记录数据

        }


    }

    private static class LeftViewHolder {
        TextView leftText;
        View backgroundView;
    }

    private static class RightViewHolder {
        TextView rightText;
//        CheckBox checkBox;
    }

}
