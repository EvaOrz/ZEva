package cn.com.zwwl.bayuwen.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.BaseRecylcerViewAdapter;
import cn.com.zwwl.bayuwen.glide.GlideApp;
import cn.com.zwwl.bayuwen.model.fm.PinglunModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;

/**
 * 课程详情页 课程表
 */
public class KeDetailView3 extends LinearLayout {

    private Context context;
    private List<PinglunModel> data = new ArrayList<>() ,allData = new ArrayList<>();
    private RecyclerView recyclerView;
    private KeDetailPinglunAdapter adapter;

    public KeDetailView3(Context context) {
        super(context);
        this.context = context;

        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_kedetail_1, null, false);
        addView(view);
        recyclerView = view.findViewById(R.id.frag_kedetail1_list);
        adapter = new KeDetailPinglunAdapter(context,data);
        recyclerView.setAdapter(adapter);
        view.findViewById(R.id.frag_kedetail1_all).setOnClickListener(new View.OnClickListener() {
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

    public void setData(List<PinglunModel> dataList) {
        data.clear();
        allData.clear();
        allData.addAll(dataList);

        if (dataList.size() > 2) {
            data.addAll(dataList.subList(0, 2));
        } else data.addAll(dataList);
        adapter.notifyDataSetChanged();

    }

    /**
     * 评论adapter
     */
    public class KeDetailPinglunAdapter extends BaseRecylcerViewAdapter<PinglunModel> {

        public KeDetailPinglunAdapter(Context mContext, List<PinglunModel> list) {
            super(mContext, list);
            this.mContext = mContext;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int
                viewType) {
            return new ViewHolder(inflater.inflate(R.layout.item_pinglun, parent,
                    false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            PinglunModel pinglunModel = list.get(position);
            viewHolder.content.setText(pinglunModel.getContent());
            if (pinglunModel.getUserModel() != null){
                viewHolder.name.setText(pinglunModel.getUserModel().getName());
                GlideApp.with(mContext)
                        .load(pinglunModel.getUserModel().getPic())
                        .placeholder(R.drawable.avatar_placeholder)
                        .error(R.drawable.avatar_placeholder)
                        .into(viewHolder.image);
            }
            viewHolder.time.setText(CalendarTools.format(Long.valueOf(pinglunModel.getCtime()) * 1000, "yyyy-MM-dd HH:mm:ss"));
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView time;
            private TextView content;
            private TextView name;
            private ImageView image;

            public ViewHolder(View itemView) {
                super(itemView);
                initView();
            }

            private void initView() {
                image = itemView.findViewById(R.id.ping_avatar);
                time = itemView.findViewById(R.id.ping_time);
                name = itemView.findViewById(R.id.ping_name);
                content = itemView.findViewById(R.id.ping_content);
            }
        }
    }



}
