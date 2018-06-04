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
import cn.com.zwwl.bayuwen.db.UserDataHelper;
import cn.com.zwwl.bayuwen.model.UserModel;

/**
 * 显示支付明细的dialog
 */
public class PayDetailDialog {
    private Context mContext;
    private Dialog mDialog;
    private Window window;

    private TextView price1, price2, price3, price4, price5, price6, price7;

    public PayDetailDialog(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        mDialog = new Dialog(mContext, R.style.CustomDialog);
        mDialog.show();
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(false);// 屏蔽dialog外焦点，弹出键盘
        window = mDialog.getWindow();
        window.setContentView(R.layout.dialog_pay_detail);

        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                .LayoutParams.MATCH_PARENT);

        price1 = window.findViewById(R.id.pay_d_price1);
        price2 = window.findViewById(R.id.pay_d_price2);
        price3 = window.findViewById(R.id.pay_d_price3);
        price4 = window.findViewById(R.id.pay_d_price4);
        price5 = window.findViewById(R.id.pay_d_price5);
        price6 = window.findViewById(R.id.pay_d_price6);
        price7 = window.findViewById(R.id.pay_d_price7);

        window.findViewById(R.id.pay_d_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancel();
            }
        });

    }


}
