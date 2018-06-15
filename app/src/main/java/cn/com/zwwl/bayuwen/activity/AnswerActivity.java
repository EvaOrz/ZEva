package cn.com.zwwl.bayuwen.activity;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SeekBar;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.OptionsAdapter;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;

/**
 * 闯关答题
 * Created by zhumangmang at 2018/6/14 18:34
 */
public class AnswerActivity extends BasicActivityWithTitle {
    @BindView(R.id.progress)
    SeekBar progress;
    @BindView(R.id.current)
    AppCompatTextView current;
    @BindView(R.id.total)
    AppCompatTextView total;
    @BindView(R.id.question)
    AppCompatTextView question;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    OptionsAdapter adapter;
    List<String> options;

    @Override
    protected int setContentView() {
        return R.layout.activity_answer;
    }

    @Override
    protected void initView() {
        current.setText("2");
        total.setText("6");
        question.setText("1. This is just a test!!!!!!!!!");
        adapter = new OptionsAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData() {
        options = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            options.add("I am right");
        }
        adapter.setOptions(options, 2);
    }

    @Override
    protected void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter quickAdapter, View view, int position) {
                adapter.setChoose(position);
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
