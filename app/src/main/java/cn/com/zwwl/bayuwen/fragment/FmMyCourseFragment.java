package cn.com.zwwl.bayuwen.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;


import com.umeng.commonsdk.debug.I;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.FmHositoryAdapter;
import cn.com.zwwl.bayuwen.adapter.FmMyCourseAdapter;
import cn.com.zwwl.bayuwen.api.FmCourseListApi;
import cn.com.zwwl.bayuwen.api.FmListApi;
import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.FmListhiistoryModel;
import cn.com.zwwl.bayuwen.model.FmMyCourceListModel;
import cn.com.zwwl.bayuwen.util.ToastUtil;
import cn.com.zwwl.bayuwen.util.Tools;

public class FmMyCourseFragment extends Fragment {

    ListView listView1;
    private View view;
    private List<FmMyCourceListModel> fmMyCourceListModels;
    private FmMyCourseAdapter fmMyCourseAdapter;
    private ImageView no_fm;


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
        String url = UrlUtil.myFmCourse();
        new FmCourseListApi(getActivity(), url, new ResponseCallBack<List<FmMyCourceListModel>>() {
            @Override
            public void result(List<FmMyCourceListModel> messageModel, ErrorMsg errorMsg) {

                if (messageModel != null && messageModel.size() > 0) {
                    fmMyCourceListModels.clear();
                    no_fm.setVisibility(View.GONE);

                    for (FmMyCourceListModel aa : messageModel) {

                        fmMyCourceListModels.add(aa);

                    }
                    if (Tools.listNotNull(fmMyCourceListModels)) {

                        fmMyCourseAdapter.setData(fmMyCourceListModels);
                        listView1.setAdapter(fmMyCourseAdapter);
                        fmMyCourseAdapter.notifyDataSetChanged();
                    }
                } else {
//                    ToastUtil.showShortToast("暂无数据");
                    no_fm.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initView(View view) {
        fmMyCourceListModels = new ArrayList<>();
        listView1 = view.findViewById(R.id.listView_cource);
        no_fm = view.findViewById(R.id.fm_no);
        fmMyCourseAdapter = new FmMyCourseAdapter(getActivity());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
