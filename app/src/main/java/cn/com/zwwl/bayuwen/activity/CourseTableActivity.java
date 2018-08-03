package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CourseTableAdapter;
import cn.com.zwwl.bayuwen.api.CourseListApi;
import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.util.Tools;

/**
 * 可操作课程列表
 * Create by zhumangmang at 2018/5/28 10:03
 */
public class CourseTableActivity extends BasicActivityWithTitle {
    @BindView(R.id.type)
    AppCompatTextView type;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    CourseTableAdapter adapter;
    private List<KeModel> keModels;

    @Override
    protected int setContentView() {
        return R.layout.activity_course_table;
    }

    @Override
    protected void initView() {
        setCustomTitle(res.getString(R.string.covert_class_course));
        type.setText(res.getString(R.string.chose_covert_class_course_by_need));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CourseTableAdapter(null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        map.put("users",mApplication.oldKe.getUsers());
        map.put("online",mApplication.oldKe.getOnline());
        map.put("type",mApplication.oldKe.getType());
        map.put("school",mApplication.oldKe.getSchool());
        getCourseData();
    }

    private void getCourseData() {
        map.put("online", "0");
        StringBuilder temp = new StringBuilder(UrlUtil.getCDetailUrl(null) + "/search");
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
    protected void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mActivity, ConvertClassActivity.class));
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void close() {
        finish();
    }
}
