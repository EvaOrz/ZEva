package cn.com.zwwl.bayuwen.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.TuanmaAdapter;
import cn.com.zwwl.bayuwen.model.OrderModel;
import cn.com.zwwl.bayuwen.model.TuanDianModel;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.NoScrollListView;

/**
 * 显示垫付之后的兑换码的dialog
 */
public class DuihuanCodeListDialog {
    private Context mContext;
    private Dialog mDialog;
    private Window window;
    private ListView listView;

    private List<TuanDianModel> tuanDianModelList;
    private TuanmaAdapter adapter;

    public DuihuanCodeListDialog(Context context, List<TuanDianModel> tuanDianModelList) {
        this.mContext = context;
        this.tuanDianModelList = tuanDianModelList;
        init();
    }

    private void init() {
        mDialog = new Dialog(mContext, R.style.CustomDialog);
        mDialog.show();
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(false);// 屏蔽dialog外焦点，弹出键盘
        window = mDialog.getWindow();
        window.setContentView(R.layout.dialog_duihuan_codes);

        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                .LayoutParams.MATCH_PARENT);

        listView = window.findViewById(R.id.duihuan_list);
        adapter = new TuanmaAdapter(mContext, 1);
        listView.setAdapter(adapter);
        adapter.setData(tuanDianModelList);
        window.findViewById(R.id.duihuan_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancel();
            }
        });

    }


}
