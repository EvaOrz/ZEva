package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.CommonModel;

/**
 *
 */
public class RadarAdapter extends BaseQuickAdapter<CommonModel, BaseViewHolder> {
    public RadarAdapter(@Nullable List<CommonModel> data) {
        super(R.layout.item_radar, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonModel item) {
        AppCompatImageView imageView = helper.getView(R.id.pic);
        LinearLayout layout = helper.getView(R.id.layout);
        int wid = MyApplication.width / 10 + MyApplication.width * 34 / 10 / 72 - 10;
        imageView.setLayoutParams(new LinearLayout.LayoutParams(wid, wid));
        layout.setLayoutParams(new LinearLayout.LayoutParams(MyApplication.width / 10,
                MyApplication.width / 10));

        if (helper.getLayoutPosition() == 0) {
            imageView.setImageResource(R.mipmap.pt_01);
        } else if (helper.getLayoutPosition() > 0 && helper.getLayoutPosition() < 9) {
            imageView.setImageResource(R.mipmap.pt_02);
        } else if (helper.getLayoutPosition() == 9) {
            imageView.setImageResource(R.mipmap.pt_03);
        } else if (helper.getLayoutPosition() == 10 || helper.getLayoutPosition() == 20 || helper
                .getLayoutPosition() == 30) {
            imageView.setImageResource(R.mipmap.pt_04);
        } else if (helper.getLayoutPosition() == 19 || helper.getLayoutPosition() == 29 || helper
                .getLayoutPosition() == 39) {
            imageView.setImageResource(R.mipmap.pt_06);
        } else if (helper.getLayoutPosition() == 40) {
            imageView.setImageResource(R.mipmap.pt_07);
        } else if (helper.getLayoutPosition() > 40 && helper.getLayoutPosition() < 49) {
            imageView.setImageResource(R.mipmap.pt_08);
        } else if (helper.getLayoutPosition() == 49) {
            imageView.setImageResource(R.mipmap.pt_09);
        } else {
            imageView.setImageResource(R.mipmap.pt_05);
        }
    }
}
