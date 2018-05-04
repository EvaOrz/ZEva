package cn.com.zwwl.bayuwen.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.db.DataHelper;
import cn.com.zwwl.bayuwen.model.UserModel;

/**
 * 修改姓名、电话等信息的dialog
 */
public class ChangeInfoDialog implements View.OnClickListener {
    private Context mContext;
    private Dialog mDialog;
    private Window window;
    private EditText edit;
    private TextView title;
    private int type;// 1：昵称；2：电话
    private UserModel userModel;
    private SignChangeListener signChangeListener;

    public interface SignChangeListener {
        public void setText(String text);
    }

    public ChangeInfoDialog(Context context, int type, SignChangeListener signChangeListener) {
        this.mContext = context;
        this.type = type;
        this.signChangeListener = signChangeListener;
        userModel = DataHelper.getUserLoginInfo(context);
        init();
    }

    private void init() {
        mDialog = new Dialog(mContext, R.style.CustomDialog);
        mDialog.show();
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(false);// 屏蔽dialog外焦点，弹出键盘
        window = mDialog.getWindow();
        window.setContentView(R.layout.dialog_motify_sign);

        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        edit = (EditText) window.findViewById(R.id.motify_sign_edit);
        title = (TextView) window.findViewById(R.id.motify_sign_title);
        if (type == 1) {
            title.setText(R.string.motify_nickname);
            edit.setText(userModel.getName());
        } else if (type == 2) {
            title.setText(R.string.motify_telephone);
            edit.setText(userModel.getTel());
        }
        window.findViewById(R.id.motify_sign_sure).setOnClickListener(this);
        window.findViewById(R.id.motify_sign_cancle).setOnClickListener(this);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                showKeyboard();
            }
        }, 200);
    }

    /**
     * 显示软键盘
     */
    private void showKeyboard() {
        if (edit != null) {
            // 设置可获得焦点
            edit.setFocusable(true);
            edit.setFocusableInTouchMode(true);
            // 请求获得焦点
            edit.requestFocus();
            // 调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) edit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(edit, 0);
        }
    }

    @Override
    public void onClick(View v) {
        String desc = edit.getEditableText().toString();
        if (v.getId() == R.id.motify_sign_sure) {
            if (!TextUtils.isEmpty(desc)) {
                if (type == 1) {//
                    if (desc.getBytes().length > 24)
                        Toast.makeText(mContext, R.string.nick_name_length_error, Toast.LENGTH_SHORT).show();
                    else {
                        signChangeListener.setText(desc);
                    }
                } else if (type == 2) {
                    signChangeListener.setText(desc);
                }
            }
        }
        mDialog.cancel();
    }


}
