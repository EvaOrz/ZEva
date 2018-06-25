package cn.com.zwwl.bayuwen.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CheckScrollAdapter;
import cn.com.zwwl.bayuwen.api.fm.PinglunApi;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.EvalListModel;
import cn.com.zwwl.bayuwen.model.fm.PinglunModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.NoScrollListView;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 课程详情页 课程表
 */
public class KeDetailView3 extends LinearLayout {

    private Activity context;
    private List<PinglunModel> data = new ArrayList<>(), allData = new ArrayList<>();
    private NoScrollListView listView;
    private KePinglunsAdapter adapter;
    private TextView lookAll;
    private String kid;
    private int page = 1, totalPage;

    public KeDetailView3(Activity context, String kid) {
        super(context);
        this.context = context;
        initView();
        this.kid = kid;
        getEvalList();
    }

    private void getEvalList() {
        new PinglunApi(context, kid, String.valueOf(page), new ResponseCallBack<EvalListModel>() {
            @Override
            public void result(final EvalListModel evalListModel, ErrorMsg errorMsg) {
                KeDetailView3.this.post(new Runnable() {
                    @Override
                    public void run() {
                        if (evalListModel != null) {
                            totalPage = evalListModel.getPageCount();
                            if (page==totalPage)lookAll.setVisibility(GONE);
                            if (evalListModel.getData() != null && evalListModel.getData().size() > 0)
                                allData.addAll(evalListModel.getData());
                            if (page == 1) {
                                if (allData.size() > 3) {
                                    data.addAll(allData.subList(0, 3));
                                    lookAll.setVisibility(VISIBLE);
                                } else {
                                    lookAll.setVisibility(GONE);
                                    data.addAll(allData);
                                }
                            } else {
                                data.clear();
                                data.addAll(allData);
                            }
                            adapter.setData(data);
                        }
                    }
                });
            }
        });
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_kedetail_1, null, false);
        addView(view);
        listView = view.findViewById(R.id.frag_kedetail1_list);
        adapter = new KePinglunsAdapter(context);
        listView.setAdapter(adapter);

        lookAll = view.findViewById(R.id.frag_kedetail1_all);
        lookAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.size() == 3 && allData.size() > data.size()) {
                    data.clear();
                    data.addAll(allData);
                    if (page==totalPage)lookAll.setVisibility(GONE);
                } else if (totalPage != 0 && page != totalPage) {
                    ++page;
                    getEvalList();
                }
            }
        });

    }

    public void setData(List<PinglunModel> dataList) {
        data.clear();
        allData.clear();
        allData.addAll(dataList);

        if (!Tools.listNotNull(dataList)) {
            lookAll.setVisibility(View.GONE);
        } else if (dataList.size() > 2) {
            data.addAll(dataList.subList(0, 2));
        } else data.addAll(dataList);
        adapter.setData(data);
    }

    /**
     * 评论adapter
     */
    public class KePinglunsAdapter extends CheckScrollAdapter<PinglunModel> {
        protected Context mContext;

        public KePinglunsAdapter(Context context) {
            super(context);
            mContext = context;
        }

        public void setData(List<PinglunModel> mItemList) {
            clear();
            isScroll = false;
            synchronized (mItemList) {
                for (PinglunModel item : mItemList) {
                    add(item);
                }
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final PinglunModel pinglunModel = getItem(position);
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_pinglun);

            ImageView image = viewHolder.getView(R.id.ping_avatar);
            TextView time = viewHolder.getView(R.id.ping_time);
            TextView name = viewHolder.getView(R.id.ping_name);
            TextView content = viewHolder.getView(R.id.ping_content);

            content.setText(pinglunModel.getContent());
            if (pinglunModel.getUserModel() != null) {
                name.setText(pinglunModel.getUserModel().getName());
                ImageLoader.display(mContext, image, pinglunModel.getUserModel()
                        .getPic(), R
                        .drawable.avatar_placeholder, R.drawable.avatar_placeholder);
            }
            time.setText(CalendarTools.format(Long.valueOf(pinglunModel.getCtime()) *
                    1000, "yyyy-MM-dd HH:mm:ss"));
            return viewHolder.getConvertView();
        }

        public boolean isScroll() {
            return isScroll;
        }

    }


}
