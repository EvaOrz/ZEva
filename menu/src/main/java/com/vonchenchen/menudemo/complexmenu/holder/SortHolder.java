package com.vonchenchen.menudemo.complexmenu.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.vonchenchen.menudemo.R;

import java.util.List;

/**
 *
 * 综合排序
 * Created by vonchenchen on 2016/4/5 0005.
 */
public class SortHolder extends BaseWidgetHolder<List<String>>{

    private OnSortInfoSelectedListener mOnSortInfoSelectedListener;

    private ListView listView;
    private List<String> mDataList;

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
                if(mOnSortInfoSelectedListener != null){
                    String text = mDataList.get(mRightSelectedIndex);
                    mOnSortInfoSelectedListener.onSortInfoSelected(text);
                }
            }
        });
        return view;
    }

    @Override
    public void refreshView(List<String> data) {
    }

    public void refreshData(List<String> data, int rightSelectedIndex){

        this.mDataList = data;

        mRightAdapter = new SortHolder.RightAdapter(data, mRightSelectedIndex);

        listView.setAdapter(mRightAdapter);
    }

    public void setOnSortInfoSelectedListener(OnSortInfoSelectedListener onSortInfoSelectedListener){
        this.mOnSortInfoSelectedListener = onSortInfoSelectedListener;
    }

    public interface OnSortInfoSelectedListener{
        void onSortInfoSelected(String info);
    }

    private class RightAdapter extends BaseAdapter {

        private List<String> mRightDataList;

        public RightAdapter(List<String> list, int rightSelectedIndex){
            this.mRightDataList = list;
            mRightSelectedIndex = rightSelectedIndex;
        }

        public void setDataList(List<String> list, int rightSelectedIndex){
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
            if(convertView == null){
                holder = new SortHolder.RightViewHolder();
                convertView = View.inflate(mContext, R.layout.layout_child_menu_item, null);
                holder.rightText = convertView.findViewById(R.id.child_textView);
                convertView.setTag(holder);
            }else{
                holder = (SortHolder.RightViewHolder) convertView.getTag();
            }

            holder.rightText.setText(mRightDataList.get(position));
            return convertView;
        }
    }

    private static class RightViewHolder{
        TextView rightText;
    }
}
