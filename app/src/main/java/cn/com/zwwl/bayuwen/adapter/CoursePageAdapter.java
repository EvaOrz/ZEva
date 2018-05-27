package cn.com.zwwl.bayuwen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;


/**
 * @author lousx
 */
public class CoursePageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> listFragment;
    private List<String> list;

    public CoursePageAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> list) {
        super(fm);
        this.listFragment = fragmentList;
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        Object obj = super.instantiateItem(container, position);
        return obj;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;// 重载 getItemPosition() 并返回
        // POSITION_NONE，以触发销毁对象以及重建对象
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

}
