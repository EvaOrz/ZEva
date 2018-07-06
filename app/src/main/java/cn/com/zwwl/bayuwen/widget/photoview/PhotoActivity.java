package cn.com.zwwl.bayuwen.widget.photoview;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.base.BasicActivity;

/**
 * 查看图片，可缩放，滑动切换
 * Created by zhumangmang at 2018/7/5 10:14
 */
public class PhotoActivity extends BasicActivity {

    @BindView(R.id.page)
    TextView page;
    @BindView(R.id.viewPager)
    PhotoViewPager viewPager;
   String[] imagesUrl;
    int current;
    PhotoAdapter adapter;

    @Override
    protected int setContentView() {
        return R.layout.activity_photo;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        if (getIntent() != null) {
            Intent intent = getIntent();
            imagesUrl = intent.getStringArrayExtra("images");
            current = intent.getIntExtra("position", 0);
        }
        adapter = new PhotoAdapter(imagesUrl, getApplicationContext());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(current);
        page.setText(String.format("%s/%s", current + 1, imagesUrl.length));
    }

    @Override
    protected void setListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                current = position;
                page.setText(String.format("%s/%s", current + 1, imagesUrl.length));
                int childCount = viewPager.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = viewPager.getChildAt(i);
                    try {
                        if (childAt != null && childAt instanceof PhotoView) {
                            PhotoView photoView = (PhotoView) childAt;
                            PhotoViewAttacher attacher = new PhotoViewAttacher(
                                    photoView);
                            attacher.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.back)
    @Override
    public void onClick(View view) {
        finish();
    }

}
