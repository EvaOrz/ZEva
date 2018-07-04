package cn.com.zwwl.bayuwen.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
    private List<CitySortModel.HotcityBean> hotcityBeans = new ArrayList<>();
    private Context mContext;
    private ListView listView;
    private String current = "北京";

    private static final int TYPE_A = 0;
    //itemB类的type标志
    private static final int TYPE_B = 1;
    private static final int TYPE_C = 2;
    private String locationCity;


    public CityAdapter(Context mContext, String locationCity, List<CitySortModel.CityBean>
            cityBeans, ListView listView, List<CitySortModel.HotcityBean> hotcityBeans) {
        this.mContext = mContext;
        this.cityBeans = cityBeans;
        this.listView = listView;
        this.hotcityBeans = hotcityBeans;
        this.locationCity = locationCity;

    }

    //获取数据设配器中条目类型的总数
    @Override
    public int getViewTypeCount() {

        return super.getViewTypeCount() + 2;
    }


    //指定索引指向的条目的类型（0代表复用系统）
    @Override
    public int getItemViewType(int position) {
        int result = 0;
        if (position == 0) {
            //0代表纯文本条目
            result = TYPE_A;

        } else if (position == 1) {
            result = TYPE_B;

        } else if (position >= 2) {
            result = TYPE_C;
        }
        return result;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param
     */
    public void updateListView(List<CitySortModel.CityBean> cityBeans, List<CitySortModel
            .HotcityBean> hotcityBeans) {
        this.cityBeans = cityBeans;
        this.hotcityBeans = hotcityBeans;
        notifyDataSetChanged();
    }

    public int getCount() {
        return cityBeans.size();
    }

    public Object getItem(int position) {
        return cityBeans.get(position);
    }

    public long getItemId(int position) {
        return position + 1;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder1 viewHolder1;
        ViewHolder2 viewHolder2;
        ViewHolder viewHolder;
        int type = getItemViewType(position);

        viewHolder1 = new ViewHolder1();
        viewHolder2 = new ViewHolder2();
        viewHolder = new ViewHolder();

        switch (type) {
            case TYPE_A:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_city1, null);
                viewHolder1.currentname = (TextView) view.findViewById(R.id.city_name);
                view.setTag(viewHolder1);

                break;
            case TYPE_B:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_gridview, null);
                viewHolder2.noScrollGridView = (NoScrollGridView) view.findViewById(R.id
                        .no_scroll_gridview);
                view.setTag(viewHolder2);

                break;
            case TYPE_C:
                if (view == null) {
                    view = LayoutInflater.from(mContext).inflate(R.layout.item_city, null);
                    viewHolder.city_name = (TextView) view.findViewById(R.id.city_name);
                    viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
                    view.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) view.getTag();
                }
                break;
        }


        switch (type) {
            case TYPE_A:
                viewHolder1 = (ViewHolder1) view.getTag();

                viewHolder1.currentname.setText(locationCity);
                viewHolder1.currentname.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        afterPick(locationCity);
                    }
                });


                break;
            case TYPE_B:
                viewHolder2 = (ViewHolder2) view.getTag();
                viewHolder2.noScrollGridView.setAdapter(new HotCityAdapter(mContext, hotcityBeans));
                viewHolder2.noScrollGridView.setOnItemClickListener(new AdapterView
                        .OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long
                            id) {
                        afterPick(hotcityBeans.get(position).getName());
                    }
                });

                break;
            case TYPE_C:

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


                break;
            default:
                break;
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                afterPick(cityBeans.get(position).getName());
            }
        });
        return view;

    }

    private void afterPick(String city) {
        TempDataHelper.setCurrentCity(mContext, city);
        MyApplication.cityStatusChange = true;
        ((Activity) mContext).finish();
    }

    /**
     * item A 的Viewholder
     */
    class ViewHolder1 {
        TextView currentname;

    }

    /**
     * item B 的Viewholder
     */
    class ViewHolder2 {

        NoScrollGridView noScrollGridView;

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
