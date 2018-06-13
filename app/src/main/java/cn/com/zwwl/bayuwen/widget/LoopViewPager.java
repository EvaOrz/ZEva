package cn.com.zwwl.bayuwen.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;

public class LoopViewPager extends LinearLayout {

    private static final int MSG_LOOP = 1000;
    private static long LOOP_INTERVAL = 5000;
    private LinearLayout mLinearPosition = null;
    private ViewPager mViewPager = null;
    private BannerHandler mBannerHandler = null;

    private List<View> viewList;
    private int viewSize;

    private static class BannerHandler extends Handler {
        private WeakReference<LoopViewPager> weakReference = null;

        public BannerHandler(LoopViewPager bannerView) {
            super(Looper.getMainLooper());
            this.weakReference = new WeakReference<LoopViewPager>(bannerView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (this.weakReference == null) {
                return;
            }
            LoopViewPager loopViewPager = this.weakReference.get();
            if (loopViewPager == null || loopViewPager.mViewPager == null || loopViewPager
                    .mViewPager.getAdapter() == null || loopViewPager.mViewPager.getAdapter()
                    .getCount() <= 0) {
//                sendEmptyMessageDelayed(MSG_LOOP, LOOP_INTERVAL);
                return;
            }
            int curPos = loopViewPager.mViewPager.getCurrentItem();
            curPos = (curPos + 1) % loopViewPager.mViewPager.getAdapter().getCount();
            loopViewPager.mViewPager.setCurrentItem(curPos);
            if (hasMessages(MSG_LOOP)) {
                removeMessages(MSG_LOOP);
            }
            sendEmptyMessageDelayed(MSG_LOOP, LOOP_INTERVAL);
        }
    }

    public LoopViewPager(Context context) {
        super(context);

        init();
    }

    public void setSize(LayoutParams params) {
        mViewPager.setLayoutParams(params);

    }


    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void startLoop(boolean flag) {
        if (flag) {
            if (mBannerHandler == null) {
                mBannerHandler = new BannerHandler(this);
            }
            mBannerHandler.sendEmptyMessageDelayed(MSG_LOOP, LOOP_INTERVAL);
        } else {
            if (mBannerHandler != null) {
                if (mBannerHandler.hasMessages(MSG_LOOP)) {
                    mBannerHandler.removeMessages(MSG_LOOP);
                    mBannerHandler = null;
                }
            }
        }
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        initViewPager();
        initLinearPosition();
        this.addView(mViewPager);
        this.addView(mLinearPosition);
    }

    private void initViewPager() {
        mViewPager = new ViewPager(getContext());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateLinearPosition();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mBannerHandler != null) {
                            if (mBannerHandler.hasMessages(MSG_LOOP)) {
                                mBannerHandler.removeMessages(MSG_LOOP);
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mBannerHandler != null) {
                            mBannerHandler.sendEmptyMessageDelayed(MSG_LOOP, LOOP_INTERVAL);
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void initLinearPosition() {
        mLinearPosition = new LinearLayout(getContext());
        mLinearPosition.setOrientation(LinearLayout.HORIZONTAL);
        mLinearPosition.setPadding(0, 10, 0,
                10);
        mLinearPosition.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams
                .WRAP_CONTENT));
    }

    public void setAdapter(PagerAdapter adapter) {
        mViewPager.setAdapter(adapter);
        adapter.registerDataSetObserver(mDataObserver);
        updateLinearPosition();
    }

    private DataSetObserver mDataObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            updateLinearPosition();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    };

    private void updateLinearPosition() {
        if (viewList != null && viewList.size() != 0) {
            if (mLinearPosition.getChildCount() != viewSize) {
                int diffCnt = mLinearPosition.getChildCount() - viewSize;
                boolean needAdd = diffCnt < 0;
                diffCnt = Math.abs(diffCnt);
                for (int i = 0; i < diffCnt; i++) {
                    if (needAdd) {
                        ImageView img = new ImageView(getContext());
                        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                                LayoutParams
                                        .WRAP_CONTENT);
                        layoutParams.rightMargin = getResources().getDimensionPixelOffset(R.dimen
                                .dimen_9dp);
                        img.setLayoutParams(layoutParams);
                        img.setBackgroundResource(R.drawable.banner_point);
                        mLinearPosition.addView(img);
                    } else {
                        mLinearPosition.removeViewAt(0);
                    }
                }
            }
            int curPos = mViewPager.getCurrentItem();
            for (int i = 0; i < mLinearPosition.getChildCount(); i++) {
                if (i == (curPos % viewSize)) {
                    mLinearPosition.getChildAt(i).setBackgroundResource(R.drawable
                            .banner_point_select);
                } else {
                    mLinearPosition.getChildAt(i).setBackgroundResource(R.drawable.banner_point);
                }
            }
        }
    }

    public void setViewList(List<View> viewList) {
        this.viewList = viewList;
        if (viewList != null && viewList.size() != 0) {
            viewSize = viewList.size();
            ImageBannerAdapter bannerAdapter = new ImageBannerAdapter(viewList);
            setAdapter(bannerAdapter);
        }
    }


    public void setLoopInterval(long loopInterval) {
        LOOP_INTERVAL = loopInterval;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mBannerHandler != null) {
            mBannerHandler.removeMessages(MSG_LOOP);
            mBannerHandler = null;
        }
    }

    /**
     * banner 适配
     */
    public class ImageBannerAdapter extends PagerAdapter {
        protected List<View> list = new ArrayList<>();

        public ImageBannerAdapter(List<View> list) {
            this.list = list;
        }


        @Override
        public int getCount() {
            if (list != null && list.size() > 0) {
                return list.size();
            } else {
                return 0;
            }
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
//            final RecommentModel recommentModel = list.get(position);
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
        }

    }
}