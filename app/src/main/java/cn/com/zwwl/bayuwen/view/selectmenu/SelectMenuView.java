package cn.com.zwwl.bayuwen.view.selectmenu;

import android.content.Context;
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
import cn.com.zwwl.bayuwen.model.KeTypeModel;

/**
 * 搜索菜单栏
 * Created by vonchenchen on 2016/4/5 0005.
 */
public class SelectMenuView extends LinearLayout {

    private Context mContext;

    /**
     * 两级筛选
     */
    private SubjectHolder mTwoHolder2, mTwoHolder4, mTwoHolder5;
    /**
     * 一级筛选
     */
    private SortHolder mOneHolder1;
    private SortHolder mOneHolder3;

    /**
     * 数据
     */
    private List<SelectTempModel> dataLists1 = new ArrayList<>();
    private List<List<SelectTempModel>> dataLists2 = new ArrayList<>();
    private List<SelectTempModel> dataLists3 = new ArrayList<>();
    private List<List<SelectTempModel>> dataLists4 = new ArrayList<>();
    private List<List<SelectTempModel>> dataLists5 = new ArrayList<>();


    private OnMenuSelectDataChangedListener mOnMenuSelectDataChangedListener;
    private RelativeLayout mContentLayout;

    private TextView mText1, mText2, mText3, mText4, mText5;
    private ImageView arrowImg1, arrowImg2, arrowImg3, arrowImg4, arrowImg5;


    private int mTabRecorder = -1;
    private boolean isPopShow = false;// 是否展开筛选器

    public SelectMenuView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public SelectMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    /**
     * 设置数据
     *
     * @param keTypeModel
     */
    public void setData(KeTypeModel keTypeModel) {
        dataLists1.clear();
        dataLists1.addAll(SelectTempModel.parseGrades(keTypeModel.getGrades()));
        mOneHolder1.refreshData(dataLists1, 0);
        dataLists2.clear();
        dataLists2.addAll(SelectTempModel.parseCourse(keTypeModel.getCourseType()));
        mTwoHolder2.refreshData(dataLists2, 0, -1);
        dataLists3.clear();
        dataLists3.addAll(SelectTempModel.parseClass(keTypeModel.getClassType()));
        mOneHolder3.refreshData(dataLists3, 0);
        dataLists4.clear();
        dataLists4.addAll(SelectTempModel.parseSchool(keTypeModel.getSchools()));
        mTwoHolder4.refreshData(dataLists4, 0, -1);
        dataLists5.clear();
        dataLists5.addAll(SelectTempModel.parseTimes(keTypeModel.getSchooltimes()));
        mTwoHolder5.refreshData(dataLists5, 0, -1);
    }

