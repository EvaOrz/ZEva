/*
 * Copyright 2014 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.com.zwwl.bayuwen.widget.observable;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * ScrollView that its scroll position can be observed.
 */
public class ObservableScrollView extends ScrollView implements Scrollable {

    // Fields that should be saved onSaveInstanceState
    private int mPrevScrollY;
    private int mScrollY;

    // Fields that don't need to be saved onSaveInstanceState
    private ObservableScrollViewCallbacks mCallbacks;
    private List<ObservableScrollViewCallbacks> mCallbackCollection;
    private ScrollState mScrollState;
    private boolean mFirstScroll;
    private boolean mDragging;
    private boolean mIntercepted;
    private MotionEvent mPrevMoveEvent;
    private ViewGroup mTouchInterceptionViewGroup;

    //    用于记录下拉位置
    private float y = 0f;
    //    zoomView原本的宽高
    private int zoomViewWidth = 0;
    private int zoomViewHeight = 0;

    //    是否正在放大
    private boolean mScaling = false;

    //    放大的view，默认为第一个子view
    private View zoomView;

    //    滑动放大系数，系数越大，滑动时放大程度越大
    private float mScaleRatio = 0.8f;

    //    最大的放大倍数
    private float mScaleTimes = 2f;

    //    回弹时间系数，系数越小，回弹越快
    private float mReplyRatio = 0.5f;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        mPrevScrollY = ss.prevScrollY;
        mScrollY = ss.scrollY;
        super.onRestoreInstanceState(ss.getSuperState());
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.prevScrollY = mPrevScrollY;
        ss.scrollY = mScrollY;
        return ss;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (hasNoCallbacks()) {
            return;
        }
        mScrollY = t;

        dispatchOnScrollChanged(t, mFirstScroll, mDragging);
        if (mFirstScroll) {
            mFirstScroll = false;
        }

        if (mPrevScrollY < t) {
            mScrollState = ScrollState.UP;
        } else if (t < mPrevScrollY) {
            mScrollState = ScrollState.DOWN;
            //} else {
            // Keep previous state while dragging.
            // Never makes it STOP even if scrollY not changed.
            // Before Android 4.4, onTouchEvent calls onScrollChanged directly for ACTION_MOVE,
            // which makes mScrollState always STOP when onUpOrCancelMotionEvent is called.
            // STOP state is now meaningless for ScrollView.
        }
        mPrevScrollY = t;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (hasNoCallbacks()) {
            return super.onInterceptTouchEvent(ev);
        }
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                // Whether or not motion events are consumed by children,
                // flag initializations which are related to ACTION_DOWN events should be executed.
                // Because if the ACTION_DOWN is consumed by children and only ACTION_MOVEs are
                // passed to parent (this view), the flags will be invalid.
                // Also, applications might implement initialization codes to onDownMotionEvent,
                // so call it here.
                mFirstScroll = mDragging = true;
                dispatchOnDownMotionEvent();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (zoomViewWidth <= 0 || zoomViewHeight <= 0) {
            zoomViewWidth = zoomView.getMeasuredWidth();
            zoomViewHeight = zoomView.getMeasuredHeight();
        }

        if (hasNoCallbacks()) {
            return super.onTouchEvent(ev);
        }
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIntercepted = false;
                mDragging = false;
                dispatchOnUpOrCancelMotionEvent(mScrollState);

