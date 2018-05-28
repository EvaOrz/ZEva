package cn.com.zwwl.bayuwen.base;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.zwwl.bayuwen.MyApplication;

/**
*  fragment基类
* Create by zhumangmang at 2018/5/26 10:13
*/

public abstract class BasicFragment extends Fragment {
    protected Activity activity;
    protected Resources res;
    protected Unbinder unbinder;
    protected MyApplication application;
    protected HashMap<String, String> paraMap = new HashMap<>();


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
        activity = getActivity();
        application = (MyApplication) getActivity().getApplication();
        res = getResources();
        initView();
        initData();
        setListener();
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();

    }
    protected abstract View setContentView(LayoutInflater inflater,
                                           ViewGroup container, Bundle savedInstanceState);

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void setListener();

    public void onClick(View view) {
    }


}