    private void init() {
        mOneHolder1 = new SortHolder(mContext);
        mOneHolder1.setOnSortInfoSelectedListener(new SortHolder.OnSortInfoSelectedListener() {
            @Override
            public void onSortInfoSelected(SelectTempModel info) {
                if (mOnMenuSelectDataChangedListener != null) {
                    mOnMenuSelectDataChangedListener.onSortChanged(info, 1);
                }
                dismissPopupWindow();
                mText1.setText(info.getText());
            }
        });

        mTwoHolder2 = new SubjectHolder(mContext);
        mTwoHolder2.setOnRightListViewItemSelectedListener(new SubjectHolder
                .OnRightListViewItemSelectedListener() {
            @Override
            public void OnRightListViewItemSelected(SelectTempModel info) {
                if (mOnMenuSelectDataChangedListener != null) {
                    mOnMenuSelectDataChangedListener.onSortChanged(info, 2);
                }
                dismissPopupWindow();
                mText2.setText(info.getText());
            }
        });

        mOneHolder3 = new SortHolder(mContext);
        mOneHolder3.setOnSortInfoSelectedListener(new SortHolder.OnSortInfoSelectedListener() {
            @Override
            public void onSortInfoSelected(SelectTempModel info) {
                if (mOnMenuSelectDataChangedListener != null) {
                    mOnMenuSelectDataChangedListener.onSortChanged(info, 3);
                }
                dismissPopupWindow();
                mText3.setText(info.getText());
            }
        });

        mTwoHolder4 = new SubjectHolder(mContext);
        mTwoHolder4.setOnRightListViewItemSelectedListener(new SubjectHolder
                .OnRightListViewItemSelectedListener() {
            @Override
            public void OnRightListViewItemSelected(SelectTempModel info) {
                if (mOnMenuSelectDataChangedListener != null) {
                    mOnMenuSelectDataChangedListener.onSortChanged(info, 4);
                }
                dismissPopupWindow();
                mText4.setText(info.getText());
            }
        });

        mTwoHolder5 = new SubjectHolder(mContext);
        mTwoHolder5.setOnRightListViewItemSelectedListener(new SubjectHolder
                .OnRightListViewItemSelectedListener() {
            @Override
            public void OnRightListViewItemSelected(SelectTempModel info) {
                if (mOnMenuSelectDataChangedListener != null) {
                    mOnMenuSelectDataChangedListener.onSortChanged(info, 5);
                }
                dismissPopupWindow();
                mText5.setText(info.getText());
            }
        });
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(mContext, R.layout.layout_search_menu, this);

        mText1 = findViewById(R.id.layout1_tv);
        mText2 = findViewById(R.id.layout2_tv);
        mText3 = findViewById(R.id.layout3_tv);
        mText4 = findViewById(R.id.layout4_tv);
        mText5 = findViewById(R.id.layout5_tv);
        arrowImg1 = findViewById(R.id.layout1_img);
        arrowImg2 = findViewById(R.id.layout2_img);
        arrowImg3 = findViewById(R.id.layout3_img);
        arrowImg4 = findViewById(R.id.layout4_img);
        arrowImg5 = findViewById(R.id.layout5_img);

        mContentLayout = findViewById(R.id.rl_content);

        //点击年级
        findViewById(R.id.layout1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMenuSelectDataChangedListener != null) {
                    mOnMenuSelectDataChangedListener.onViewClicked(mOneHolder1.getRootView());
                }
                handleClickView(mOneHolder1.getRootView(), 1);
            }
        });
        //点击 项目
        findViewById(R.id.layout2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMenuSelectDataChangedListener != null) {
                    mOnMenuSelectDataChangedListener.onViewClicked(mTwoHolder2.getRootView());
                }
                handleClickView(mTwoHolder2.getRootView(), 2);
            }
        });
        //点击 类型
        findViewById(R.id.layout3).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMenuSelectDataChangedListener != null) {
                    mOnMenuSelectDataChangedListener.onViewClicked(mOneHolder3.getRootView());
                }
                handleClickView(mOneHolder3.getRootView(), 3);
            }
        });

        //点击 校区
        findViewById(R.id.layout4).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMenuSelectDataChangedListener != null) {
                    mOnMenuSelectDataChangedListener.onViewClicked(mTwoHolder4.getRootView());
                }
                handleClickView(mTwoHolder4.getRootView(), 4);
            }
        });
        //点击 筛选
        findViewById(R.id.layout5).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMenuSelectDataChangedListener != null) {
                    mOnMenuSelectDataChangedListener.onViewClicked(mTwoHolder5.getRootView());
                }
                handleClickView(mTwoHolder5.getRootView(), 5);
            }
        });

        mContentLayout.setOnClickListener(new OnClickListener() {
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
        isPopShow = true;
        setTabExtend(type);
        mTabRecorder = type;
    }


    private void dismissPopupWindow() {
        mContentLayout.removeAllViews();
        isPopShow = false;
        setTabClose();
    }


    /**
     * 接口
     */
    public void setOnMenuSelectDataChangedListener(OnMenuSelectDataChangedListener
                                                           onMenuSelectDataChangedListener) {
        this.mOnMenuSelectDataChangedListener = onMenuSelectDataChangedListener;
    }

    public interface OnMenuSelectDataChangedListener {

        void onSortChanged(SelectTempModel sortType, int type);

        void onViewClicked(View view);

    }

    private void setTabExtend(int tab) {
        if (tab == 1) {
            mText1.setTextColor(getResources().getColor(R.color.gold));
            arrowImg1.setImageResource(R.mipmap.ic_up_blue);
        } else if (tab == 2) {
            mText2.setTextColor(getResources().getColor(R.color.gold));
            arrowImg2.setImageResource(R.mipmap.ic_up_blue);
        } else if (tab == 3) {
            mText3.setTextColor(getResources().getColor(R.color.gold));
            arrowImg3.setImageResource(R.mipmap.ic_up_blue);
        } else if (tab == 4) {
            mText4.setTextColor(getResources().getColor(R.color.gold));
            arrowImg4.setImageResource(R.mipmap.ic_up_blue);
        } else if (tab == 5) {
            mText5.setTextColor(getResources().getColor(R.color.gold));
            arrowImg5.setImageResource(R.mipmap.ic_up_blue);
        }
    }

    private void resetTabExtend(int tab) {
        if (tab == 1) {
            mText1.setTextColor(getResources().getColor(R.color.gray_dark));
            arrowImg1.setImageResource(R.mipmap.ic_down);
        } else if (tab == 2) {
            mText2.setTextColor(getResources().getColor(R.color.gray_dark));
            arrowImg2.setImageResource(R.mipmap.ic_down);
        } else if (tab == 3) {
            mText3.setTextColor(getResources().getColor(R.color.gray_dark));
            arrowImg3.setImageResource(R.mipmap.ic_down);
        } else if (tab == 4) {
            mText4.setTextColor(getResources().getColor(R.color.gray_dark));
            arrowImg4.setImageResource(R.mipmap.ic_down);
        } else if (tab == 5) {
            mText5.setTextColor(getResources().getColor(R.color.gray_dark));
            arrowImg5.setImageResource(R.mipmap.ic_down);
        }
    }

    private void setTabClose() {
        mText1.setTextColor(getResources().getColor(R.color.gray_dark));
        arrowImg1.setImageResource(R.mipmap.ic_down);
        mText2.setTextColor(getResources().getColor(R.color.gray_dark));
        arrowImg2.setImageResource(R.mipmap.ic_down);
        mText3.setTextColor(getResources().getColor(R.color.gray_dark));
        arrowImg3.setImageResource(R.mipmap.ic_down);
        mText4.setTextColor(getResources().getColor(R.color.gray_dark));
        arrowImg4.setImageResource(R.mipmap.ic_down);
        mText5.setTextColor(getResources().getColor(R.color.gray_dark));
        arrowImg5.setImageResource(R.mipmap.ic_down);
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