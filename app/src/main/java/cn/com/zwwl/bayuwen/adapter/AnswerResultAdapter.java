package cn.com.zwwl.bayuwen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import cn.com.zwwl.bayuwen.fragment.FgAnswerResult;

public class AnswerResultAdapter  extends FragmentStatePagerAdapter {
    private String[]answer;
    public AnswerResultAdapter(FragmentManager fm, String[]data) {
        super(fm);
        this.answer=data;
    }

    @Override
    public Fragment getItem(int position) {
        return FgAnswerResult.newInstance(answer[position]);
    }

    @Override
    public int getCount() {
        return answer==null?0:answer.length;
    }
}

