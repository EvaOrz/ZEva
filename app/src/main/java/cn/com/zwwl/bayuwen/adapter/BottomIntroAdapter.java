package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.LastIntromodel;

public class BottomIntroAdapter extends BaseAdapter {
    private Context mContext;
    private List<LastIntromodel> data;
    private LayoutInflater mInflater;
    /**
     * 页数下标,从0开始(当前是第几页)
     */
    private int curIndex;
    /**
     * 每一页显示的个数
     */
    private int pageSize;

    public BottomIntroAdapter(Context context, List<LastIntromodel> data, int curIndex, int pageSize) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.data = data;
        this.curIndex = curIndex;
        this.pageSize = pageSize;
    }

    public void setData(List<LastIntromodel> data) {
        this.data = data;
    }

    /**
     * 先判断数据集的大小是否足够显示满本页,如果够，则直接返回每一页显示的最大条目个数pageSize,如果不够，则有几项就返回几,(也就是最后一页的时候就显示剩余item)
     */
    public int getCount() {
        return data.size() > (curIndex + 1) * pageSize ? pageSize : (data.size() - curIndex * pageSize);

    }

    public Object getItem(int position) {
        return data.get(position + curIndex * pageSize);
    }

    public long getItemId(int position) {
        return position + curIndex * pageSize;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        BottomIntroAdapter.ViewHolder viewHolder;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.bottom_grid_item, null);
            viewHolder = new BottomIntroAdapter.ViewHolder();
            viewHolder.bottom_grid_item_iv = (ImageView) convertView.findViewById(R.id.bottom_grid_item_iv);
            viewHolder.bottom_grid_item_name = (TextView) convertView.findViewById(R.id.bottom_grid_item_name);
            viewHolder.bottom_grid_item_desc = (TextView) convertView.findViewById(R.id.bottom_grid_item_desc);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BottomIntroAdapter.ViewHolder) convertView.getTag();
        }
        /**
         * 在给View绑定显示的数据时，计算正确的position = position + curIndex * pageSize
         */
        int pos = position + curIndex * pageSize;
        if (!TextUtils.isEmpty(data.get(pos).getImg())) {
            ImageLoader.display(mContext, viewHolder.bottom_grid_item_iv, data.get(pos).getImg());
        }
        viewHolder.bottom_grid_item_name.setText(data.get(pos).getName());
        viewHolder.bottom_grid_item_desc.setText(data.get(pos).getDesc());
        return convertView;
    }

    class ViewHolder {
        ImageView bottom_grid_item_iv;
        TextView bottom_grid_item_name;
        TextView bottom_grid_item_desc;
    }

}
