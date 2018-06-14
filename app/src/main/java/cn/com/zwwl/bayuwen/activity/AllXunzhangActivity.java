package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.RadarAdapter;
import cn.com.zwwl.bayuwen.model.CommonModel;

/**
 * 全部勋章页面
 */
public class AllXunzhangActivity extends BaseActivity {


    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xunzhang);
        initView();
    }

    private void initView() {
        findViewById(R.id.xunzhang_back).setOnClickListener(this);

       RecyclerView radar = findViewById(R.id.radar);
       List<CommonModel> models = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            CommonModel model = new CommonModel();
            model.setContent("");
            models.add(model);
        }
        RadarAdapter radarAdapter = new RadarAdapter(models);
        radar.setAdapter(radarAdapter);
        radar.setLayoutManager(new GridLayoutManager(this, 10));
//        radar.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                int m = parent.getChildAdapterPosition(view) % 10;
//                if (m != 0)
//                    outRect.left = -DensityUtil.dip2px(res, R.dimen.dp_1) * m;
//                outRect.bottom = -DensityUtil.dip2px(res, R.dimen.dp_1);
//            }
//        });
        radar.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.xunzhang_back:
                finish();
                break;
        }
    }
}
