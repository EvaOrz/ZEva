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
import java.util.List;

import cn.com.zwwl.bayuwen.R;

public class BannerView extends FrameLayout {

    private static final int MSG_LOOP = 1000;
    private static long LOOP_INTERVAL = 5000;
    private LinearLayout mLinearPosition = null;
    private ViewPager mViewPager = null;
    private BannerHandler mBannerHandler = null;

    private List<View> viewList;
    private int viewSize;

    private static class BannerHandler extends Handler {
        private WeakReference<BannerView> weakReference = null;

        public BannerHandler(BannerView bannerView) {
            super(Looper.getMainLooper());
            this.weakReference = new WeakReference<BannerView>(bannerView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (this.weakReference == null) {
                return;
            }
            BannerView bannerView = this.weakReference.get();
            if (bannerView == null || bannerView.mViewPager == null || bannerView.mViewPager.getAdapter() == null || bannerView.mViewPager.getAdapter().getCount() <= 0) {
//                sendEmptyMessageDelayed(MSG_LOOP, LOOP_INTERVAL);
                return;
            }
            int curPos = bannerView.mViewPager.getCurrentItem();
            curPos = (curPos + 1) % bannerView.mViewPager.getAdapter().getCount();
            bannerView.mViewPager.setCurrentItem(curPos);
            if (hasMessages(MSG_LOOP)) {
                removeMessages(MSG_LOOP);
            }
            sendEmptyMessageDelayed(MSG_LOOP, LOOP_INTERVAL);
        }
    }

    public BannerView(Context context) {
        super(context);
        init();
    }

    public BannerView(Context context, AttributeSet attrs) {
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
        initViewPager();
        initLinearPosition();
        this.addView(mViewPager);
        this.addView(mLinearPosition);
    }

    private void initViewPager() {
        mViewPager = new ViewPager(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        mViewPager.setLayoutParams(layoutParams);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateLinearPosition();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setOnTouchListener(new OnTouchListener() {
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
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.dimen_9dp);
        mLinearPosition.setPadding(getResources().getDimensionPixelSize(R.dimen.dimen_9dp), 0, 0, 0);
        mLinearPosition.setLayoutParams(layoutParams);
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
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.rightMargin = getResources().getDimensionPixelOffset(R.dimen.dimen_9dp);
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
                    mLinearPosition.getChildAt(i).setBackgroundResource(R.drawable.banner_point_select);
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
            BannerAdapter bannerAdapter = new BannerAdapter(viewList);
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

    public class BannerAdapter extends PagerAdapter {

        private List<View> viewList;
        private int size;
        private final int cacheCount = 3;

        public BannerAdapter(List<View> viewList) {
            this.viewList = viewList;
            size = viewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (viewList.size() > cacheCount) {
                container.removeView(viewList.get(position % size));
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ViewGroup parent = (ViewGroup) viewList.get(position % size).getParent();
            if (parent != null) {
                parent.removeView(viewList.get(position % size));
            }
            container.addView(viewList.get(position % size));
            return viewList.get(position % size);
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}