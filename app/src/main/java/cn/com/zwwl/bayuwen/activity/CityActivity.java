package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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


    @Override
    protected int setContentView() {
        return R.layout.activity_city;
    }

    @Override
    protected void initView() {
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

                    cityAdapter = new CityAdapter(CityActivity.this, cityBeans, countryLvcountry, hotcityBeans);
                    countryLvcountry.setAdapter(cityAdapter);

                } else {
                    ToastUtil.showShortToast("暂无数据");
                }
            }

        });

    }

    private void HttpDate(String a) {
        new CityListApi(this, CityListUrl,a, new ResponseCallBack<CitySortModel>() {
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

                    cityAdapter = new CityAdapter(CityActivity.this, cityBeans, countryLvcountry, hotcityBeans);
                    countryLvcountry.setAdapter(cityAdapter);

                } else {
                    ToastUtil.showShortToast("暂无数据");
                }
            }

        });
    }


    @Override
    protected void setListener() {
        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                   // HttpDate(v.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });

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
}
