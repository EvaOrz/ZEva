package cn.com.zwwl.bayuwen.view.selectmenu;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.TeacherTypeModel;
import cn.com.zwwl.bayuwen.util.Tools;

/**
 * 教师条件筛选器
 */
public class TeacherMenuView extends LinearLayout {

    private Context mContext;
    private TextView sureBt;// 确定按钮
    private CheckBoxHolder mTwoHolder1, mTwoHolder2;
    private OnSureClickListener onSureClickListener;// 确定点击事件

    /**
     * 数据
     */
    private List<List<SelectTempModel>> dataLists1 = new ArrayList<>();
    private List<List<SelectTempModel>> dataLists2 = new ArrayList<>();

    private RelativeLayout mContentLayout, rl_trans_layout;

    private TextView mText1, mText2;
    private ImageView arrowImg1, arrowImg2;


    private int mTabRecorder = -1;
    private boolean isPopShow = false;// 是否展开筛选器

    public TeacherMenuView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public TeacherMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    /**
     * 设置数据
     *
     * @param teacherTypeModel
     */
    public void setData(TeacherTypeModel teacherTypeModel) {
        dataLists1.clear();
        dataLists1.addAll(SelectTempModel.parseTGrades(teacherTypeModel.getGrades()));
        mTwoHolder1.refreshData(dataLists1);
        dataLists2.clear();
        dataLists2.addAll(SelectTempModel.parseCourse(teacherTypeModel.getCourseType()));
        mTwoHolder2.refreshData(dataLists2);
    }

    private void init() {
        mTwoHolder1 = new CheckBoxHolder(mContext);
        mTwoHolder2 = new CheckBoxHolder(mContext);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(mContext, R.layout.layout_teacher_menu, this);

        mText1 = findViewById(R.id.layout1_tv);
        mText2 = findViewById(R.id.layout2_tv);
        arrowImg1 = findViewById(R.id.layout1_img);
        arrowImg2 = findViewById(R.id.layout2_img);
        sureBt = findViewById(R.id.menu_sure);
        mContentLayout = findViewById(R.id.rl_content);
        rl_trans_layout = findViewById(R.id.rl_trans_layout);

        //点击年级
        findViewById(R.id.layout1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                handleClickView(mTwoHolder1.getRootView(), 1);
            }
        });
        //点击 项目
        findViewById(R.id.layout2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                handleClickView(mTwoHolder2.getRootView(), 2);
            }
        });
        sureBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTabRecorder == 1) {
                    if (Tools.listNotNull(mTwoHolder1.getCheckedData())) {
                        String gtxt = "";
                        for (SelectTempModel selectTempModel : mTwoHolder1.getCheckedData()) {
                            if (selectTempModel.isCheck())
                                gtxt += selectTempModel.getText() + ",";
                        }
                        if (!TextUtils.isEmpty(gtxt))
                            mText1.setText(gtxt.substring(0, gtxt.length() - 1));
                        onSureClickListener.onClick(mTwoHolder1.getCheckedData(), 1);
                    }

                } else if (mTabRecorder == 2) {
                    if (Tools.listNotNull(mTwoHolder2.getCheckedData())){
                        String gtxt = "";
                        for (SelectTempModel selectTempModel : mTwoHolder2.getCheckedData()) {
                            if (selectTempModel.isCheck())
                                gtxt += selectTempModel.getText() + ",";
                        }
                        if (!TextUtils.isEmpty(gtxt))
                            mText2.setText(gtxt.substring(0, gtxt.length() - 1));
                        onSureClickListener.onClick(mTwoHolder2.getCheckedData(), 2);
                    }

                }
                dismissPopupWindow();
            }
        });

        rl_trans_layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPopupWindow();
            }
        });
    }

    /**
     * 弹出menu页面
     *
     * @param view
     * @param type
     */
    private void handleClickView(View view, int type) {
        // 点击相同tab 收起筛选器
        if (mTabRecorder == type && isPopShow) {
            dismissPopupWindow();
            return;
        }
        if (mTabRecorder != -1) {
            resetTabExtend(mTabRecorder);
        }
        mContentLayout.removeAllViews();
        mContentLayout.addView(view, ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rl_trans_layout.removeAllViews();
        rl_trans_layout.addView(new View(mContext), ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        isPopShow = true;
        setTabExtend(type);
        sureBt.setVisibility(View.VISIBLE);
        mTabRecorder = type;
    }


    private void dismissPopupWindow() {
        mContentLayout.removeAllViews();
        rl_trans_layout.removeAllViews();
        sureBt.setVisibility(View.GONE);
        isPopShow = false;
        setTabClose();
    }


    /**
     * 接口
     */
    public void setOnSureClickListener(OnSureClickListener onSureClickListener) {
        this.onSureClickListener = onSureClickListener;
    }

    public interface OnSureClickListener {
        // 1：年级 2：项目
        void onClick(List<SelectTempModel> checkedList, int type);
    }

    private void setTabExtend(int tab) {
        if (tab == 1) {
            mText1.setTextColor(getResources().getColor(R.color.gray_dark));
            arrowImg1.setImageResource(R.mipmap.ic_up_blue);
        } else if (tab == 2) {
            mText2.setTextColor(getResources().getColor(R.color.gray_dark));
            arrowImg2.setImageResource(R.mipmap.ic_up_blue);
        }
    }

    private void resetTabExtend(int tab) {
        if (tab == 1) {
            mText1.setTextColor(getResources().getColor(R.color.gray_dark));
            arrowImg1.setImageResource(R.mipmap.ic_down);
        } else if (tab == 2) {
            mText2.setTextColor(getResources().getColor(R.color.gray_dark));
            arrowImg2.setImageResource(R.mipmap.ic_down);
        }
    }

    private void setTabClose() {
        mText1.setTextColor(getResources().getColor(R.color.gray_dark));
        arrowImg1.setImageResource(R.mipmap.ic_down);
        mText2.setTextColor(getResources().getColor(R.color.gray_dark));
        arrowImg2.setImageResource(R.mipmap.ic_down);
    }

    /**
     * 清除控件内部选项
     */
    public void clearAllInfo() {

//        mSubjectHolder.refreshData(mSubjectDataList, 0, -1);
//        mSortHolder.refreshView(null);
//        mSortHolder.refreshView(null);

        //清除菜单栏显示
//        mSubjectText.setText("项目");
//        mSortText.setText("类型");
    }
}