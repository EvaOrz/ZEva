package cn.com.zwwl.bayuwen.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.zwwl.bayuwen.R;

public class CDetailTabFrag2 extends Fragment {

    public static CDetailTabFrag2 newInstance(String evaluate) {
        CDetailTabFrag2 fg = new CDetailTabFrag2();
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
