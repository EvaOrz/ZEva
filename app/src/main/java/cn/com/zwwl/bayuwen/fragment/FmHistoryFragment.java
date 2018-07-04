package cn.com.zwwl.bayuwen.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.fm.AlbumDetailActivity;
import cn.com.zwwl.bayuwen.adapter.FmHositoryAdapter;
import cn.com.zwwl.bayuwen.api.FmListApi;
import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.FmListhiistoryModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.ToastUtil;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.NoScrollListView;


public class FmHistoryFragment extends Fragment {
    private View view;
    private ImageView fm_no;
    private NoScrollListView history_listviewtoday,history_listviewpre;
    private TextView today, yesterday;
    private FmHositoryAdapter adapter1, adapter2;
    private List<FmListhiistoryModel> fmListhiistoryModels = new ArrayList<>(), fmListhiistoryModels2 = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_fm_history, container, false);
        initView(view);
        HttpData();
        return view;
    }

    private void HttpData() {
        String url= UrlUtil.getHistoryurl();
        new FmListApi(getActivity(), url,new ResponseCallBack<List<FmListhiistoryModel>>() {
            @Override
            public void result(List<FmListhiistoryModel> messageModel, ErrorMsg errorMsg) {


                if (messageModel != null && messageModel.size() > 0) {
                    fmListhiistoryModels.clear();
                    fmListhiistoryModels2.clear();
                    for (FmListhiistoryModel aa : messageModel) {
                        if (System.currentTimeMillis() - CalendarTools.fromStringTotime(aa.getCreated_at()) > 1000 * 60 * 60 * 24) {
                            fmListhiistoryModels2.add(aa);
                        } else {
                            fmListhiistoryModels.add(aa);
                        }
                    }
                    if (Tools.listNotNull(fmListhiistoryModels)) {
                        today.setVisibility(View.VISIBLE);
                        history_listviewtoday.setVisibility(View.VISIBLE);
                        today.setVisibility(View.VISIBLE);
                        adapter1.setData(fmListhiistoryModels);
                        history_listviewtoday.setAdapter(adapter1);
                        adapter1.notifyDataSetChanged();
                    } else {
                        today.setVisibility(View.GONE);
                        history_listviewtoday.setVisibility(View.GONE);
                    }
                    if (Tools.listNotNull(fmListhiistoryModels2)) {
                        yesterday.setVisibility(View.VISIBLE);
                        history_listviewpre.setVisibility(View.VISIBLE);
                        adapter2.setData(fmListhiistoryModels2);
                        history_listviewpre.setAdapter(adapter2);
                        adapter2.notifyDataSetChanged();
                    } else {
                        yesterday.setVisibility(View.GONE);
                        history_listviewpre.setVisibility(View.GONE);

                    }

                } else {
//                    ToastUtil.showShortToast("暂无数据");
                    fm_no.setVisibility(View.VISIBLE);
                }
            }

        });
    }


    private void initView(View view) {
        history_listviewtoday =view.findViewById(R.id.history_listview1);
        history_listviewpre=view.findViewById(R.id.history_listview2);
        today =view.findViewById(R.id.history__1);
        yesterday =view.findViewById(R.id.history__2);
        fm_no=view.findViewById(R.id.fm_no);
        adapter1 = new FmHositoryAdapter(getActivity());
        adapter2 = new FmHositoryAdapter(getActivity());

        history_listviewtoday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sId=fmListhiistoryModels.get(position).getKid();
                Intent intent =new Intent(getActivity(), AlbumDetailActivity.class);
                intent.putExtra("AlbumDetailActivity_data",sId);
                startActivity(intent);
            }
        });

        history_listviewpre.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sId=fmListhiistoryModels2.get(position).getKid();
                Intent intent =new Intent(getActivity(), AlbumDetailActivity.class);
                intent.putExtra("AlbumDetailActivity_data",sId);
                startActivity(intent);
            }
        });
    }


}
