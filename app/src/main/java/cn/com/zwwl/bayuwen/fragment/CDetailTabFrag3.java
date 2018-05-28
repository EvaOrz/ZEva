package cn.com.zwwl.bayuwen.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.zwwl.bayuwen.R;

public class CDetailTabFrag3 extends Fragment {

    public static CDetailTabFrag3 newInstance(String evaluate) {
        CDetailTabFrag3 fg = new CDetailTabFrag3();
        Bundle bundle = new Bundle();
        bundle.putString("evaluate", evaluate);
        fg.setArguments(bundle);
        return fg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_evaluate, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
    }

}
