package cn.com.zwwl.bayuwen.adapter;

/**
 * lmj  on 2018/6/22
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.CitySortModel;

public class HotCityAdapter extends BaseAdapter {

    private List<CitySortModel.HotcityBean> cityEntities;
    private LayoutInflater inflater;

    public HotCityAdapter(Context mContext, List<CitySortModel.HotcityBean> cityEntities) {
        this.cityEntities = cityEntities;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return cityEntities == null ? 0 : cityEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return cityEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.hotcity_gridview_item, null);
            holder.hotcityName = convertView.findViewById(R.id.hotcity_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.hotcityName.setText(cityEntities.get(position).getName());

        return convertView;
    }

    class ViewHolder {

        TextView hotcityName;


    }
}
