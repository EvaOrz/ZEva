package cn.com.zwwl.bayuwen.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.KeModel;


/**
 * Created by lousx
 */
public class CompleteCourseAdapter extends BaseQuickAdapter<KeModel,BaseViewHolder> {

    public CompleteCourseAdapter(List<KeModel> list) {
        super(R.layout.item_course_complete, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, KeModel item) {
        if (item!=null) {
            helper.setText(R.id.course_name, item.getName());
            helper.setText(R.id.course_chapter,item.getDesc());
            ImageLoader.display(mContext, (ImageView) helper.getView(R.id.course_cover), item.getImg());
        }
    }

}
