package cn.com.zwwl.bayuwen.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.com.zwwl.bayuwen.R;

/**
 *
 */
public class CDetailTabFrag1 extends Fragment {

    private Activity mActivity;

    public static CDetailTabFrag1 newInstance(String evaluate) {
        CDetailTabFrag1 fg = new CDetailTabFrag1();
        Bundle bundle = new Bundle();
        bundle.putString("evaluate", evaluate);
        fg.setArguments(bundle);
        return fg;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
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
