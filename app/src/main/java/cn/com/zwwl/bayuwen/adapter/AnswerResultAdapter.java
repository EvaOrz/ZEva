package cn.com.zwwl.bayuwen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import cn.com.zwwl.bayuwen.fragment.FgAnswerResult;
import cn.com.zwwl.bayuwen.model.AnswerModel;

public class AnswerResultAdapter  extends FragmentStatePagerAdapter {
    private List<AnswerModel>answer;
    public AnswerResultAdapter(FragmentManager fm, List<AnswerModel>answer) {
        super(fm);
        this.answer=answer;
    }

    @Override
    public Fragment getItem(int position) {
        return FgAnswerResult.newInstance(position+1,answer.get(position));
    }

    @Override
    public int getCount() {
        return answer==null?0:answer.size();
    }
}

