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
    private int colunms = 9;// 每行的拼图个数
    private int totalWidth = MyApplication.width;// 拼图控件的总宽度

    private static int tuSideWidth = 34;// 每个拼图的边框宽度 *2
    private static int tuMidWidth = 72;// 每个拼图的中间部分宽度

    private static int tuPadding = 10;// 拼图的缝隙宽度


    public RadarAdapter(@Nullable List<CommonModel> data) {
        super(R.layout.item_radar, data);
    }

    public RadarAdapter(@Nullable List<CommonModel> data, int totalWidth) {
        super(R.layout.item_radar, data);
        this.totalWidth = totalWidth;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonModel item) {
        AppCompatImageView imageView = helper.getView(R.id.pic);
        LinearLayout layout = helper.getView(R.id.layout);

        int layoutWid = totalWidth / colunms;
        layout.setLayoutParams(new LinearLayout.LayoutParams(layoutWid, layoutWid));
        int wid = layoutWid + layoutWid * tuSideWidth / tuMidWidth - tuPadding;
        imageView.setLayoutParams(new LinearLayout.LayoutParams(wid, wid));


        if (helper.getLayoutPosition() == 0) {
            imageView.setImageResource(R.mipmap.pt_01);
        } else if (helper.getLayoutPosition() > 0 && helper.getLayoutPosition() < 8) {
            imageView.setImageResource(R.mipmap.pt_02);
        } else if (helper.getLayoutPosition() == 8) {
            imageView.setImageResource(R.mipmap.pt_03);
        } else if (helper.getLayoutPosition() == 9 || helper.getLayoutPosition() == 18 || helper
                .getLayoutPosition() == 27 || helper.getLayoutPosition() == 36) {
            imageView.setImageResource(R.mipmap.pt_04);
        } else if (helper.getLayoutPosition() == 17 || helper.getLayoutPosition() == 26 || helper
                .getLayoutPosition() == 35 || helper.getLayoutPosition() == 44) {
            imageView.setImageResource(R.mipmap.pt_06);
        } else if (helper.getLayoutPosition() == 45) {
            imageView.setImageResource(R.mipmap.pt_07);
        } else if (helper.getLayoutPosition() > 45 && helper.getLayoutPosition() < 53) {
            imageView.setImageResource(R.mipmap.pt_08);
        } else if (helper.getLayoutPosition() == 53) {
            imageView.setImageResource(R.mipmap.pt_09);
        } else {
            imageView.setImageResource(R.mipmap.pt_05);
        }
    }
}
