package cn.com.zwwl.bayuwen.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.ChildInfoActivity;
import cn.com.zwwl.bayuwen.model.UserModel;

/**
 * 首页选择学生下拉菜单
 */
public class ChildMenuPopView extends PopupWindow {
    private View conentView;
    private OnChildPickListener onChildPickListener;
    private LinearLayout layout;

    public ChildMenuPopView(final Activity context, List<UserModel> list, OnChildPickListener onChildPickListener) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.pop_child_menu, null);
        layout = conentView.findViewById(R.id.pop_child_layout);
        for (int i = 0; i < 2; i++) {
            layout.addView(inflater.inflate(R.layout.item_menu_child, null));
        }
        conentView.findViewById(R.id.pop_child_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ChildInfoActivity.class));
                dismiss();
            }
        });
// 设置SelectPicPopupWindow的View
        this.setContentView(conentView);

// 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(MyApplication.width / 3);
// 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
// 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
// 刷新状态
        this.update();
// 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
// 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimationPreview);
    }

    public interface OnChildPickListener {
        public void onChildPick();
    }


    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
// 以下拉方式显示popupwindow
            this.showAsDropDown(parent, MyApplication.width / 2 - MyApplication.width / 6, 0, Gravity.CENTER_HORIZONTAL);
        } else {
            this.dismiss();
        }
    }
}