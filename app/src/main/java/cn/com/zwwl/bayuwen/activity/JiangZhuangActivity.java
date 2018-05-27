package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.zwwl.bayuwen.R;

/**
 * 奖状编辑页面
 */
public class JiangZhuangActivity extends BaseActivity {
    private RecyclerView jiangzhuangard;
    private JiangZhuangCardAdapter jiangZhuangCardAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiangzhuang);
        initView();
    }

    private void initView() {
        findViewById(R.id.jiangzhuang_back).setOnClickListener(this);
        jiangzhuangard = findViewById(R.id.jiangzhuang_card);
        jiangZhuangCardAdapter = new JiangZhuangCardAdapter();
        jiangzhuangard.setAdapter(jiangZhuangCardAdapter);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.jiangzhuang_back:
                finish();
                break;
        }
    }

    @Override
    protected void initData() {

    }

    /**
     * 奖状card adapter
     */
    public static class JiangZhuangCardAdapter extends RecyclerView.Adapter<JiangZhuangCardAdapter.ViewHolder> {


        static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView bookImage;
            TextView bookname;

            public ViewHolder(View view) {
                super(view);
//            bookImage = (ImageView) view.findViewById(R.id.book_iamge);
//            bookname = (TextView) view.findViewById(R.id.book_name);
            }
        }

        public JiangZhuangCardAdapter() {

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jiang_card, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.bookname.setText(book.getName());
//        holder.bookImage.setImageResource(book.getImageId());
        }

        @Override
        public int getItemCount() {
//        return datas.size();
            return 3;
        }


    }
}