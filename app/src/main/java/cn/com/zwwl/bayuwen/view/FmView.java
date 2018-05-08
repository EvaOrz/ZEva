package cn.com.zwwl.bayuwen.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.fm.AlbumDetailActivity;
import cn.com.zwwl.bayuwen.activity.fm.AlbumListActivity;
import cn.com.zwwl.bayuwen.activity.fm.FmMainActivity;
import cn.com.zwwl.bayuwen.activity.fm.FmSearchActivity;
import cn.com.zwwl.bayuwen.adapter.AlbumAdapter;
import cn.com.zwwl.bayuwen.api.fm.RecommentApi;
import cn.com.zwwl.bayuwen.model.AlbumModel;
import cn.com.zwwl.bayuwen.model.RecommentModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.Tools;

public class FmView implements View.OnClickListener {
    private Context context;
    private View view;
    private BannerView bannerView;

    private TextView bt1, bt2, bt3, bt4, bt5, bt6, bt7;
    private NoScrollListView hotListView;// 热门推荐listview
    private AlbumAdapter albumAdapter;


    //data
    private List<AlbumModel> albumDatas = new ArrayList<>();
    private List<RecommentModel> bannerData = new ArrayList<>();
    private List<View> bannerViews = new ArrayList<>();

    public FmView(Context context) {
        this.context = context;
        initView();
        initData();
    }

    private void initData() {
        new RecommentApi(context, new RecommentApi.FetchRecommentListListener() {
            @Override
            public void setData(List<RecommentModel> list) {
                if (Tools.listNotNull(list)) {
                    albumDatas.clear();
                    bannerData.clear();
                    for (RecommentModel r : list) {
                        if (r.getParent().equals("19")) {
                            bannerData.add(r);
                        } else if (r.getParent().equals("20")) {
                            albumDatas.add(r.getAlbumModel());
                        }
                    }
                    handler.sendEmptyMessage(2);
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }

    private ImageView getBannerItem(final RecommentModel recommentModel) {
        ImageView view = new ImageView(context);
        view.setBackgroundColor(context.getResources().getColor(R.color.gray_line));
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(MyApplication.width, MyApplication.width * 9 / 32);
        view.setLayoutParams(params);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(recommentModel.getPic()).into(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (recommentModel.getType().equals("1") && recommentModel.getAlbumModel() != null) {
                    goAlbumDetailActivity(recommentModel.getAlbumModel().getKid());
                }
            }
        });
        return view;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    ((FmMainActivity) context).unshowPlayController();
                    break;

                case 1:
                    ((FmMainActivity) context).showPlayController();
                    break;

                case 2:// 初始化页面
                    albumAdapter.setData(albumDatas);
                    albumAdapter.notifyDataSetChanged();
                    bannerViews.clear();
                    for (RecommentModel r : bannerData)
                        bannerViews.add(getBannerItem(r));
                    bannerView.startLoop(true);

                    bannerView.setViewList(bannerViews);
                    break;
            }
        }
    };

    private void initView() {
        view = LayoutInflater.from(context).inflate(R.layout.view_fm, null);
        bannerView = view.findViewById(R.id.fm_head);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MyApplication.width, MyApplication.width * 9 / 32);
        bannerView.setLayoutParams(params);

        ((CallScrollView) view.findViewById(R.id.main_scroll)).setOnScrollListener(new CallScrollView.OnScrollListener() {
            @Override
            public void onScroll(boolean isUp) {
                if (isUp) {
                    handler.sendEmptyMessage(0);
                } else {
                    handler.sendEmptyMessage(1);
                }
            }

        });
        bt1 = view.findViewById(R.id.fm_bt_1);
        bt2 = view.findViewById(R.id.fm_bt_2);
        bt3 = view.findViewById(R.id.fm_bt_3);
        bt4 = view.findViewById(R.id.fm_bt_4);
        bt5 = view.findViewById(R.id.fm_bt_5);
        bt6 = view.findViewById(R.id.fm_bt_6);
        bt7 = view.findViewById(R.id.fm_bt_7);
//        bt1.setCompoundDrawables(null, getDrawable(R.mipmap.home_1), null, null);
//        bt2.setCompoundDrawables(null, getDrawable(R.mipmap.home_2), null, null);
//        bt3.setCompoundDrawables(null, getDrawable(R.mipmap.home_3), null, null);
//        bt4.setCompoundDrawables(null, getDrawable(R.mipmap.home_back), null, null);
//        bt5.setCompoundDrawables(null, getDrawable(R.mipmap.home_story), null, null);
//        bt6.setCompoundDrawables(null, getDrawable(R.mipmap.home_zhuaxue), null, null);
//        bt7.setCompoundDrawables(null, getDrawable(R.mipmap.home_child), null, null);
        hotListView = view.findViewById(R.id.fm_hot_layout);

        albumAdapter = new AlbumAdapter(context);
        hotListView.setAdapter(albumAdapter);
        hotListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goAlbumDetailActivity(albumDatas.get(position).getKid());
            }
        });
        // set onClick
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
        bt7.setOnClickListener(this);
        view.findViewById(R.id.fm_search).setOnClickListener(this);
        view.findViewById(R.id.fm_album_list).setOnClickListener(this);
    }

    /**
     * 去往课程详情页面
     */
    private void goAlbumDetailActivity(String kid) {
        Intent intent = new Intent(context, AlbumDetailActivity.class);
        intent.putExtra("AlbumDetailActivity_data", kid);
        context.startActivity(intent);
    }

    private Drawable getDrawable(int resId) {
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, 200, 200);
        return drawable;
    }

    public View getView() {
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fm_search:
                context.startActivity(new Intent(context, FmSearchActivity.class));
                break;
            case R.id.fm_album_list:
                context.startActivity(new Intent(context, AlbumListActivity.class));
                break;
            case R.id.fm_bt_1://了就读书
                goAlbumList("1000");
                break;
            case R.id.fm_bt_2://干货
                goAlbumList("1001");
                break;
            case R.id.fm_bt_3://统编教材
                goAlbumList("1002");
                break;
            case R.id.fm_bt_4://窦神归来
                goAlbumList("1003");
                break;
            case R.id.fm_bt_5://蒋故事
                goAlbumList("1004");
                break;
            case R.id.fm_bt_6://爪学
                goAlbumList("1005");
                break;
            case R.id.fm_bt_7://低幼儿童
                goAlbumList("1006");
                break;


        }
    }

    private void goAlbumList(String aid) {
        Intent i = new Intent(context, AlbumListActivity.class);
        i.putExtra("AlbumListActivity_data", aid);
        context.startActivity(i);
    }
}
