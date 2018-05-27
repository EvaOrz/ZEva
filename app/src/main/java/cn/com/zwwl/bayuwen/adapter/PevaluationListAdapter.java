package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.fm.PinglunModel;

/**
 * Created by lousx
 */
public class PevaluationListAdapter extends BaseRecylcerViewAdapter<PinglunModel>{
    public PevaluationListAdapter(Context mContext, List<PinglunModel> list) {
        super(mContext, list);
    }

    @NonNull
    @Override
    public PevaluationListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PevaluationListAdapter.ViewHolder(inflater.inflate(R.layout.item_pevaluation_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final PevaluationListAdapter.ViewHolder viewHolder = (PevaluationListAdapter.ViewHolder) holder;

        viewHolder.user_name.setText("张妈妈");
        viewHolder.time_tv.setText("2018-02-09 19:15");
        viewHolder.comment_tv.setText("生动有趣生动有趣生动有趣生动有趣生动有趣生动有趣生动有趣生动有趣");
        viewHolder.avatar.setImageResource(R.drawable.avatar_placeholder);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView comment_tv;
        private TextView time_tv;
        private TextView user_name;
        private ImageView avatar;

        public ViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            comment_tv =  itemView.findViewById(R.id.comment_tv);
            time_tv =  itemView.findViewById(R.id.time_tv);
            user_name =  itemView.findViewById(R.id.user_name);
            avatar =  itemView.findViewById(R.id.avatar);
        }
    }
}
