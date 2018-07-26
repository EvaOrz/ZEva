package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.RadarAdapter;
import cn.com.zwwl.bayuwen.api.PintuIntroApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.PintuModel;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.util.UmengLogUtil;
import cn.com.zwwl.bayuwen.view.DatiPopWindow;

public class AbilityAnalysisActivity extends BaseActivity {

    @BindView(R.id.tab_course_name)
    TabLayout tabCourseName;
    @BindView(R.id.course_content)
    RadioButton courseContent;
    @BindView(R.id.student_condition)
    RadioButton studentCondition;
    @BindView(R.id.system_introduction)
    RadioButton systemIntroduction;
    @BindView(R.id.course_d_group)
    RadioGroup courseDGroup;
    @BindView(R.id.course_d_line1)
    View courseDLine1;
    @BindView(R.id.course_d_line2)
    View courseDLine2;
    @BindView(R.id.course_d_line3)
    View courseDLine3;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.questionNum)
    TextView questionNum;
    @BindView(R.id.rightNum)
    TextView rightNum;
    @BindView(R.id.errorNum)
    TextView errorNum;
    @BindView(R.id.student_content)
    LinearLayout studentContent;
    @BindView(R.id.pintu_layout)
    LinearLayout pintuLayout;
    @BindView(R.id.id_back)
    ImageView idBack;
    @BindView(R.id.title_name)
    TextView titleName;
    private ArrayList<PintuModel> pintuModels = new ArrayList<>();// 拼图数据
    private PintuModel pintuModel;

    private int pintuWid, pintuHei;// 拼图item的宽高
    private int paddingLeft, paddingRight, paddingTop, paddingBottom;
    private View pintuView;
    private RecyclerView pintuRecyclerView;
    private RadarAdapter radarAdapter;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ability_analysis);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        mActivity = this;
        initView1();
    }

    @Override
    protected void initData() {

    }


    protected void initView1() {
        titleName.setText("能力分析拼图");
        pintuWid = MyApplication.width - 200;
        pintuHei = (MyApplication.width - 200) * 6 / 9;
        paddingLeft = pintuWid * 50 / 1018;
        paddingRight = pintuWid * 50 / 1018;
        paddingTop = pintuHei * 72 / 676;
        paddingBottom = pintuHei * 112 / 676;
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(pintuWid +
                paddingLeft + paddingRight,
                pintuHei +
                        paddingTop + paddingBottom);

        pintuView = LayoutInflater.from(mActivity).inflate(R.layout.item_pingtu, null);
        pintuView.setLayoutParams(params1);
        pintuView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        pintuRecyclerView = pintuView.findViewById(R.id.radar_fragmain1);
        pintuRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(pintuWid, pintuHei));
        pintuLayout.removeAllViews();
        pintuLayout.addView(pintuView);
        setListener();
        initData1();

    }

    protected void initData1() {
        Intent intent = getIntent();
        pintuModels = (ArrayList<PintuModel>) intent.getSerializableExtra("pintuModels");
        final int pos = intent.getIntExtra("pin_pos", 0);
        for (int i = 0; i < pintuModels.size(); i++) {
            tabCourseName.addTab(tabCourseName.newTab().setText(pintuModels.get(i).getName()));
        }
        tabCourseName.setScrollPosition(pos, 0, true);

        tabCourseName.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    Field mTabStripField = tabCourseName.getClass().getDeclaredField("mTabStrip");
                    mTabStripField.setAccessible(true);
                    LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(tabCourseName);

                    int scrollX = 0;
                    int screenHalf = MyApplication.width / 2;// 屏幕宽度的一半
                    for (int i = 0; i < pos; i++) {
                        View tt = mTabStrip.getChildAt(i);
                        scrollX += getTWid(tt);
                    }

                    View tabView = mTabStrip.getChildAt(pos);
                    int ww = getTWid(tabView);
                    tabCourseName.scrollTo(scrollX - ww / 2, 0);

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

        pintuModel = pintuModels.get(pos);
        content.setText(pintuModel.getContent().

                getContent());

        initPintu();
        initAddData();
    }

    private int getTWid(View tt) {
        int width = 0;
        try {
            //拿到tabView的mTextView属性
            Field ff = tt.getClass().getDeclaredField("mTextView");
            ff.setAccessible(true);
            TextView vv = (TextView) ff.get(tt);

            width = vv.getWidth();
            if (width == 0) {
                vv.measure(0, 0);
                width = vv.getMeasuredWidth();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return width;
    }

    private void initPintu() {
        pintuView.setBackgroundResource(R.drawable.pintu_bg_wangzhe);
        if (Tools.listNotNull(pintuModel.getLectureinfo())) {
            final List<PintuModel.LectureinfoBean.SectionListBean> models = pintuModel
                    .getLectureinfo().get(0).getSectionList();
            if (Tools.listNotNull(models) && models.size() == 54) {
                radarAdapter = new RadarAdapter(models, pintuWid);
                pintuRecyclerView.setAdapter(radarAdapter);
                pintuRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 9));
                pintuRecyclerView.setItemAnimator(new DefaultItemAnimator());
            }
            radarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    getIntro(models.get(position).getSectionId(), radarAdapter.checkLevel(models
                            .get(position)) > 0);
                }
            });
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

                    Bundle bundle = msg.getData();
                    PintuModel.LectureinfoBean.SectionListBean model = (PintuModel
                            .LectureinfoBean.SectionListBean) bundle.getSerializable("gezi_detail");
                    boolean is = bundle.getBoolean("isShowDeatil");
                    new DatiPopWindow(mContext, model, is);
                    break;
            }
        }
    };

    /**
     * 获取格子详情
     */
    private void getIntro(int selectionId, final boolean isShowDeatil) {
        new PintuIntroApi(mContext, selectionId, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                if (entry != null) {
                    Message message = new Message();
                    Bundle b = new Bundle();
                    b.putBoolean("isShowDeatil", isShowDeatil);
                    b.putSerializable("gezi_detail", entry);
                    message.what = 0;
                    message.setData(b);
                    handler.sendMessage(message);
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                if (error != null) showToast(error.getDesc());
            }
        });
    }


    protected void setListener() {
        tabCourseName.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pintuModel = pintuModels.get(tab.getPosition());
                initPintu();
                initAddData();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void initAddData() {
        courseContent.setText(pintuModel.getContent().getTitle());
        studentCondition.setText(pintuModel.getStudent_info().getTitle());
        systemIntroduction.setText(pintuModel.getCurricula().getTitle());
    }

    @OnClick({R.id.course_content, R.id.system_introduction, R.id.student_condition, R.id
            .id_back})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.course_content:
                UmengLogUtil.logPintuTabClick(mContext, 0);
                courseDLine1.setVisibility(View.VISIBLE);
                courseDLine2.setVisibility(View.INVISIBLE);
                courseDLine3.setVisibility(View.INVISIBLE);
                content.setVisibility(View.VISIBLE);
                studentContent.setVisibility(View.GONE);
                content.setText(pintuModel.getContent().getContent());
                break;
            case R.id.student_condition:
                UmengLogUtil.logPintuTabClick(mContext, 1);
                courseDLine1.setVisibility(View.INVISIBLE);
                courseDLine2.setVisibility(View.VISIBLE);
                courseDLine3.setVisibility(View.INVISIBLE);
                content.setVisibility(View.GONE);
                studentContent.setVisibility(View.VISIBLE);
                if (pintuModel.getLectureinfo() != null && pintuModel.getLectureinfo().size() !=
                        0) {
                    questionNum.setText("总题目数：" + pintuModel.getLectureinfo().get(0)
                            .getQuestionNum());
                    rightNum.setText("答对题数：" + pintuModel.getLectureinfo().get(0).getRightNum
                            ());
                    errorNum.setText("答错题数：" + pintuModel.getLectureinfo().get(0).getErrorNum
                            ());
                }
                break;
            case R.id.system_introduction:
                UmengLogUtil.logPintuTabClick(mContext, 2);
                courseDLine1.setVisibility(View.INVISIBLE);
                courseDLine2.setVisibility(View.INVISIBLE);
                courseDLine3.setVisibility(View.VISIBLE);
                content.setVisibility(View.VISIBLE);
                studentContent.setVisibility(View.GONE);
                content.setText(pintuModel.getCurricula().getContent());
                break;
            case R.id.id_back:
                finish();
                break;
        }

    }


}
