package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.CircleTransform;
import cn.com.zwwl.bayuwen.glide.GlideApp;
import cn.com.zwwl.bayuwen.model.PraiseModel;

/**
 * Created by lousx
 */
public class AcPraiseListAdapter extends BaseRecylcerViewAdapter<PraiseModel.StuAdvisorsEntity> {
    private Context mContext;

    public AcPraiseListAdapter(Context mContext, List<PraiseModel.StuAdvisorsEntity> list) {
        super(mContext, list);
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public AcPraiseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AcPraiseListAdapter.ViewHolder(inflater.inflate(R.layout.item_praise, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PraiseModel.StuAdvisorsEntity item = list.get(position);
        final AcPraiseListAdapter.ViewHolder viewHolder = (AcPraiseListAdapter.ViewHolder) holder;
        viewHolder.tcherNameTv.setText(item.getTo_name());
        viewHolder.tcherDesTv.setText(item.getT_desc());
        if (!TextUtils.isEmpty(list.get(position).getPic()))
            GlideApp.with(mContext)
                    .load(item.getPic())
                    .placeholder(R.drawable.avatar_placeholder)
                    .error(R.drawable.avatar_placeholder)
                    .transform(new CircleTransform(mContext))
                    .into(viewHolder.course_cover);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView course_cover;
        private TextView tcherNameTv;
        private TextView tcherDesTv;

        public ViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            course_cover = itemView.findViewById(R.id.course_cover);
            tcherNameTv = itemView.findViewById(R.id.tcherNameTv);
            tcherDesTv = itemView.findViewById(R.id.tcherDesTv);
        }
    }
}
