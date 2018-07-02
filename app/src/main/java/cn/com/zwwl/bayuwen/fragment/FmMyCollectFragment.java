package cn.com.zwwl.bayuwen.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.FmHositoryAdapter;
import cn.com.zwwl.bayuwen.adapter.FmMyCollectionAdapter;
import cn.com.zwwl.bayuwen.api.AddCommentApi;
import cn.com.zwwl.bayuwen.api.CancelCollectApi;
import cn.com.zwwl.bayuwen.api.FmCollectListApi;
import cn.com.zwwl.bayuwen.api.FmListApi;
import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.dialog.AskDialog;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.FmListCollectModel;
import cn.com.zwwl.bayuwen.util.ToastUtil;

public class FmMyCollectFragment extends Fragment {

    ListView listView1;
    private View view;
    private List<FmListCollectModel.DataBean> dataBeans;
    private List<FmListCollectModel.LinksBean> linksBeans;
    private FmMyCollectionAdapter fmMyCollectionAdapter;
    private String ID;
    private ImageView no_fm;


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
        String url = UrlUtil.getCollecturl();
        new FmCollectListApi(getActivity(), url, new ResponseCallBack<FmListCollectModel>() {
            @Override
            public void result(FmListCollectModel messageModel, ErrorMsg errorMsg) {

                if (messageModel != null) {
                    dataBeans.clear();
                    no_fm.setVisibility(View.GONE);
                    dataBeans = messageModel.getData();
//                    for (FmListCollectModel.DataBean aa : dataBeans) {
//
//                        dataBeans.add(aa);
//
//                    }


                    fmMyCollectionAdapter.setData(dataBeans);
                    listView1.setAdapter(fmMyCollectionAdapter);
                    fmMyCollectionAdapter.notifyDataSetChanged();
//                    }
                } else {
//                ToastUtil.showShortToast("暂无数据");
                    no_fm.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initView(View view) {
        dataBeans = new ArrayList<>();
        no_fm = view.findViewById(R.id.fm_no);
        fmMyCollectionAdapter = new FmMyCollectionAdapter(getActivity());
        listView1 = view.findViewById(R.id.listView_collection);
        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ID = dataBeans.get(position).getId();
                new AskDialog(getActivity(), "取消收藏", new AskDialog.OnSurePickListener() {
                    @Override
                    public void onSure() {
                        CancelCollect(ID);
                    }

                    @Override
                    public void onCancle() {

                    }
                });


                return false;
            }
        });

    }

    private void CancelCollect(String id) {

        new CancelCollectApi(getActivity(), id, new ResponseCallBack<String>() {
            @Override
            public void result(String message, ErrorMsg errorMsg) {

                if (errorMsg == null) {

                    ToastUtil.showShortToast("取消收藏成功");
                    fmMyCollectionAdapter.notifyDataSetChanged();
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
