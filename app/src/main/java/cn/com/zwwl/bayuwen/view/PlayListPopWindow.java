package cn.com.zwwl.bayuwen.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CheckScrollAdapter;
import cn.com.zwwl.bayuwen.model.FmModel;


/**
 * 播放列表
 */
public class PlayListPopWindow {

    private Context mContext;
    private PopupWindow window;
    private OnItemClickListener listener;
    private ListView listView;
    private PlayerListAdapter adapter;
    private List<FmModel> fmModelList;
    private View view;

    public PlayListPopWindow(Context context, List<FmModel> fmModelList, OnItemClickListener listener) {
        mContext = context;
        this.listener = listener;
        this.fmModelList = fmModelList;
        init();
    }

    public interface OnItemClickListener {
        public void choose(int position);
    }

    public void init() {
        view = LayoutInflater.from(mContext).inflate(
                R.layout.pop_playlist, null);

        view.findViewById(R.id.play_cancle).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        listView = view.findViewById(R.id.play_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.choose(position);
            }
        });
        adapter = new PlayerListAdapter(mContext);
        listView.setAdapter(adapter);
        adapter.setData(fmModelList, 0);

    }

    public void setCurrentPos(int pos) {
        adapter.setData(fmModelList, pos);
    }

    public void show() {
        window = new PopupWindow(view, LayoutParams.FILL_PARENT,
                MyApplication.height / 2);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setAnimationStyle(R.style.fetch_image_popup_anim);
        window.update();
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    public class PlayerListAdapter extends CheckScrollAdapter<FmModel> {
        protected Context mContext;
        private int cuPosition = -1;

        public PlayerListAdapter(Context context) {
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
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_play_pop);
            TextView title = viewHolder.getView(R.id.pop_title);
            TextView name = viewHolder.getView(R.id.pop_name);
            GifView gif = viewHolder.getView(R.id.pop_gif);
            gif.setMovieResource(R.raw.gif_red);

            if (position == cuPosition) {
                gif.setVisibility(View.VISIBLE);

            } else {
                gif.setVisibility(View.INVISIBLE);
            }
            title.setText(item.getTitle());
            name.setText("  -  " + "C君");
            return viewHolder.getConvertView();
        }

        public boolean isScroll() {
            return isScroll;
        }

    }


}
