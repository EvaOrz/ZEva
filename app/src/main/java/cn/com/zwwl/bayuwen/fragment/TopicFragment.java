package cn.com.zwwl.bayuwen.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import cn.com.zwwl.bayuwen.R;


public class TopicFragment extends Fragment {
 private View root;
 private AppCompatTextView menu_search;
 private ListView topic_list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_topic, container, false);
        initFragment(root);
        return root;
    }

    private void initFragment(View view) {
        menu_search=view.findViewById(R.id.menu_search);
        topic_list=view.findViewById(R.id.topic_list);
    }


}