                //恢复view
                mScaling = false;
                replyView();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mPrevMoveEvent == null) {
                    mPrevMoveEvent = ev;
                }
                float diffY = ev.getY() - mPrevMoveEvent.getY();
                mPrevMoveEvent = MotionEvent.obtainNoHistory(ev);
                if (getCurrentScrollY() - diffY <= 0) {
                    // Can't scroll anymore.

                    if (mIntercepted) {
                        // Already dispatched ACTION_DOWN event to parents, so stop here.
                        return false;
                    }

                    // Apps can set the interception target other than the direct parent.
                    final ViewGroup parent;
                    if (mTouchInterceptionViewGroup == null) {
                        parent = (ViewGroup) getParent();
                    } else {
                        parent = mTouchInterceptionViewGroup;
                    }

                    // Get offset to parents. If the parent is not the direct parent,
                    // we should aggregate offsets from all of the parents.
                    float offsetX = 0;
                    float offsetY = 0;
                    for (View v = this; v != null && v != parent; v = (View) v.getParent()) {
                        offsetX += v.getLeft() - v.getScrollX();
                        offsetY += v.getTop() - v.getScrollY();
                    }
                    final MotionEvent event = MotionEvent.obtainNoHistory(ev);
                    event.offsetLocation(offsetX, offsetY);

                    if (parent.onInterceptTouchEvent(event)) {
                        mIntercepted = true;

                        // If the parent wants to intercept ACTION_MOVE events,
                        // we pass ACTION_DOWN event to the parent
                        // as if these touch events just have began now.
                        event.setAction(MotionEvent.ACTION_DOWN);

                        // Return this onTouchEvent() first and set ACTION_DOWN event for parent
                        // to the queue, to keep events sequence.
                        post(new Runnable() {
                            @Override
                            public void run() {
                                parent.dispatchTouchEvent(event);
                            }
                        });
                        return false;
                    }

                    //下拉放大
                    if (!mScaling) {
                        if (getScrollY() == 0) {
                            y = ev.getY();//滑动到顶部时，记录位置
                        } else {
                            break;
                        }
                    }
                    int distance = (int) ((ev.getY() - y)*mScaleRatio);
                    if (distance < 0) break;//若往下滑动
                    mScaling = true;
                    setZoom(distance);

                    // Even when this can't be scrolled anymore,
                    // simply returning false here may cause subView's click,
                    // so delegate it to super.
                    return true;
                }else {
                    if (mScaling) {
                        int distances = (int) ((ev.getY() - y) * mScaleRatio);
                        if (distances == 0) {
                            setZoom(distances);
                            mScaling = false;
                            return true;
                        } else if (distances > 0) {
                            setZoom(distances);
                            return true;
                        }
                    } else {
                        return super.onTouchEvent(ev);
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void setScrollViewCallbacks(ObservableScrollViewCallbacks listener) {
        mCallbacks = listener;
    }

    @Override
    public void addScrollViewCallbacks(ObservableScrollViewCallbacks listener) {
        if (mCallbackCollection == null) {
            mCallbackCollection = new ArrayList<>();
        }
        mCallbackCollection.add(listener);
    }

    @Override
    public void removeScrollViewCallbacks(ObservableScrollViewCallbacks listener) {
        if (mCallbackCollection != null) {
            mCallbackCollection.remove(listener);
        }
    }

    @Override
    public void clearScrollViewCallbacks() {
        if (mCallbackCollection != null) {
            mCallbackCollection.clear();
        }
    }

    @Override
    public void setTouchInterceptionViewGroup(ViewGroup viewGroup) {
        mTouchInterceptionViewGroup = viewGroup;
    }

    @Override
    public void scrollVerticallyTo(int y) {
        scrollTo(0, y);
    }

    @Override
    public int getCurrentScrollY() {
        return mScrollY;
    }

    private void dispatchOnDownMotionEvent() {
        if (mCallbacks != null) {
            mCallbacks.onDownMotionEvent();
        }
        if (mCallbackCollection != null) {
            for (int i = 0; i < mCallbackCollection.size(); i++) {
                ObservableScrollViewCallbacks callbacks = mCallbackCollection.get(i);
                callbacks.onDownMotionEvent();
            }
        }
    }

    private void dispatchOnScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        if (mCallbacks != null) {
            mCallbacks.onScrollChanged(scrollY, firstScroll, dragging);
        }
        if (mCallbackCollection != null) {
            for (int i = 0; i < mCallbackCollection.size(); i++) {
                ObservableScrollViewCallbacks callbacks = mCallbackCollection.get(i);
                callbacks.onScrollChanged(scrollY, firstScroll, dragging);
            }
        }
    }

    private void dispatchOnUpOrCancelMotionEvent(ScrollState scrollState) {
        if (mCallbacks != null) {
            mCallbacks.onUpOrCancelMotionEvent(scrollState);
        }
        if (mCallbackCollection != null) {
            for (int i = 0; i < mCallbackCollection.size(); i++) {
                ObservableScrollViewCallbacks callbacks = mCallbackCollection.get(i);
                callbacks.onUpOrCancelMotionEvent(scrollState);
            }
        }
    }

    private boolean hasNoCallbacks() {
        return mCallbacks == null && mCallbackCollection == null;
    }

    static class SavedState extends BaseSavedState {
        int prevScrollY;
        int scrollY;

        /**
         * Called by onSaveInstanceState.
         */
        SavedState(Parcelable superState) {
            super(superState);
        }

        /**
         * Called by CREATOR.
         */
        private SavedState(Parcel in) {
            super(in);
            prevScrollY = in.readInt();
            scrollY = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(prevScrollY);
            out.writeInt(scrollY);
        }

        public static final Creator<SavedState> CREATOR
            = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 不可过度滚动，否则上移后下拉会出现部分空白的情况
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    //设置放大view
    public void setZoomView(View zoomView) {
        this.zoomView = zoomView;
    }

    // 滑动放大系数，系数越大，滑动时放大程度越大
    public void setmScaleRatio(float mScaleRatio) {
        this.mScaleRatio = mScaleRatio;
    }

    // 最大的放大倍数
    public void setmScaleTimes(int mScaleTimes) {
        this.mScaleTimes = mScaleTimes;
    }

    // 回弹时间系数，系数越小，回弹越快
    public void setmReplyRatio(float mReplyRatio) {
        this.mReplyRatio = mReplyRatio;
    }

    /**放大view*/
    private void setZoom(float s) {
        float scaleTimes = (float) ((zoomViewWidth+s)/(zoomViewWidth*1.0));
        //如超过最大放大倍数，直接返回
        if (scaleTimes > mScaleTimes) return;

        ViewGroup.LayoutParams layoutParams = zoomView.getLayoutParams();
        layoutParams.width = (int) (zoomViewWidth + s);
        layoutParams.height = (int) (zoomViewHeight * ((zoomViewWidth + s) / zoomViewWidth));
        //置控件水平居中
        ((MarginLayoutParams) layoutParams).setMargins(-(layoutParams.width - zoomViewWidth) / 2, 0, -(layoutParams.width - zoomViewWidth) / 2, 0);
        zoomView.setLayoutParams(layoutParams);
    }

    /**回弹*/
    private void replyView() {
        final float distance = zoomView.getMeasuredWidth() - zoomViewWidth;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // 设置动画
            ValueAnimator anim = ObjectAnimator.ofFloat(distance, 0.0F).setDuration((long) (distance * mReplyRatio));
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        setZoom((Float) animation.getAnimatedValue());
                    }
                }
            });
            anim.start();
        }

    }
}
