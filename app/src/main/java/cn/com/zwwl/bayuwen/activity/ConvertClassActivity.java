package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CourseTableAdapter;
import cn.com.zwwl.bayuwen.api.CourseListApi;
import cn.com.zwwl.bayuwen.api.KeSelectTypeApi;
import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.base.MenuCode;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.KeTypeModel;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.view.selectmenu.SelectMenuView;
import cn.com.zwwl.bayuwen.view.selectmenu.SelectTempModel;

/**
 * 选择可调课（可转）班级
 * Created by zhumangmang at 2018/5/29 13:57
 */
public class ConvertClassActivity extends BasicActivityWithTitle {
    @BindView(R.id.search)
    AppCompatEditText search;
    @BindView(R.id.operate)
    SelectMenuView operate;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    CourseTableAdapter adapter;
    KeTypeModel typeModel;
    private String url = UrlUtil.getCDetailUrl(null) + "/search";
    private List<KeModel> keModels;
    private List<KeModel> stockClass;

    @Override
    protected int setContentView() {
        return R.layout.activity_convert_class;
    }

    @Override
    protected void initView() {
        showMenu(MenuCode.HIDE_CLASS);
        keModels = new ArrayList<>();
        stockClass = new ArrayList<>();
        if (mApplication.operate_type == 0) {
            setCustomTitle(res.getString(R.string.chose_chanable_class));
        } else {
            setCustomTitle(res.getString(R.string.chose_convertable_class));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CourseTableAdapter(null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        getChoseType();
        getCourseData();
    }

    private void getChoseType() {
        new KeSelectTypeApi(mContext, 1, new FetchEntryListener() {
            @Override
            public void setData(final Entry entry) {
                if (entry != null) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            typeModel = (KeTypeModel) entry;
                            operate.setOfflineData(typeModel);
                        }
                    });

                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }

    @Override
    protected void setListener() {
        operate.setOnMenuSelectDataChangedListener(new SelectMenuView.OnMenuSelectDataChangedListener() {
            @Override
            public void onSortChanged(SelectTempModel sortType, int type) {
                switch (type) {
                    case 1:
                        map.put("users", sortType.getId());
                        break;
                    case 2:
                        map.put("type", sortType.getId());
                        break;
                    case 4:
                        map.put("school", sortType.getId());
                        break;
                    default:
                        map.put("time", sortType.getText());
                        break;
                }
                getCourseData();
            }

            @Override
            public void onViewClicked(View view) {

            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mApplication.newKe = keModels.get(position);
                if (mApplication.operate_type == 0) {
                    Intent intent = new Intent(mActivity, UnitTableActivity.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("kid", keModels.get(position).getKid());
                    intent.putExtra("course_type",1);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mActivity, ClassDetailActivity.class);
                    intent.putExtra("kid", keModels.get(position).getKid());
                    startActivity(intent);
                }
            }
        });
    }

    private void getCourseData() {
        map.put("keyword", Tools.getText(search));
        map.put("online", "0");
        StringBuilder temp = new StringBuilder(url);
        if (map.size() > 0) {
            temp.append("?");
            for (String key : map.keySet()) {
                if (!TextUtils.isEmpty(map.get(key))) {
                    String and = temp.toString().endsWith("?") ? "" : "&";
                    temp.append(and).append(key).append("=").append(map.get(key));
                }
            }
            new CourseListApi(mContext, temp.toString(), new FetchEntryListListener() {
                @Override
                public void setData(final List list) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            keModels.clear();
                            if (Tools.listNotNull(list))
                                keModels.addAll(list);
                            adapter.setNewData(keModels);
                            stockClass.clear();
                            for (KeModel model : keModels) {
                                if (!"0".equals(model.getStock())) stockClass.add(model);
                            }

                        }
                    });


                }

                @Override
                public void setError(ErrorMsg error) {
                }
            });
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void onMenuClick(int menuCode) {
        adapter.setNewData(stockClass);
    }
}
