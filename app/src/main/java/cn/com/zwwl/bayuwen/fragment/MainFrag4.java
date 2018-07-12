package cn.com.zwwl.bayuwen.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.CityActivity;
import cn.com.zwwl.bayuwen.activity.MainActivity;
import cn.com.zwwl.bayuwen.activity.MessageActivity;
import cn.com.zwwl.bayuwen.activity.SearchCourseActivity;
import cn.com.zwwl.bayuwen.activity.fm.AlbumDetailActivity;
import cn.com.zwwl.bayuwen.activity.fm.AlbumListActivity;
import cn.com.zwwl.bayuwen.activity.fm.FmSearchActivity;
import cn.com.zwwl.bayuwen.adapter.AlbumAdapter;
import cn.com.zwwl.bayuwen.adapter.MyViewPagerAdapter;
import cn.com.zwwl.bayuwen.api.fm.RecommentApi;
import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.CommentModel;
import cn.com.zwwl.bayuwen.model.CommonModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.GiftAndJiangModel;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.model.fm.RecommentModel;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.CircleImageView;
import cn.com.zwwl.bayuwen.widget.MostGridView;
import cn.com.zwwl.bayuwen.widget.RoundAngleImageView;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 *
 */
public class MainFrag4 extends Fragment implements View.OnClickListener {
    private View root;
    private ViewPager bannerView;
    private LinearLayout mLinearPosition;
    private RelativeLayout mToolbarView;
    private MostGridView mostGridView;
    private MostGridView hotsGridView;
    private CainiAdapter cainiAdapter;
    private TextView locationTv;

    //data
    private List<AlbumModel> albumDatas = new ArrayList<>();
    private List<RecommentModel> bannerData = new ArrayList<>();
    private List<CommonModel> tagDatas = new ArrayList<>();

