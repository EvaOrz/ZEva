package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

import cn.com.zwwl.bayuwen.listener.OnItemClickListener;

/**
 * Created by lousx on 2016/8/5.
 */
public abstract class BaseRecylcerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    protected Context mContext;
    protected List<T> list;
    protected LayoutInflater inflater;
    public OnItemClickListener onItemClickListener;

    public List<T> getList() {
        return list;
    }

    public BaseRecylcerViewAdapter(Context mContext, List<T> list){
        this.list = list;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    /**
     * 添加
     *
     * @param mList
     */
    public void appendData(List<T> mList) {
        if (mList != null) {
            this.list.addAll(mList);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加
     *
     * @param mList
     */
    public void appendDataHead(List<T> mList) {
        if (mList != null) {
            for(int i = mList.size() - 1; i >= 0; i--) {
                this.list.add(0, mList.get(i));
            }
//            this.list.addAll(mList);
        }
    }

    /**
     * 清空
     */
    public void clear() {
        if (list != null) {
            this.list.clear();
        }
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    //设置点击事件
    protected void setItemClickView(View view, final int position){
        //设置点击事件
        if (onItemClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.setOnItemClickListener(view, position);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.setOnLongItemClickListener(view, position);
                    return false;
                }
            });
        }
    }

    //向外提供设置监听的方法
    public void setOnItemClickListener(OnItemClickListener onitemclicklistener) {
        this.onItemClickListener = onitemclicklistener;
    }
}
