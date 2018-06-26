package cn.com.zwwl.bayuwen.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CheckScrollAdapter;
import cn.com.zwwl.bayuwen.api.LessonListApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.BaseResponse;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.LessonModel;
import cn.com.zwwl.bayuwen.widget.NoScrollListView;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 课程详情页 课程表
 */
public class KeDetailView1 extends LinearLayout {

    private Activity context;
    private NoScrollListView listView;
    private TextView lookAll;
    private KeLectruesAdapter adapter;
    private List<LessonModel> data = new ArrayList<>();
    private List<LessonModel> allData = new ArrayList<>();
    private int page = 1, totalPage;
    private String cid;

    public KeDetailView1(Activity context, String cid) {
        super(context);
        this.context = context;
        initView();
        this.cid = cid;
        getLessonList();
    }

    private void getLessonList() {
        new LessonListApi(context, cid, page, new ResponseCallBack<BaseResponse>() {
            @Override
            public void result(final BaseResponse baseResponse, ErrorMsg errorMsg) {
                KeDetailView1.this.post(new Runnable() {
                    @Override
                    public void run() {
                        if (baseResponse != null) {
                             totalPage = baseResponse.getTotal() / baseResponse.getPagesize();
                            if (baseResponse.getTotal() % baseResponse.getPagesize() > 0)
                                totalPage = totalPage + 1;
                            if (page == totalPage) lookAll.setVisibility(GONE);
                            if (baseResponse.getLectures() != null && baseResponse.getLectures()
                                    .size() > 0)
                                allData.addAll(baseResponse.getLectures());
                            if (page == 1) {
                                if (allData.size() > 3) {
                                    lookAll.setVisibility(VISIBLE);
                                    data.addAll(allData.subList(0, 3));
                                } else {
                                    lookAll.setVisibility(GONE);
                                    data.addAll(allData);
                                }
                            } else {
                                data.clear();
                                data.addAll(allData);
                            }
                        }
                        adapter.setData(data);
                    }
                });
            }
        });
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_kedetail_1, null, false);
        addView(view);
        listView = view.findViewById(R.id.frag_kedetail1_list);

        lookAll = view.findViewById(R.id.frag_kedetail1_all);
        lookAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.size() == 3 && allData.size() > data.size()) {
                    data.clear();
                    data.addAll(allData);
                    if (page==totalPage)lookAll.setVisibility(GONE);
                    adapter.setData(data);
                } else if (totalPage != 0 && page != totalPage) {
                    ++page;
                    getLessonList();
                }
            }
        });
        adapter = new KeLectruesAdapter(context);
        listView.setAdapter(adapter);
    }

    public class KeLectruesAdapter extends CheckScrollAdapter<LessonModel> {
        protected Context mContext;

        public KeLectruesAdapter(Context context) {
            super(context);
            mContext = context;
        }

        public void setData(List<LessonModel> mItemList) {
            clear();
            isScroll = false;
            synchronized (mItemList) {
                for (LessonModel item : mItemList) {
                    add(item);
                }
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final LessonModel item = getItem(position);
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_c_list);

            TextView video_id = viewHolder.getView(R.id.course_d_id);
            TextView video_title = viewHolder.getView(R.id.course_d_title);
            TextView video_time = viewHolder.getView(R.id.course_d_time);
            video_id.setText(String.valueOf(position+1));
            video_title.setText(item.getTitle());
            video_time.setText(item.getStart_at()
                    + "  " + item.getClass_start_at()
                    + "-" + item.getClass_end_at()
                    + "(" + item.getHours() + "课时)");
            return viewHolder.getConvertView();
        }

        public boolean isScroll() {
            return isScroll;
        }

    }
}
