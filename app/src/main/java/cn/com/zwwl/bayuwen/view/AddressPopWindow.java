package cn.com.zwwl.bayuwen.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.util.AddressTools;
import cn.com.zwwl.bayuwen.util.AddressTools.*;
import cn.com.zwwl.bayuwen.widget.wheel.OnWheelChangedListener;
import cn.com.zwwl.bayuwen.widget.wheel.OnWheelScrollListener;
import cn.com.zwwl.bayuwen.widget.wheel.WheelView;
import cn.com.zwwl.bayuwen.widget.wheel.adapters.AbstractWheelTextAdapter;

/**
 * 地区选择控件
 * 支持省市区三级筛选
 */
public class AddressPopWindow implements View.OnClickListener {

    private Context mContext;
    private PopupWindow window;
    private OnAddressCListener listener;
    private AddressTools addressTools;
    private int type = 0;// 0：三级区间 1：二级区间

    private WheelView wvProvince, wvCitys, wvDist;
    private AddressTextAdapter provinceAdapter;
    private AddressTextAdapter cityAdapter;
    private AddressTextAdapter distAdapter;

    private ProvinceModel curProvince;
    private CityModel curCity;
    private DistModel curDist;

    private int maxsize = 20;
    private int minsize = 12;

    public ArrayList<ProvinceModel> arrProvinces = new ArrayList<>();// 当前的省列表
    private ArrayList<CityModel> arrCitys = new ArrayList<>();// 当前的市列表
    private ArrayList<DistModel> arrDists = new ArrayList<>();// 当前的区列表

    public AddressPopWindow(Context context, int type, OnAddressCListener listener) {
        mContext = context;
        this.listener = listener;
        init();
    }

    /**
     * 回调接口
     *
     * @author Administrator
     */
    public interface OnAddressCListener {
        public void onClick(ProvinceModel province, CityModel city, DistModel dist);
    }


    public void init() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.pop_address, null);
        window = new PopupWindow(view, RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setAnimationStyle(R.style.fetch_image_popup_anim);
        window.update();
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        wvProvince = view.findViewById(R.id.wv_address_province);
        wvCitys = view.findViewById(R.id.wv_address_city);
        wvDist = view.findViewById(R.id.wv_address_dist);
        view.findViewById(R.id.btn_myinfo_sure).setOnClickListener(this);
        view.findViewById(R.id.btn_myinfo_cancel).setOnClickListener(this);

        addressTools = new AddressTools(mContext);
        arrProvinces.clear();
        arrProvinces.addAll(addressTools.initProvinceList());
        resetProvince(0);

        provinceAdapter = new AddressTextAdapter(mContext, arrProvinces, null,
                null, 0, 0);
        wvProvince.setVisibleItems(5);
        wvProvince.setViewAdapter(provinceAdapter);
        cityAdapter = new AddressTextAdapter(mContext, null, arrCitys,
                null, 0, 1);
        wvCitys.setVisibleItems(5);
        wvCitys.setViewAdapter(cityAdapter);
        distAdapter = new AddressTextAdapter(mContext, null, null,
                arrDists, 0, 2);
        wvDist.setVisibleItems(5);
        wvDist.setViewAdapter(distAdapter);

        wvProvince.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                resetProvince(wheel.getCurrentItem());

                setTextviewSize(curProvince.getPtxt(), provinceAdapter);
            }
        });

        wvProvince.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) provinceAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, provinceAdapter);
            }
        });

        wvCitys.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                resetCity(wheel.getCurrentItem());

                setTextviewSize(curCity.getCtxt(), cityAdapter);
            }
        });

        wvCitys.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) cityAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, cityAdapter);
            }
        });
        wvDist.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                setTextviewSize((String) distAdapter.getItemText(wheel.getCurrentItem()),
                        distAdapter);
            }
        });

        wvDist.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) distAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, distAdapter);
            }
        });
    }

    /**
     * 省份变化
     *
     * @param i
     */
    private void resetProvince(int i) {
        curProvince = arrProvinces.get(i);

        arrCitys.clear();
        arrCitys.addAll(addressTools.initCityList(curProvince.getPid()));
        curCity = arrCitys.get(0);

        arrDists.clear();
        arrDists.addAll(addressTools.initDistList(curCity.getCid()));
        curDist = arrDists.get(0);

        cityAdapter = new AddressTextAdapter(mContext, null, arrCitys,
                null, 0, 1);
        wvCitys.setVisibleItems(5);
        wvCitys.setViewAdapter(cityAdapter);

        distAdapter = new AddressTextAdapter(mContext, null, null,
                arrDists, 0, 2);
        wvDist.setVisibleItems(5);
        wvDist.setViewAdapter(distAdapter);
    }

    /**
     * 城市变化
     *
     * @param i
     */
    private void resetCity(int i) {
        curCity = arrCitys.get(i);

        arrDists.clear();
        arrDists.addAll(addressTools.initDistList(curCity.getCid()));
        curDist = arrDists.get(0);

        distAdapter = new AddressTextAdapter(mContext, null, null,
                arrDists, 0, 2);
        wvDist.setVisibleItems(5);
        wvDist.setViewAdapter(distAdapter);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_myinfo_sure:
                listener.onClick(curProvince, curCity, curDist);
                break;
        }
        window.dismiss();
    }

    private class AddressTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<ProvinceModel> plist;
        ArrayList<CityModel> clist;
        ArrayList<DistModel> dlist;
        int type = 0;

        protected AddressTextAdapter(Context context, ArrayList<ProvinceModel> list1,
                                     ArrayList<CityModel> list2, ArrayList<DistModel> list3, int
                                             currentItem, int type) {
            super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
            this.type = type;
            this.plist = list1;
            this.clist = list2;
            this.dlist = list3;
            setItemTextResource(R.id.tempValue);
        }


        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            int i = 0;
            if (type == 0) {
                i = plist.size();
            } else if (type == 1) {
                i = clist.size();
            } else if (type == 2) {
                i = dlist.size();
            }
            return i;
        }

        @Override
        protected CharSequence getItemText(int index) {
            String cc = "";
            if (type == 0) {
                cc = plist.get(index).getPtxt();
            } else if (type == 1) {
                cc = clist.get(index).getCtxt();
            } else if (type == 2) {
                cc = dlist.get(index).getDtxt();
            }
            return cc;
        }

    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, AddressTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(24);
            } else {
                textvew.setTextSize(14);
            }
        }
    }


}
