package cn.com.zwwl.bayuwen.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CityAdapter;
import cn.com.zwwl.bayuwen.adapter.HotCityAdapter;
import cn.com.zwwl.bayuwen.api.CityListApi;
import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.CitySortModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.ToastUtil;
import cn.com.zwwl.bayuwen.util.UmengLogUtil;
import cn.com.zwwl.bayuwen.view.PinyinComparator;
import cn.com.zwwl.bayuwen.view.SideBar;
import cn.com.zwwl.bayuwen.widget.NoScrollGridView;

public class CityActivity extends BaseActivity {


    @BindView(R.id.now_city)
    TextView nowCity;
    @BindView(R.id.country_lvcountry)
    ListView countryLvcountry;
    @BindView(R.id.dialog)
    TextView dialog;

    @BindView(R.id.sidrbar)
    SideBar sidrbar;
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    @BindView(R.id.search_view)
    EditText searchView;
    @BindView(R.id.id_back)
    ImageView idBack;
    @BindView(R.id.title_name)
    TextView titleName;
    private String CityListUrl;
    private CitySortModel citySortModel;
    private List<CitySortModel.CityBean> cityBeans;
    private List<CitySortModel.HotcityBean> hotcityBeans;
    private CityAdapter cityAdapter;
    private PinyinComparator pinyinComparator;
    private LocationManager locationManager;

    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    private double latitude = 0;

    private double longitude = 0;
    private String locationCity;
    private Location location;


    private View headerView;
    private TextView city_name;
    private NoScrollGridView no_scroll_gridview;
    private HotCityAdapter hotCityAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        setContentView(R.layout.activity_city);
        ButterKnife.bind(this);
        setView();
    }

    protected void setView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        titleName.setText("选择城市");
        nowCity.setText(TempDataHelper.getCurrentCity(this));

        CityListUrl = UrlUtil.getCityList();
        cityBeans = new ArrayList<CitySortModel.CityBean>();
        hotcityBeans = new ArrayList<CitySortModel.HotcityBean>();
        pinyinComparator = new PinyinComparator();
        //listview加载头部文件
        headerView = getLayoutInflater().inflate(R.layout.item_city1, null);
        city_name = headerView.findViewById(R.id.city_name);
        no_scroll_gridview = headerView.findViewById(R.id.no_scroll_gridview);

        setListener();


        sidrbar.setTextView(dialog);

        // 设置右侧触摸监听
        sidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = cityAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    countryLvcountry.setSelection(position);
                }


            }
        });
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        initGPS();
    }

    private void initGPS() {

        if (ActivityCompat.checkSelfPermission(CityActivity.this, Manifest.permission
                .ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (CityActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager
                .PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(CityActivity.this, new String[]{Manifest.permission
                            .ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);

        } else {
            LocationGPS();
        }


    }

    private void LocationGPS() {
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude(); // 经度
            longitude = location.getLongitude(); // 纬度
            double[] data = {latitude, longitude};
            List<Address> addList = null;
            Geocoder ge = new Geocoder(getApplicationContext());
            try {
                addList = ge.getFromLocation(data[0], data[1], 1);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (addList != null && addList.size() > 0) {
                for (int i = 0; i < addList.size(); i++) {
                    Address ad = addList.get(i);
                    locationCity = ad.getLocality();

                }
            }
        }
        city_name.setText(locationCity);
        HttpDate();
    }


    @Override
    protected void initData() {

    }

    private void HttpDate() {
        new CityListApi(this, CityListUrl, new ResponseCallBack<CitySortModel>() {
            @Override
            public void result(CitySortModel ScitySortModel, ErrorMsg errorMsg) {
                if (ScitySortModel != null) {
                    citySortModel = ScitySortModel;
                    //   cityBeans =citySortModel.getCity();
                    // 汉字转换成拼音
                    for (int i = 0; i < citySortModel.getCity().size(); i++) {
                        String s = citySortModel.getCity().get(i).getInitial().toUpperCase();

                        if (s.matches("[A-Z]")) {
                            citySortModel.getCity().get(i).setInitial(s.toUpperCase());
                        } else {
//                           citySortModel.getCity().get(i).setInitial("热门城市");
                        }
                    }
                    cityBeans = citySortModel.getCity();

                    // // 根据a-z进行排序源数据
                    Collections.sort(cityBeans,
                            pinyinComparator);
                    //listview加载头文件
                    hotcityBeans = citySortModel.getHotcity();
                    hotCityAdapter = new HotCityAdapter(CityActivity.this, hotcityBeans);
                    no_scroll_gridview.setAdapter(hotCityAdapter);

                    countryLvcountry.addHeaderView(headerView);
                    cityAdapter = new CityAdapter(CityActivity.this, cityBeans,
                            countryLvcountry);
                    countryLvcountry.setAdapter(cityAdapter);

                } else {
                    ToastUtil.showShortToast("暂无数据");
                }
            }

        });
    }


    protected void setListener() {
        no_scroll_gridview.setOnItemClickListener(new AdapterView
                .OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long
                    id) {
                TempDataHelper.setCurrentCity(mContext, hotcityBeans.get(position).getName());
                MyApplication.cityStatusChange = true;
                finish();
            }
        });
        city_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TempDataHelper.setCurrentCity(mContext, locationCity);
                MyApplication.cityStatusChange = true;
                finish();
            }
        });
        idBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED) {
                    LocationGPS();

                }
                break;
        }

    }



}
