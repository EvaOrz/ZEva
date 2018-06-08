package cn.com.zwwl.bayuwen.activity;

import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.FinalEvalAdapter;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.base.MenuCode;
import cn.com.zwwl.bayuwen.widget.decoration.GridItemDecoration;

/**
 * 课程评价
 * Create by zhumangmang at 2018/5/26 16:22
 */
public class CourseEvalActivity extends BasicActivityWithTitle {
    @BindView(R.id.star_eval)
    AppCompatRatingBar starEval;
    @BindView(R.id.star_level)
    AppCompatTextView starLevel;
    @BindView(R.id.label)
    RecyclerView label;
    @BindView(R.id.advise)
    AppCompatEditText advise;
    @BindView(R.id.teacher)
    RecyclerView teacher;
    @BindView(R.id.adviser)
    RecyclerView adviser;
    @BindView(R.id.tutor)
    RecyclerView tutor;

    @Override
    protected int setContentView() {
        return R.layout.activity_course_eval;
    }

    @Override
    protected void initView() {
        showMenu(MenuCode.SUBMIT);
        setCustomTitle("课程评价");
        initRecycler(label, 2, Arrays.asList("上课需要更幽默", "备课需要更充分", "下课需要更提前"), 0);
        initRecycler(teacher, 3, Arrays.asList("张老师", "李老师", "王老师", "孙老师"), 1);
        initRecycler(tutor, 3, Arrays.asList("张老师", "李老师"), 1);
        initRecycler(adviser, 3, Arrays.asList("张老师"), 1);
    }

    private void initRecycler(RecyclerView recyclerView, int spanCount, List<String> data, int type) {
        recyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new GridItemDecoration(this));
        FinalEvalAdapter adapter;
        if (type == 0)
            adapter = new FinalEvalAdapter(R.layout.item_final_label, data);
        else
            adapter = new FinalEvalAdapter(R.layout.item_final_vote, data);
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onMenuClick(int menuCode) {
//        if (TextUtils.isEmpty(Tools.getText(content))) {
//            ToastUtil.showShortToast("请输入评价内容");
//            return;
//        }
//        map.put("kid", kid);
//        map.put("content", Tools.getText(content));
//        map.put("type", "1");
//        map.put("is_anonymity", "0");
//        new EvalApi(this, map, new ResponseCallBack<CommonModel>() {
//            @Override
//            public void result(CommonModel commonModel, ErrorMsg errorMsg) {
//                if (errorMsg != null) {
//                    ToastUtil.showShortToast(errorMsg.getDesc());
//                } else {
//                    ToastUtil.showShortToast("提交成功,谢谢您的评价");
//                    finish();
//                }
//            }
//        });
    }

    @Override
    public void close() {
        finish();
    }


}
