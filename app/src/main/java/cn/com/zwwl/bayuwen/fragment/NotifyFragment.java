package cn.com.zwwl.bayuwen.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.SysMessageActivity;

//
public class NotifyFragment extends Fragment implements View.OnClickListener{

    private View root;
    private LinearLayout system_message_id,interact_message_id,course_message_id,other_message_id;
    private ListView all_message_list;
    private LinearLayout all_message_lin;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         root= inflater.inflate(R.layout.fragment_notify, container, false);
         initView(root);
         return  root;
    }

    private void initView(View view) {
        system_message_id=view.findViewById(R.id.system_notific_id);
        interact_message_id=view.findViewById(R.id.interact_message_id);
        course_message_id=view.findViewById(R.id.course_message_id);
        other_message_id=view.findViewById(R.id.other_message_id);
        all_message_list=view.findViewById(R.id.all_message_list);
        all_message_lin=view.findViewById(R.id.all_message_lin);

        system_message_id.setOnClickListener(this);
        interact_message_id.setOnClickListener(this);
        course_message_id.setOnClickListener(this);
        other_message_id.setOnClickListener(this);



    }

//
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.system_notific_id:
                Intent intent=new Intent(getActivity(), SysMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.interact_message_id:
                break;
            case R.id.course_message_id:
                break;
            case R.id.other_message_id:
                break;

        }
    }
}
