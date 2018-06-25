package cn.com.zwwl.bayuwen.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CityAdapter;
import cn.com.zwwl.bayuwen.api.CityListApi;
import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.CitySortModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.ToastUtil;
import cn.com.zwwl.bayuwen.view.PinyinComparator;
import cn.com.zwwl.bayuwen.view.SideBar;
import cn.jiguang.net.HttpResponse;

public class CityActivity extends BasicActivityWithTitle {


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
//    //调用系统Api取到当前城市
//    private Handler handler = new Handler() {
//        public void handleMessage(android.os.Message msg) {
//            double[] data = (double[]) msg.obj;
////            showJW.setText("经度：" + data[0] + "\t纬度:" + data[1]);
//
//            List<Address> addList = null;
//            Geocoder ge = new Geocoder(getApplicationContext());
//            try {
//                addList = ge.getFromLocation(data[0], data[1], 1);
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            if (addList != null && addList.size() > 0) {
//                for (int i = 0; i < addList.size(); i++) {
//                    Address ad = addList.get(i);
//                    locationCity = ad.getLocality();
//                }
//            }
//
////            nowCity.setText(latLongString);
//        }
//
//    };


    @Override
    protected int setContentView() {
        return R.layout.activity_city;
    }

    @Override
    protected void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setCustomTitle("选择城市");
        nowCity.setText(TempDataHelper.getCurrentCity(this));

        CityListUrl = UrlUtil.getCityList();
        cityBeans = new ArrayList<CitySortModel.CityBean>();
        hotcityBeans = new ArrayList<CitySortModel.HotcityBean>();
        pinyinComparator = new PinyinComparator();


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

         if (ActivityCompat.checkSelfPermission(CityActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CityActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

               ActivityCompat.requestPermissions(CityActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);

               }else {
                  LocationGPS();
         }


    }

    private void LocationGPS() {
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude(); // 经度
            longitude = location.getLongitude(); // 纬度
            double[] data = {latitude, longitude};
//                        Message msg = handler.obtainMessage();
//                        msg.obj = data;
//                        handler.sendMessage(msg);
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
    }



    @Override
    protected void initData() {
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
                            // sortModel.setSortLetters("热门城市");
                        }
                    }
                    cityBeans = citySortModel.getCity();
                    // // 根据a-z进行排序源数据
                    Collections.sort(cityBeans,
                            pinyinComparator);

                    hotcityBeans = citySortModel.getHotcity();

                    cityAdapter = new CityAdapter(CityActivity.this, locationCity, cityBeans, countryLvcountry, hotcityBeans);
                    countryLvcountry.setAdapter(cityAdapter);

                } else {
                    ToastUtil.showShortToast("暂无数据");
                }
            }

        });

    }

    private void HttpDate(String a) {
        new CityListApi(this, CityListUrl, a, new ResponseCallBack<CitySortModel>() {
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
                            // sortModel.setSortLetters("热门城市");
                        }
                    }
                    cityBeans = citySortModel.getCity();
                    // // 根据a-z进行排序源数据
                    Collections.sort(cityBeans,
                            pinyinComparator);

                    hotcityBeans = citySortModel.getHotcity();

                    cityAdapter = new CityAdapter(CityActivity.this, locationCity, cityBeans, countryLvcountry, hotcityBeans);
                    countryLvcountry.setAdapter(cityAdapter);

                } else {
                    ToastUtil.showShortToast("暂无数据");
                }
            }

        });
    }


    @Override
    protected void setListener() {
//        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//
//                   // HttpDate(v.getText().toString().trim());
//                    return true;
//                }
//                return false;
//            }
//        });


    }

    //权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    LocationGPS();
                    }
                }

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void close() {
        super.close();
        finish();
    }
}
