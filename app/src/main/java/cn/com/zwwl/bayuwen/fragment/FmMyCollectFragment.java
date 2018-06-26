package cn.com.zwwl.bayuwen.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.FmHositoryAdapter;
import cn.com.zwwl.bayuwen.api.AddCommentApi;
import cn.com.zwwl.bayuwen.api.CancelCollectApi;
import cn.com.zwwl.bayuwen.api.FmListApi;
import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.dialog.AskDialog;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.FmListhiistoryModel;
import cn.com.zwwl.bayuwen.util.ToastUtil;
import cn.com.zwwl.bayuwen.util.Tools;

public class FmMyCollectFragment extends Fragment {

    ListView listView1;
    private View view;
    private List<FmListhiistoryModel> fmListhiistoryModels;
    private FmHositoryAdapter fmHositoryAdapter;
    private String ID;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fm_my_collect, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HttpData();
    }

    private void HttpData() {
        String url= UrlUtil.myFmCollection();
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

        fmHositoryAdapter=new FmHositoryAdapter(getActivity());
        listView1=view.findViewById(R.id.listView_collection);
        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ID=fmListhiistoryModels.get(position).getId();
                new AskDialog(getActivity(), "取消收藏", new AskDialog.OnSurePickListener() {
                    @Override
                    public void onSure() {
                        CancelCollect(ID);
                    }
                });


                return false;
            }
        });

    }

    private void CancelCollect(String id) {

        new CancelCollectApi(getActivity(),id,new ResponseCallBack<String>() {
            @Override
            public void result(String message, ErrorMsg errorMsg) {

                if (errorMsg != null) {

                    ToastUtil.showShortToast("取消收藏成功");
                    fmHositoryAdapter.notifyDataSetChanged();
                     HttpData();

                } else {
                    ToastUtil.showShortToast(errorMsg.getDesc());
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