    private Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.view_fm, container, false);
        initView();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        handler.sendEmptyMessage(0);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        handler.sendEmptyMessage(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.sendEmptyMessage(0);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    private void initData() {
        new RecommentApi(mActivity, new RecommentApi.FetchRecommentListListener() {
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
                    handler.sendEmptyMessage(1);
                    handler.sendEmptyMessage(2);
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:// 显示当前城市
                    locationTv.setText(TempDataHelper.getCurrentCity(mActivity));
                    break;
                case 1:
                    cainiAdapter.notifyDataSetChanged();
                    break;
                case 2:// 初始化bannerview
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                            (MyApplication.width - 80, 340);
                    params.setMargins(40, 0, 40, 0);
                    bannerView.setLayoutParams(params);

                    mLinearPosition.removeAllViews();
                    List<View> views = new ArrayList<>();
                    for (RecommentModel recommentModel : bannerData) {
                        RoundAngleImageView r = new RoundAngleImageView(mActivity);
                        r.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        ImageLoader.display(mActivity, r, recommentModel.getPic());
                        views.add(r);

                        ImageView img = new ImageView(getContext());
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams
                                (RelativeLayout.LayoutParams.WRAP_CONTENT,
                                        RelativeLayout.LayoutParams
                                                .WRAP_CONTENT);
                        layoutParams.rightMargin = getResources().getDimensionPixelOffset(R.dimen
                                .dimen_9dp);
                        img.setLayoutParams(layoutParams);
                        img.setBackgroundResource(R.drawable.viewlooper_gray_status);
                        mLinearPosition.addView(img);
                    }
                    MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(views);
                    bannerView.setAdapter(viewPagerAdapter);
                    bannerView.setCurrentItem(0);
                    updateLinearPosition(0);
                    break;
            }
        }
    };

    private void initView() {
        bannerView = root.findViewById(R.id.frag4_viewpager);
        bannerView.setOffscreenPageLimit(2);
        bannerView.setPageMargin(30);
        mLinearPosition = root.findViewById(R.id.frag4_viewpager_indicator);
        mToolbarView = root.findViewById(R.id.main2_toolbar);
        mostGridView = root.findViewById(R.id.frag4_tags);
        hotsGridView = root.findViewById(R.id.frag4_hots);
        locationTv = root.findViewById(R.id.position);

        tagDatas.add(new CommonModel("了就读书", "1000", R.mipmap.home_fm_1));
        tagDatas.add(new CommonModel("干货|讲座", "1001", R.mipmap.home_fm_2));
        tagDatas.add(new CommonModel("统编教材", "1002", R.mipmap.home_fm_3));
        tagDatas.add(new CommonModel("每日窦神", "1003", R.mipmap.home_fm_4));
        tagDatas.add(new CommonModel("名师精粹", "1004", R.mipmap.home_fm_5));
        tagDatas.add(new CommonModel("精品课程", "1005", R.mipmap.home_fm_6));
        tagDatas.add(new CommonModel("有声读物", "1006", R.mipmap.home_fm_7));
        Frag4Adapter adapter = new Frag4Adapter(mActivity, tagDatas);
        mostGridView.setAdapter(adapter);
        mostGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goAlbumList(tagDatas.get(position).getName(), tagDatas.get(position).getId());
            }
        });
        cainiAdapter = new CainiAdapter(mActivity, albumDatas);
        hotsGridView.setAdapter(cainiAdapter);
        hotsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goAlbumDetailActivity(albumDatas.get(position).getKid());
            }
        });

        mToolbarView.findViewById(R.id.menu_more).setOnClickListener(this);
        mToolbarView.findViewById(R.id.menu_news).setOnClickListener(this);
        locationTv.setOnClickListener(this);
        mToolbarView.findViewById(R.id.menu_search).setOnClickListener(this);
        root.findViewById(R.id.fm_album_list).setOnClickListener(this);

        bannerView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateLinearPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 去往课程详情页面
     */
    private void goAlbumDetailActivity(String kid) {
        Intent intent = new Intent(mActivity, AlbumDetailActivity.class);
        ((MainActivity) mActivity).isGoAlbumActivity = true;
        intent.putExtra("AlbumDetailActivity_data", kid);
        intent.putExtra("is_from_main", true);
        startActivity(intent);
    }

    private void updateLinearPosition(int position) {
        for (int i = 0; i < mLinearPosition.getChildCount(); i++) {
            if (i == position) {
                mLinearPosition.getChildAt(i).setBackgroundResource(R.drawable
                        .viewlooper_gold_status);
            } else {
                mLinearPosition.getChildAt(i).setBackgroundResource(R.drawable
                        .viewlooper_gray_status);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fm_album_list:
                startActivity(new Intent(mActivity, AlbumListActivity.class));
                break;
            case R.id.menu_news:
                startActivity(new Intent(mActivity, MessageActivity.class));
                break;
            case R.id.menu_more:
                ((MainActivity) mActivity).openDrawer();
                break;
            case R.id.position:
                Intent intent = new Intent(getActivity(), CityActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_search:
                startActivity(new Intent(mActivity, FmSearchActivity.class));
                break;


        }
    }

    private void goAlbumList(String title, String aid) {
        Intent i = new Intent(mActivity, AlbumListActivity.class);
        i.putExtra("AlbumListActivity_title", title);
        i.putExtra("AlbumListActivity_data", aid);
        startActivity(i);
    }

    /**
     * 带传参的构造方法
     *
     * @param ss
     * @return
     */
    public static MainFrag4 newInstance(String ss) {
//        Bundle args = new Bundle();
        MainFrag4 fragment = new MainFrag4();
//        fragment.setArguments(args);
        return fragment;
    }

    /**
     * tag
     */
    public class Frag4Adapter extends BaseAdapter {

        private Context mContext;
        private List<CommonModel> commonModels;

        public Frag4Adapter(Context context, List<CommonModel> commonModels) {
            mContext = context;
            this.commonModels = commonModels;
        }

        public int getCount() {
            return commonModels.size();
        }


        public Object getItem(int position) {
            return commonModels.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout
                    .item_frag2_tag);
            CircleImageView imageView = viewHolder.getView(R.id.cdetail_t_avatar);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(130, 130));
            TextView textView = viewHolder.getView(R.id.cdetail_t_name);

            imageView.setImageResource(commonModels.get(position).getState());
            textView.setText(commonModels.get(position).getName());
            return viewHolder.getConvertView();
        }

    }

    /**
     * album
     */
    public class CainiAdapter extends BaseAdapter {

        private Context mContext;
        private List<AlbumModel> albumModels;

        public CainiAdapter(Context context, List<AlbumModel> albumModels) {
            mContext = context;
            this.albumModels = albumModels;
        }

        public int getCount() {
            return albumModels.size();
        }


        public Object getItem(int position) {
            return albumModels.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AlbumModel albumModel = albumModels.get(position);
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout
                    .item_frag4_album);
            RoundAngleImageView imageView = viewHolder.getView(R.id.frag4_img);
            TextView textView = viewHolder.getView(R.id.frag4_title);

            ImageLoader.display(mContext, imageView, albumModel.getPic());
            textView.setText(albumModel.getTitle());
            return viewHolder.getConvertView();
        }

    }
}
