package cn.com.zwwl.bayuwen.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.AlbumModel;

/**
 * 日历页面课程卡片
 */
public class CalendarKeAdapter extends RecyclerView.Adapter<CalendarKeAdapter.ViewHolder> {

    private List<AlbumModel> datas = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImage;
        TextView bookname;

        public ViewHolder(View view) {
            super(view);
//            bookImage = (ImageView) view.findViewById(R.id.book_iamge);
//            bookname = (TextView) view.findViewById(R.id.book_name);
        }
    }

    public CalendarKeAdapter(List<AlbumModel> mBookList) {
        this.datas = mBookList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calender_ke, parent, false);
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
        return 12;
    }

}
