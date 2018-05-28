package cn.com.zwwl.bayuwen.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.BaseRecylcerViewAdapter;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.LessonModel;

/**
 * 课程表
 */
public class CDetailTabFrag1 extends Fragment {

    private Activity mActivity;
    private RecyclerView recyclerView;
    private TextView lookAll;
    private KeDetailListAdapter adapter;
    private List<LessonModel> data = new ArrayList<>();
    private List<LessonModel> allData = new ArrayList<>();

    public static CDetailTabFrag1 newInstance() {
        CDetailTabFrag1 fg = new CDetailTabFrag1();
        Bundle bundle = new Bundle();
        fg.setArguments(bundle);
        return fg;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kedetail_1, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.frag_kedetail1_list);
        adapter = new KeDetailListAdapter(mActivity, data);
        recyclerView.setAdapter(adapter);

        lookAll = view.findViewById(R.id.frag_kedetail1_all);
        lookAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allData.size() > data.size()) {
                    data.clear();
                    data.addAll(allData);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void setData(List<LessonModel> dataList) {
        data.clear();
        allData.clear();
        allData.addAll(dataList);

        if (dataList.size() > 2) {
            data.addAll(dataList.subList(0, 2));
        } else data.addAll(dataList);
        adapter.notifyDataSetChanged();

    }

    /**
     * 课程详情页课表item
     */
    public class KeDetailListAdapter extends BaseRecylcerViewAdapter<LessonModel> {

        public KeDetailListAdapter(Context mContext, List<LessonModel> list) {
            super(mContext, list);
            this.mContext = mContext;
        }


        @NonNull
        @Override
        public KeDetailListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int
                viewType) {
            return new KeDetailListAdapter.ViewHolder(inflater.inflate(R.layout.item_c_list, parent,
                    false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            final KeDetailListAdapter.ViewHolder viewHolder = (KeDetailListAdapter.ViewHolder)
                    holder;
            viewHolder.video_id.setText(position + 1 + "");
            viewHolder.video_title.setText(list.get(position).getTitle());
            viewHolder.video_time.setText(list.get(position).getStart_at()
                    + "  " + list.get(position).getClass_start_at()
                    + "-" + list.get(position).getClass_end_at()
                    + "(" + list.get(position).getHours() + "小时)");
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView video_title;
            private TextView video_time;
            private TextView video_id;

            public ViewHolder(View itemView) {
                super(itemView);
                initView();
            }

            private void initView() {
                video_title = itemView.findViewById(R.id.course_d_title);
                video_time = itemView.findViewById(R.id.course_d_time);
                video_id = itemView.findViewById(R.id.course_d_id);
            }
        }
    }


}
