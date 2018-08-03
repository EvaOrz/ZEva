package cn.com.zwwl.bayuwen.view.selectmenu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;

/**
 * 科目
 * Created by vonchenchen on 2016/4/5 0005.
 */
public class SubjectHolder extends BaseWidgetHolder<List<List<SelectTempModel>>> {

    private List<List<SelectTempModel>> mDataList;

    private ListView mLeftListView;
    private ListView mRightListView;

    private LeftAdapter mLeftAdapter;
    private RightAdapter mRightAdapter;

    private int mLeftSelectedIndex = 0;
    private int mRightSelectedIndex = 0;
    private int mLeftSelectedIndexRecord = mLeftSelectedIndex;
    private int mRightSelectedIndexRecord = mRightSelectedIndex;

    /**
     * 记录左侧条目背景位置
     */
    private View mLeftRecordView = null;

    //用于首次测量时赋值标志
    private boolean mIsFirstMeasureLeft = true;
    private boolean mIsFirstMeasureRight = true;

    private OnRightListViewItemSelectedListener mOnRightListViewItemSelectedListener;

    public SubjectHolder(Context context) {
        super(context);
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
                if (mLeftRecordView != null) {
                    mLeftRecordView.findViewById(R.id.ll_main).setBackgroundResource(R.color
                            .body_bg);
                    ((TextView) mLeftRecordView.findViewById(R.id.group_textView)).setTextColor
                            (mContext.getResources().getColor(R.color.gray_dark));
                }
                view.findViewById(R.id.ll_main).setBackgroundResource(R.color
                        .white);
                ((TextView) view.findViewById(R.id.group_textView)).setTextColor
                        (mContext.getResources().getColor(R.color.gold));
                mLeftRecordView = view;

                if (mDataList.get(0).get(position).getText().equals("全部")) {// 如果点击全部，直接返回
                    if (mOnRightListViewItemSelectedListener != null) {
                        mOnRightListViewItemSelectedListener.OnRightListViewItemSelected
                                (mDataList.get(0).get(position));
                    }
                    mRightAdapter.setDataList(new ArrayList<SelectTempModel>(), -1);
                    mRightAdapter.notifyDataSetChanged();
                } else {// 不是全部 初始化右边list
                    mRightAdapter.setDataList(mDataList.get(position + 1), mRightSelectedIndex);
                    mRightAdapter.notifyDataSetChanged();
                }
            }
        });

        mRightListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mRightSelectedIndex = position;
                mLeftSelectedIndexRecord = mLeftSelectedIndex;

                if (mOnRightListViewItemSelectedListener != null) {

                    List<SelectTempModel> dataList = mDataList.get(mLeftSelectedIndex + 1);

                    mOnRightListViewItemSelectedListener.OnRightListViewItemSelected
                            (dataList.get(mRightSelectedIndex));
                }
            }
        });

        return view;
    }

    @Override
    public void refreshView(List<List<SelectTempModel>> data) {

    }

    public void refreshData(List<List<SelectTempModel>> data, int leftSelectedIndex, int
            rightSelectedIndex) {

        this.mDataList = data;

        mLeftSelectedIndex = leftSelectedIndex;
        mRightSelectedIndex = rightSelectedIndex;

        mLeftSelectedIndexRecord = mLeftSelectedIndex;
        mRightSelectedIndexRecord = mRightSelectedIndex;

        mLeftAdapter = new LeftAdapter(data.get(0), mLeftSelectedIndex);
        if (mLeftSelectedIndex > -1 && data.get(0).get(mLeftSelectedIndex).getText().equals("全部")) {
            mRightAdapter = new RightAdapter(new ArrayList<SelectTempModel>(), -1);
        } else
            mRightAdapter = new RightAdapter(data.get(mLeftSelectedIndex + 1), mRightSelectedIndex);

        mLeftListView.setAdapter(mLeftAdapter);
        mRightListView.setAdapter(mRightAdapter);
    }

    private class LeftAdapter extends BaseAdapter {

        private List<SelectTempModel> mLeftDataList;

        public LeftAdapter(List<SelectTempModel> list, int leftIndex) {
            this.mLeftDataList = list;
            mLeftSelectedIndex = leftIndex;
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
                holder.leftText.setTextColor(mContext.getResources().getColor(R.color.gold));
                if (mIsFirstMeasureLeft) {
                    mIsFirstMeasureLeft = false;
                    mLeftRecordView = convertView;
                }
            } else {
                holder.leftText.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                holder.backgroundView.setBackgroundResource(R.color.body_bg);  //其他项背景
            }

            return convertView;
        }
    }

    public void clearSelectedInfo() {

    }

    private class RightAdapter extends BaseAdapter {

        private List<SelectTempModel> mRightDataList;

        public RightAdapter(List<SelectTempModel> list, int rightSelectedIndex) {
            this.mRightDataList = list;
            mRightSelectedIndex = rightSelectedIndex;
        }

        public void setDataList(List<SelectTempModel> list, int rightSelectedIndex) {
            this.mRightDataList = list;
            mRightSelectedIndex = rightSelectedIndex;
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
        public View getView(int position, View convertView, ViewGroup parent) {

            RightViewHolder holder;
            if (convertView == null) {
                holder = new RightViewHolder();
                convertView = View.inflate(mContext, R.layout.layout_child_menu_item, null);
                holder.rightText = convertView.findViewById(R.id.child_textView);
                convertView.setTag(holder);
            } else {
                holder = (RightViewHolder) convertView.getTag();
            }

            holder.rightText.setText(mRightDataList.get(position).getText());
            if (mRightSelectedIndex == position && mLeftSelectedIndex == mLeftSelectedIndexRecord) {
                holder.rightText.setTextColor(mContext.getResources().getColor(R.color.gold));
            } else {
                holder.rightText.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
            }

            return convertView;
        }
    }

    private static class LeftViewHolder {
        TextView leftText;
        View backgroundView;
    }

    private static class RightViewHolder {
        TextView rightText;
    }

    public void setOnRightListViewItemSelectedListener(OnRightListViewItemSelectedListener
                                                               onRightListViewItemSelectedListener) {
        this.mOnRightListViewItemSelectedListener = onRightListViewItemSelectedListener;
    }

    public interface OnRightListViewItemSelectedListener {
        void OnRightListViewItemSelected(SelectTempModel selectTempModel);
    }
}
