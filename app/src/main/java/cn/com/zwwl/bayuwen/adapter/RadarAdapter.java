package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.PintuModel;

/**
 * 拼图色块适配器
 */
public class RadarAdapter extends BaseQuickAdapter<PintuModel.LectureinfoBean.SectionListBean,
        BaseViewHolder> {
    private int colunms = 9;// 每行的拼图个数
    private int totalWidth = MyApplication.width;// 拼图控件的总宽度

    private static int tuSideWidth = 34 - 6;// 每个拼图的边框宽度 *2
    private static int tuMidWidth = 72;// 每个拼图的中间部分宽度

//    private static int tuPadding = 10;// 拼图的缝隙宽度

    private int[] suoImgsVip = new int[]{R.drawable.tu1, R.drawable.tu2, R.drawable.tu3, R.drawable
            .tu4, R.drawable.tu5, R.drawable.tu6, R.drawable.tu7, R.drawable.tu8, R.drawable.tu9};
    private int[] tongImgsVip = new int[]{R.drawable.tu28, R.drawable.tu29, R.drawable.tu30, R
            .drawable
            .tu31, R.drawable.tu32, R.drawable.tu33, R.drawable.tu34, R.drawable.tu35, R.drawable
            .tu36};
    private int[] yinImgsVip = new int[]{R.drawable.tu19, R.drawable.tu20, R.drawable.tu21, R
            .drawable.tu22, R.drawable.tu23, R.drawable.tu24, R.drawable.tu25, R.drawable.tu26, R
            .drawable
            .tu27};
    private int[] jinImgsVip = new int[]{R.drawable.tu10, R.drawable.tu11, R.drawable.tu12, R
            .drawable
            .tu13, R.drawable.tu14, R.drawable.tu15, R.drawable.tu16, R.drawable.tu17, R.drawable
            .tu18};


    public RadarAdapter(@Nullable List<PintuModel.LectureinfoBean.SectionListBean> data) {
        super(R.layout.item_radar, data);
    }

    public RadarAdapter(@Nullable List<PintuModel.LectureinfoBean.SectionListBean> data, int
            totalWidth) {
        super(R.layout.item_radar, data);
        this.totalWidth = totalWidth;
//        tuPadding = 10 * totalWidth / MyApplication.width;
    }

    @Override
    protected void convert(BaseViewHolder helper, PintuModel.LectureinfoBean.SectionListBean item) {

        AppCompatImageView imageView = helper.getView(R.id.pic);
        LinearLayout layout = helper.getView(R.id.layout);
        int layoutWid = totalWidth / colunms;
        layout.setLayoutParams(new LinearLayout.LayoutParams(layoutWid, layoutWid));
        int wid = layoutWid + layoutWid * tuSideWidth / tuMidWidth;
        imageView.setLayoutParams(new LinearLayout.LayoutParams(wid, wid));

        int status = checkLevel(item);
        if (status == 0) {
            if (helper.getLayoutPosition() == 0) {
                imageView.setImageResource(suoImgsVip[0]);
            } else if (helper.getLayoutPosition() > 0 && helper.getLayoutPosition() < 8) {
                imageView.setImageResource(suoImgsVip[1]);
            } else if (helper.getLayoutPosition() == 8) {
                imageView.setImageResource(suoImgsVip[2]);
            } else if (helper.getLayoutPosition() == 9 || helper.getLayoutPosition() == 18 || helper
                    .getLayoutPosition() == 27 || helper.getLayoutPosition() == 36) {
                imageView.setImageResource(suoImgsVip[3]);
            } else if (helper.getLayoutPosition() == 17 || helper.getLayoutPosition() == 26 ||
                    helper
                            .getLayoutPosition() == 35 || helper.getLayoutPosition() == 44) {
                imageView.setImageResource(suoImgsVip[5]);
            } else if (helper.getLayoutPosition() == 45) {
                imageView.setImageResource(suoImgsVip[6]);
            } else if (helper.getLayoutPosition() > 45 && helper.getLayoutPosition() < 53) {
                imageView.setImageResource(suoImgsVip[7]);
            } else if (helper.getLayoutPosition() == 53) {
                imageView.setImageResource(suoImgsVip[8]);
            } else {
                imageView.setImageResource(suoImgsVip[4]);
            }
        } else if (status == 1) {
            if (helper.getLayoutPosition() == 0) {
                imageView.setImageResource(tongImgsVip[0]);
            } else if (helper.getLayoutPosition() > 0 && helper.getLayoutPosition() < 8) {
                imageView.setImageResource(tongImgsVip[1]);
            } else if (helper.getLayoutPosition() == 8) {
                imageView.setImageResource(tongImgsVip[2]);
            } else if (helper.getLayoutPosition() == 9 || helper.getLayoutPosition() == 18 || helper
                    .getLayoutPosition() == 27 || helper.getLayoutPosition() == 36) {
                imageView.setImageResource(tongImgsVip[3]);
            } else if (helper.getLayoutPosition() == 17 || helper.getLayoutPosition() == 26 ||
                    helper
                            .getLayoutPosition() == 35 || helper.getLayoutPosition() == 44) {
                imageView.setImageResource(tongImgsVip[5]);
            } else if (helper.getLayoutPosition() == 45) {
                imageView.setImageResource(tongImgsVip[6]);
            } else if (helper.getLayoutPosition() > 45 && helper.getLayoutPosition() < 53) {
                imageView.setImageResource(tongImgsVip[7]);
            } else if (helper.getLayoutPosition() == 53) {
                imageView.setImageResource(tongImgsVip[8]);
            } else {
                imageView.setImageResource(tongImgsVip[4]);
            }
        } else if (status == 2) {
            if (helper.getLayoutPosition() == 0) {
                imageView.setImageResource(yinImgsVip[0]);
            } else if (helper.getLayoutPosition() > 0 && helper.getLayoutPosition() < 8) {
                imageView.setImageResource(yinImgsVip[1]);
            } else if (helper.getLayoutPosition() == 8) {
                imageView.setImageResource(yinImgsVip[2]);
            } else if (helper.getLayoutPosition() == 9 || helper.getLayoutPosition() == 18 || helper
                    .getLayoutPosition() == 27 || helper.getLayoutPosition() == 36) {
                imageView.setImageResource(yinImgsVip[3]);
            } else if (helper.getLayoutPosition() == 17 || helper.getLayoutPosition() == 26 ||
                    helper
                            .getLayoutPosition() == 35 || helper.getLayoutPosition() == 44) {
                imageView.setImageResource(yinImgsVip[5]);
            } else if (helper.getLayoutPosition() == 45) {
                imageView.setImageResource(yinImgsVip[6]);
            } else if (helper.getLayoutPosition() > 45 && helper.getLayoutPosition() < 53) {
                imageView.setImageResource(yinImgsVip[7]);
            } else if (helper.getLayoutPosition() == 53) {
                imageView.setImageResource(yinImgsVip[8]);
            } else {
                imageView.setImageResource(yinImgsVip[4]);
            }
        } else if (status == 3) {
            if (helper.getLayoutPosition() == 0) {
                imageView.setImageResource(jinImgsVip[0]);
            } else if (helper.getLayoutPosition() > 0 && helper.getLayoutPosition() < 8) {
                imageView.setImageResource(jinImgsVip[1]);
            } else if (helper.getLayoutPosition() == 8) {
                imageView.setImageResource(jinImgsVip[2]);
            } else if (helper.getLayoutPosition() == 9 || helper.getLayoutPosition() == 18 || helper
                    .getLayoutPosition() == 27 || helper.getLayoutPosition() == 36) {
                imageView.setImageResource(jinImgsVip[3]);
            } else if (helper.getLayoutPosition() == 17 || helper.getLayoutPosition() == 26 ||
                    helper
                            .getLayoutPosition() == 35 || helper.getLayoutPosition() == 44) {
                imageView.setImageResource(jinImgsVip[5]);
            } else if (helper.getLayoutPosition() == 45) {
                imageView.setImageResource(jinImgsVip[6]);
            } else if (helper.getLayoutPosition() > 45 && helper.getLayoutPosition() < 53) {
                imageView.setImageResource(jinImgsVip[7]);
            } else if (helper.getLayoutPosition() == 53) {
                imageView.setImageResource(jinImgsVip[8]);
            } else {
                imageView.setImageResource(jinImgsVip[4]);
            }
        }


    }

    /**
     * 拼图颜色值规则：(int)(答对数量/总题数)*100
     * 0 未答题、总题数为空 锁住
     * [0-60) 青铜
     * [60-80) 白银
     * [80-100) 黄金
     *
     * @return
     */
    public int checkLevel(PintuModel.LectureinfoBean.SectionListBean sectionListBean) {
        if (sectionListBean.getQuestionNum() == 0 || sectionListBean.getRightNum() +
                sectionListBean.getErrorNum() == 0)
            return 0;
        double scoal = sectionListBean.getRightNum() / sectionListBean.getQuestionNum();
        if (scoal > 0 && scoal < 0.6) return 1;
        else if (scoal > 0.6 && scoal < 0.8) return 2;
        else if (scoal > 0.8) return 3;
        return 0;

    }
}
