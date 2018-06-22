package cn.com.zwwl.bayuwen.view.selectmenu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

import cn.com.zwwl.bayuwen.R;

/**
 * 综合排序
 * Created by vonchenchen on 2016/4/5 0005.
 */
public class SortHolder extends BaseWidgetHolder<List<SelectTempModel>> {

    private OnSortInfoSelectedListener mOnSortInfoSelectedListener;

    private ListView listView;
    private List<SelectTempModel> mDataList;

    private RightAdapter mRightAdapter;

    private int mRightSelectedIndex;

    public SortHolder(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.layout_holder_sort, null);
        listView = view.findViewById(R.id.listView1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mRightSelectedIndex = position;
                if (mOnSortInfoSelectedListener != null) {
                    mOnSortInfoSelectedListener.onSortInfoSelected(mDataList.get(mRightSelectedIndex));
                }
            }
        });
        return view;
    }

    @Override
    public void refreshView(List<SelectTempModel> data) {
    }

    public void refreshData(List<SelectTempModel> data, int rightSelectedIndex) {

        this.mDataList = data;
        mRightSelectedIndex = rightSelectedIndex;
        mRightAdapter = new SortHolder.RightAdapter(data, mRightSelectedIndex);

        listView.setAdapter(mRightAdapter);
    }

    public void setOnSortInfoSelectedListener(OnSortInfoSelectedListener
                                                      onSortInfoSelectedListener) {
        this.mOnSortInfoSelectedListener = onSortInfoSelectedListener;
    }

    public interface OnSortInfoSelectedListener {
        void onSortInfoSelected(SelectTempModel info);
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

            SortHolder.RightViewHolder holder;
            if (convertView == null) {
                holder = new SortHolder.RightViewHolder();
                convertView = View.inflate(mContext, R.layout.layout_child_menu_item, null);
                holder.rightText = convertView.findViewById(R.id.child_textView);
                convertView.setTag(holder);
            } else {
                holder = (SortHolder.RightViewHolder) convertView.getTag();
            }

            holder.rightText.setText(mRightDataList.get(position).getText());
            if (mRightSelectedIndex == position) {
                holder.rightText.setTextColor(mContext.getResources().getColor(R.color.gold));
            } else {
                holder.rightText.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
            }
            return convertView;
        }
    }

    private static class RightViewHolder {
        TextView rightText;
    }
}
