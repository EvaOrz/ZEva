package cn.com.zwwl.bayuwen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import cn.com.zwwl.bayuwen.fragment.FgLookPPT;

public class PPTAdapter extends FragmentStatePagerAdapter {
    private List<String>imgUrls;
    public PPTAdapter(FragmentManager fm,List<String>imgUrls) {
        super(fm);
        this.imgUrls=imgUrls;
    }

    @Override
    public Fragment getItem(int position) {
        return FgLookPPT.newInstance(imgUrls.get(position));
    }

    @Override
    public int getCount() {
        return imgUrls==null?0:imgUrls.size();
    }
}
