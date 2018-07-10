package cn.com.zwwl.bayuwen.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.model.CitySortModel;
import cn.com.zwwl.bayuwen.widget.NoScrollGridView;

/**
 * lmj  on 2018/6/21
 */
public class CityAdapter extends BaseAdapter {
    private List<CitySortModel.CityBean> cityBeans = new ArrayList<>();
    private Context mContext;
    private ListView listView;


    public CityAdapter(Context mContext, List<CitySortModel.CityBean>
            cityBeans, ListView listView) {
        this.mContext = mContext;
        this.cityBeans = cityBeans;
        this.listView = listView;

    }


    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param
     */
    public void updateListView(List<CitySortModel.CityBean> cityBeans, List<CitySortModel
            .HotcityBean> hotcityBeans) {
        this.cityBeans = cityBeans;
        notifyDataSetChanged();
    }

    public int getCount() {
        return cityBeans.size();
    }

    public Object getItem(int position) {
        return cityBeans.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup arg2) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_city, null);
            viewHolder.city_name = (TextView) convertView.findViewById(R.id.city_name);
            viewHolder.tvLetter = (TextView) convertView.findViewById(R.id.catalog);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(); // 获取，通过ViewHolder找到相应的控件
        }

        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(cityBeans.get(position).getInitial());
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }

        viewHolder.city_name.setText(this.cityBeans.get(position).getName());


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                afterPick(cityBeans.get(position).getName());
            }
        });
        return convertView;

    }


    private void afterPick(String city) {
        TempDataHelper.setCurrentCity(mContext, city);
        MyApplication.cityStatusChange = true;
        ((Activity) mContext).finish();
    }


    class ViewHolder {
        TextView tvLetter;
        TextView city_name;
    }


    abstract class MyClickListener implements View.OnClickListener {
        int position;
        ViewHolder holder;

        public MyClickListener(ViewHolder h, int p) {
            position = p;
            holder = h;
        }

        @Override
        public void onClick(View v) {
            click(holder, v, position);
        }

        abstract void click(ViewHolder h, View v, int p);

    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        if (cityBeans == null) {
            return 0;
        } else
            return cityBeans.get(position).getInitial().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = cityBeans.get(i).getInitial();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    public List<CitySortModel.CityBean> getInstance() {
        return cityBeans;
    }


}
