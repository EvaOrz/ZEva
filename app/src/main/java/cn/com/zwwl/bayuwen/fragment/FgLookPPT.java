package cn.com.zwwl.bayuwen.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.base.BasicFragment;
import cn.com.zwwl.bayuwen.glide.ImageLoader;

public class FgLookPPT extends BasicFragment {
    @BindView(R.id.ppt)
    AppCompatImageView ppt;

    public static Fragment newInstance(String url) {
        FgLookPPT fragment = new FgLookPPT();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_look_ppt, container, false);
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String url = bundle.getString("url");
            ImageLoader.displayBorderCircle(activity,ppt,url);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
}
