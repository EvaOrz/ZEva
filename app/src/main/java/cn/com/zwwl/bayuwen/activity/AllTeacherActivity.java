package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CheckScrollAdapter;
import cn.com.zwwl.bayuwen.api.KeSelectTypeApi;
import cn.com.zwwl.bayuwen.api.KeTagListApi;
import cn.com.zwwl.bayuwen.api.TeacherApi;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeTypeModel;
import cn.com.zwwl.bayuwen.model.TeacherModel;
import cn.com.zwwl.bayuwen.model.TeacherTypeModel;
import cn.com.zwwl.bayuwen.model.fm.FmModel;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.view.selectmenu.SelectMenuView;
import cn.com.zwwl.bayuwen.view.selectmenu.SelectTempModel;
import cn.com.zwwl.bayuwen.view.selectmenu.TeacherMenuView;
import cn.com.zwwl.bayuwen.widget.CircleImageView;
import cn.com.zwwl.bayuwen.widget.GifView;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 全部教师页面
 */
public class AllTeacherActivity extends BaseActivity {
    private TeacherMenuView menuView;
    private EditText searchEv;
    private GridView gridView;
    private TeacherAdapter teacherAdapter;
    private List<TeacherModel> teacherModels = new ArrayList<>();
    private TeacherTypeModel teacherTypeModel;

    private int type = 1;// 1:老师 2:班主任

    @Override
    protected void initData() {
        // 获取分类列表
        new KeSelectTypeApi(mContext, 2, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                if (entry != null) {
                    teacherTypeModel = (TeacherTypeModel) entry;
                    handler.sendEmptyMessage(1);
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });

        // 获取全部教师
        new TeacherApi(mContext, type, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                if (Tools.listNotNull(list)) {
                    teacherModels.addAll(list);
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    teacherAdapter.setData(teacherModels);
                    break;
                case 1:
                    menuView.setData(teacherTypeModel);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_teacher);
        type = getIntent().getIntExtra("AllTeacherActivity_data", 1);
        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.all_t_back).setOnClickListener(this);
        searchEv = findViewById(R.id.search_view);

        menuView = findViewById(R.id.all_t_menu);
        menuView.setOnSureClickListener(new TeacherMenuView.OnSureClickListener() {
            @Override
            public void onClick(List<SelectTempModel> checkedList, int type) {
                String stxt = searchEv.getText().toString();
                if (type == 1) {
                    String gtxt = "";
                    for (SelectTempModel selectTempModel : checkedList) {
                        if (selectTempModel.isCheck())
                            gtxt += selectTempModel.getId() + ",";
                    }
                    doSearch(null, gtxt, stxt);
                } else if (type == 2) {
                    String xtxt = "";
                    for (SelectTempModel selectTempModel : checkedList) {
                        if (selectTempModel.isCheck())
                            xtxt += selectTempModel.getText() + ",";
                    }
                    doSearch(xtxt, null, stxt);
                }

            }
        });

        int height = getViewHeight(menuView);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout
                .LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, height, 0, 0);
        layoutParams.addRule(RelativeLayout.BELOW, R.id.search_head_layout);
        gridView = findViewById(R.id.all_t_grid);
        gridView.setLayoutParams(layoutParams);
        teacherAdapter = new TeacherAdapter(mContext);
        gridView.setAdapter(teacherAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(mContext, TeacherDetailActivity.class);
                i.putExtra("tid", teacherModels.get(position).getTid());
                startActivity(i);
            }
        });
    }

    /**
     * 搜索
     *
     * @param xiangmu
     * @param grade
     * @param keyword
     */
    private void doSearch(String xiangmu, String grade, String keyword) {
        showLoadingDialog(true);
        new TeacherApi(mContext, xiangmu, grade, keyword, type, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                showLoadingDialog(false);
                teacherModels.clear();
                if (Tools.listNotNull(list)) {
                    teacherModels.addAll(list);
                }
                handler.sendEmptyMessage(0);
            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error != null)
                    showToast(error.getDesc());
            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.all_t_back:
                finish();
                break;
        }
    }

    public class TeacherAdapter extends CheckScrollAdapter<TeacherModel> {
        protected Context mContext;


        public TeacherAdapter(Context mContext) {
            super(mContext);
            this.mContext = mContext;
        }

        public void setData(List<TeacherModel> mItemList) {
            clear();
            isScroll = false;
            synchronized (mItemList) {
                for (TeacherModel item : mItemList) {
                    add(item);
                }
                notifyDataSetChanged();
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final TeacherModel item = getItem(position);
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout
                    .item_cdetail_teacher);

            TextView title = viewHolder.getView(R.id.cdetail_t_name);
            CircleImageView img = viewHolder.getView(R.id.cdetail_t_avatar);

            title.setText(item.getName());
            if (!TextUtils.isEmpty(item.getPic()))
                ImageLoader.display(mContext, img, item.getPic(), R
                        .drawable.avatar_placeholder, R.drawable.avatar_placeholder);
            return viewHolder.getConvertView();
        }

    }
}
