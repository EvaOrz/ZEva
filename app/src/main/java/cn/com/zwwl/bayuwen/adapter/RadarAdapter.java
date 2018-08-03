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
    private boolean isPaid = false;

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

    private int[] suoImgs = new int[]{R.drawable.tu37, R.drawable.tu38, R.drawable.tu39, R.drawable
            .tu40, R.drawable.tu41, R.drawable.tu42, R.drawable.tu43, R.drawable.tu44, R.drawable
            .tu45};
    private int[] tongImgs = new int[]{R.drawable.tu64, R.drawable.tu65, R.drawable.tu66, R
            .drawable
            .tu67, R.drawable.tu68, R.drawable.tu69, R.drawable.tu70, R.drawable.tu71, R.drawable
            .tu72};
    private int[] yinImgs = new int[]{R.drawable.tu55, R.drawable.tu56, R.drawable.tu57, R
            .drawable.tu58, R.drawable.tu59, R.drawable.tu60, R.drawable.tu61, R.drawable.tu62, R
            .drawable
            .tu63};
    private int[] jinImgs = new int[]{R.drawable.tu46, R.drawable.tu47, R.drawable.tu48, R
            .drawable
            .tu49, R.drawable.tu50, R.drawable.tu51, R.drawable.tu52, R.drawable.tu53, R.drawable
            .tu54};


    public RadarAdapter(@Nullable List<PintuModel.LectureinfoBean.SectionListBean> data) {
        super(R.layout.item_radar, data);
    }

    public RadarAdapter(@Nullable List<PintuModel.LectureinfoBean.SectionListBean> data, int
            totalWidth, boolean isPaid) {
        super(R.layout.item_radar, data);
        this.totalWidth = totalWidth;
        this.isPaid = isPaid;
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
                imageView.setImageResource(isPaid ? suoImgsVip[0] : suoImgs[0]);
            } else if (helper.getLayoutPosition() > 0 && helper.getLayoutPosition() < 8) {
                imageView.setImageResource(isPaid ? suoImgsVip[1] : suoImgs[1]);
            } else if (helper.getLayoutPosition() == 8) {
                imageView.setImageResource(isPaid ? suoImgsVip[2] : suoImgs[2]);
            } else if (helper.getLayoutPosition() == 9 || helper.getLayoutPosition() == 18 || helper
                    .getLayoutPosition() == 27 || helper.getLayoutPosition() == 36) {
                imageView.setImageResource(isPaid ? suoImgsVip[3] : suoImgs[3]);
            } else if (helper.getLayoutPosition() == 17 || helper.getLayoutPosition() == 26 ||
                    helper
                            .getLayoutPosition() == 35 || helper.getLayoutPosition() == 44) {
                imageView.setImageResource(isPaid ? suoImgsVip[5] : suoImgs[5]);
            } else if (helper.getLayoutPosition() == 45) {
                imageView.setImageResource(isPaid ? suoImgsVip[6] : suoImgs[6]);
            } else if (helper.getLayoutPosition() > 45 && helper.getLayoutPosition() < 53) {
                imageView.setImageResource(isPaid ? suoImgsVip[7] : suoImgs[7]);
            } else if (helper.getLayoutPosition() == 53) {
                imageView.setImageResource(isPaid ? suoImgsVip[8] : suoImgs[8]);
            } else {
                imageView.setImageResource(isPaid ? suoImgsVip[4] : suoImgs[4]);
            }
        } else if (status == 1) {
            if (helper.getLayoutPosition() == 0) {
                imageView.setImageResource(isPaid ? tongImgsVip[0] : tongImgs[0]);
            } else if (helper.getLayoutPosition() > 0 && helper.getLayoutPosition() < 8) {
                imageView.setImageResource(isPaid ? tongImgsVip[1] : tongImgs[1]);
            } else if (helper.getLayoutPosition() == 8) {
                imageView.setImageResource(isPaid ? tongImgsVip[2] : tongImgs[2]);
            } else if (helper.getLayoutPosition() == 9 || helper.getLayoutPosition() == 18 || helper
                    .getLayoutPosition() == 27 || helper.getLayoutPosition() == 36) {
                imageView.setImageResource(isPaid ? tongImgsVip[3] : tongImgs[3]);
            } else if (helper.getLayoutPosition() == 17 || helper.getLayoutPosition() == 26 ||
                    helper
                            .getLayoutPosition() == 35 || helper.getLayoutPosition() == 44) {
                imageView.setImageResource(isPaid ? tongImgsVip[5] : tongImgs[5]);
            } else if (helper.getLayoutPosition() == 45) {
                imageView.setImageResource(isPaid ? tongImgsVip[6] : tongImgs[6]);
            } else if (helper.getLayoutPosition() > 45 && helper.getLayoutPosition() < 53) {
                imageView.setImageResource(isPaid ? tongImgsVip[7] : tongImgs[7]);
            } else if (helper.getLayoutPosition() == 53) {
                imageView.setImageResource(isPaid ? tongImgsVip[8] : tongImgs[8]);
            } else {
                imageView.setImageResource(isPaid ? tongImgsVip[4] : tongImgs[4]);
            }
        } else if (status == 2) {
            if (helper.getLayoutPosition() == 0) {
                imageView.setImageResource(isPaid ? yinImgsVip[0] : yinImgs[0]);
            } else if (helper.getLayoutPosition() > 0 && helper.getLayoutPosition() < 8) {
                imageView.setImageResource(isPaid ? yinImgsVip[1] : yinImgs[1]);
            } else if (helper.getLayoutPosition() == 8) {
                imageView.setImageResource(isPaid ? yinImgsVip[2] : yinImgs[2]);
            } else if (helper.getLayoutPosition() == 9 || helper.getLayoutPosition() == 18 || helper
                    .getLayoutPosition() == 27 || helper.getLayoutPosition() == 36) {
                imageView.setImageResource(isPaid ? yinImgsVip[3] : yinImgs[3]);
            } else if (helper.getLayoutPosition() == 17 || helper.getLayoutPosition() == 26 ||
                    helper
                            .getLayoutPosition() == 35 || helper.getLayoutPosition() == 44) {
                imageView.setImageResource(isPaid ? yinImgsVip[5] : yinImgs[5]);
            } else if (helper.getLayoutPosition() == 45) {
                imageView.setImageResource(isPaid ? yinImgsVip[6] : yinImgs[6]);
            } else if (helper.getLayoutPosition() > 45 && helper.getLayoutPosition() < 53) {
                imageView.setImageResource(isPaid ? yinImgsVip[7] : yinImgs[7]);
            } else if (helper.getLayoutPosition() == 53) {
                imageView.setImageResource(isPaid ? yinImgsVip[8] : yinImgs[8]);
            } else {
                imageView.setImageResource(isPaid ? yinImgsVip[4] : yinImgs[4]);
            }
        } else if (status == 3) {
            if (helper.getLayoutPosition() == 0) {
                imageView.setImageResource(isPaid ? jinImgsVip[0] : jinImgs[0]);
            } else if (helper.getLayoutPosition() > 0 && helper.getLayoutPosition() < 8) {
                imageView.setImageResource(isPaid ? jinImgsVip[1] : jinImgs[1]);
            } else if (helper.getLayoutPosition() == 8) {
                imageView.setImageResource(isPaid ? jinImgsVip[2] : jinImgs[2]);
            } else if (helper.getLayoutPosition() == 9 || helper.getLayoutPosition() == 18 || helper
                    .getLayoutPosition() == 27 || helper.getLayoutPosition() == 36) {
                imageView.setImageResource(isPaid ? jinImgsVip[3] : jinImgs[3]);
            } else if (helper.getLayoutPosition() == 17 || helper.getLayoutPosition() == 26 ||
                    helper
                            .getLayoutPosition() == 35 || helper.getLayoutPosition() == 44) {
                imageView.setImageResource(isPaid ? jinImgsVip[5] : jinImgs[5]);
            } else if (helper.getLayoutPosition() == 45) {
                imageView.setImageResource(isPaid ? jinImgsVip[6] : jinImgs[6]);
            } else if (helper.getLayoutPosition() > 45 && helper.getLayoutPosition() < 53) {
                imageView.setImageResource(isPaid ? jinImgsVip[7] : jinImgs[7]);
            } else if (helper.getLayoutPosition() == 53) {
                imageView.setImageResource(isPaid ? jinImgsVip[8] : jinImgs[8]);
            } else {
                imageView.setImageResource(isPaid ? jinImgsVip[4] : jinImgs[4]);
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
        double scoal = sectionListBean.getRightNum() * 1.0 / sectionListBean.getQuestionNum();
        if (scoal >= 0 && scoal < 0.6) return 1;
        else if (scoal >= 0.6 && scoal < 0.8) return 2;
        else if (scoal >= 0.8) return 3;
        return 0;

    }
}
