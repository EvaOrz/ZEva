package com.woxue.app.base;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.woxue.app.MyApplication;
import com.woxue.app.dialog.LoadingDialog;
import com.woxue.app.util.ThemeUtils;
import com.woxue.app.util.eventbus.Event;
import com.woxue.app.util.eventbus.EventBusUtil;
import com.woxue.app.util.okhttp.OkHttpManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Monty on 2017/5/24.
 * fragment基类
 */

public abstract class BaseFragment extends Fragment {
    protected Activity activity;
    protected Resources res;
    protected Unbinder unbinder;
    protected MyApplication application;
    protected HashMap<String, String> paraMap = new HashMap<>();
    protected LoadingDialog loadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        application = MyApplication.getInstance();
        res = getResources();
        loadingDialog = new LoadingDialog(activity);
        OkHttpManager.getInstance().setTag(this);
        if (isRegisterEventBus())
            EventBusUtil.register(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = setContentView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        setListener();
        ThemeUtils.isChangeThemeStyle(activity, false);
    }

    @Override
    public void onDestroy() {
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
        OkHttpManager.getInstance().cancelTag(this);
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
        paraMap = null;
        activity = null;
        application = null;
        res = null;
        loadingDialog = null;
        unbinder.unbind();
        super.onDestroy();

    }

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(Event event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
    }

    /**
     * 接收到分发到事件
     *
     * @param event 事件
     */
    protected void receiveEvent(Event event) {
    }

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    protected void receiveStickyEvent(Event event) {
    }

    protected abstract View setContentView(LayoutInflater inflater,
                                           ViewGroup container, Bundle savedInstanceState);

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void setListener();

    public void onClick(View view) {
    }


}
