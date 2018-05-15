package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.FmModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.GifView;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * fm列表adapter
 */
public class FmAdapter extends CheckScrollAdapter<FmModel> {
    protected Context mContext;
    private int cuPosition = -1;

    public FmAdapter(Context context) {
        super(context);
        mContext = context;
    }

    public void setData(List<FmModel> mItemList, int cuPosition) {
        clear();
        isScroll = false;
        this.cuPosition = cuPosition;
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
        if (position == cuPosition) {
            id.setVisibility(View.INVISIBLE);
            gif.setVisibility(View.VISIBLE);
            gif.setMovieResource(R.raw.gif_red);
        } else {
            id.setVisibility(View.VISIBLE);
            gif.setVisibility(View.INVISIBLE);
        }


        id.setText(item.getId());
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
