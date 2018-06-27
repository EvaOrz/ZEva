package cn.com.zwwl.bayuwen.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.FmHositoryAdapter;
import cn.com.zwwl.bayuwen.api.FmListApi;
import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.FmListhiistoryModel;
import cn.com.zwwl.bayuwen.util.ToastUtil;
import cn.com.zwwl.bayuwen.util.Tools;

public class FmMyCourseFragment extends Fragment {

    ListView listView1;
    private View view;
    private List<FmListhiistoryModel> fmListhiistoryModels;
    private FmHositoryAdapter fmHositoryAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fm_my_course, container, false);
         initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HttpData();
    }

    private void HttpData() {
       String url= UrlUtil.myFmCourse();
        new FmListApi(getActivity(),url, new ResponseCallBack<List<FmListhiistoryModel>>() {
            @Override
            public void result(List<FmListhiistoryModel> messageModel, ErrorMsg errorMsg) {

                if (messageModel != null && messageModel.size() > 0) {
                    fmListhiistoryModels.clear();
                    for (FmListhiistoryModel aa : messageModel) {

                        fmListhiistoryModels.add(aa);

                    }
                    if (Tools.listNotNull(fmListhiistoryModels)) {

                        fmHositoryAdapter.setData(fmListhiistoryModels);
                        listView1.setAdapter(fmHositoryAdapter);
                        fmHositoryAdapter.notifyDataSetChanged();
                    }
                }else {
//                    ToastUtil.showShortToast("暂无数据");
                }
            }
        });
    }
    private void initView(View view) {
        fmListhiistoryModels=new ArrayList<>();
        listView1=view.findViewById(R.id.listView_cource);
        fmHositoryAdapter=new FmHositoryAdapter(getActivity());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
