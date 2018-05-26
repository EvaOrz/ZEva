package cn.com.zwwl.bayuwen.activity;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.InClassStatusAdapter;
import cn.com.zwwl.bayuwen.base.BasicActivity;
import cn.com.zwwl.bayuwen.widget.decoration.HSpacesItemDecoration;

/**
 * 上课情况照片
 * Create by zhumangmang at 2018/5/26 11:13
 */
public class InClassStatusActivity extends BasicActivity {
    @BindView(R.id.title)
    AppCompatTextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<String> imgUrls = new ArrayList<>();

    @Override
    protected int setContentView() {
        return R.layout.activity_in_class_status;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title.setText("上课情况照片");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HSpacesItemDecoration(10));
    }

    @Override
    protected void initData() {
        imgUrls.add("https://m.360buyimg.com/n0/jfs/t8830/106/1760940277/195595/5cf9412f/59bf2ef5N5ab7dc16.jpg!q70.jpg");
        imgUrls.add("https://m.360buyimg.com/n0/jfs/t5428/70/1520969931/274676/b644dd0d/591128e7Nd2f70da0.jpg!q70.jpg");
        imgUrls.add("https://m.360buyimg.com/n0/jfs/t5566/365/1519564203/36911/620c750c/591128eaN54ac3363.jpg!q70.jpg");
        InClassStatusAdapter adapter=new InClassStatusAdapter(imgUrls);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View view) {

    }
}
