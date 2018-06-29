package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.fm.FmModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.widget.GifView;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * fm列表adapter
 */
public class FmAdapter extends CheckScrollAdapter<FmModel> {
    protected Context mContext;

    public FmAdapter(Context context) {
        super(context);
        mContext = context;
    }

    public void setData(List<FmModel> mItemList) {
        clear();
        isScroll = false;
        synchronized (mItemList) {
            for (FmModel item : mItemList) {
                add(item);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FmModel item = getItem(position);
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_fm);
        TextView id = viewHolder.getView(R.id.fm_id);
        TextView title = viewHolder.getView(R.id.fm_title);
        TextView play = viewHolder.getView(R.id.fm_play);
        TextView pinglun = viewHolder.getView(R.id.fm_pinglun);
        TextView time = viewHolder.getView(R.id.fm_time);
        GifView gif = viewHolder.getView(R.id.fm_gif);
        ImageView lock = viewHolder.getView(R.id.fm_lock);

        if (item.getGifSta() == 0) {
            gif.setVisibility(View.INVISIBLE);
            if (item.getStatus() == 0) {// id
                id.setVisibility(View.VISIBLE);
                lock.setVisibility(View.INVISIBLE);
                id.setText(String.valueOf(position + 1));
            } else if (item.getStatus() == 1) {// lock
                id.setVisibility(View.INVISIBLE);
                lock.setVisibility(View.VISIBLE);
            }
        } else {
            id.setVisibility(View.INVISIBLE);
            lock.setVisibility(View.INVISIBLE);
            gif.setVisibility(View.VISIBLE);
            if (item.getGifSta() == 1) {//loading
                gif.setMovieResource(R.raw.fm_loading);
            } else if (item.getGifSta() == 2) {
                gif.setMovieResource(R.raw.gif_red);
            }
        }

        title.setText(item.getTitle());
        play.setText(item.getPlay_num() + "");
        pinglun.setText("0");
        time.setText(CalendarTools.getTime(Long.valueOf(item.getAudioDuration())));
        return viewHolder.getConvertView();
    }

    public File inputstreamtofile(String fileName) {
        File file = new File(fileName);
        try {
            InputStream ins = mContext.getResources().openRawResource(R.raw.gif_blue);
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (IOException e) {

        }

        return file;
    }


    public boolean isScroll() {
        return isScroll;
    }

}
