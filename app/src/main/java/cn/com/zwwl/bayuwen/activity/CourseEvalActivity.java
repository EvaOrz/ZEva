package cn.com.zwwl.bayuwen.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Switch;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.EvalApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.base.MenuCode;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.CommonModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.Tools;

/**
 * 课程评价
 * Create by zhumangmang at 2018/5/26 16:22
 */
public class CourseEvalActivity extends BasicActivityWithTitle {
    @BindView(R.id.content)
    AppCompatEditText content;
    @BindView(R.id.hide_name)
    Switch hideName;
    String kid;

    @Override
    protected int setContentView() {
        return R.layout.activity_course_eval;
    }

    @Override
    protected void initView() {
        showMenu(MenuCode.SUBMIT);
        setCustomTitle("课程评价");
    }

    @Override
    protected void initData() {
        kid = getIntent().getStringExtra("kid");
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onMenuClick(int menuCode) {
        if (TextUtils.isEmpty(Tools.getText(content))) {
            Snackbar.make(content, "请输入评价内容", Snackbar.LENGTH_LONG).show();
            return;
        }
        map.put("kid", kid);
        map.put("content", Tools.getText(content));
        map.put("type", "1");
        map.put("is_anonymity", "0");
        new EvalApi(this, map, new ResponseCallBack<CommonModel>() {
            @Override
            public void result(CommonModel commonModel, ErrorMsg errorMsg) {
                if (errorMsg!=null){
                    Snackbar.make(content, errorMsg.getDesc(), Snackbar.LENGTH_LONG).show();
                }else {
                    Snackbar.make(content, "success", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void close() {
        finish();
    }
}
