package cn.com.zwwl.bayuwen.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.zwwl.bayuwen.R;


/**
 * @author Monty
 *         created at 2017/6/7 16:37
 */

public class IActivityImpl implements IActivity, Toolbar.OnMenuItemClickListener {
    private BasicActivityWithTitle mContext;


    private Toolbar mToolbar;

    private View back;
    //自定义title
    private CharSequence mTitle;

    IActivityImpl(Activity context) {
        this.mContext = (BasicActivityWithTitle) context;
        if (mContext.setParentScrollable()) {
            mContext.getDelegate().setContentView(R.layout.activity_scroll_base);
        } else {
            mContext.getDelegate().setContentView(R.layout.activity_base);
        }
        mToolbar = mContext.findViewById(R.id.toolbar);
        back = mContext.findViewById(R.id.back);
        mContext.setSupportActionBar(mToolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.close();
            }
        });
        mToolbar.setOnMenuItemClickListener(this);
        setCustomTitle("");
    }

    @Override
    public void setCustomTitle(CharSequence title) {
        this.mTitle = title;
        TextView titleView = mContext.findViewById(R.id.title);
        titleView.setText(title);
        setDisplayShowCustomTitleEnabled(true);
    }

    @Override
    public void setCustomTitle(int title) {
        if (title != 0) {
            this.mTitle = mContext.getResources().getString(title);
        } else {
            this.mTitle = null;
        }

        setCustomTitle(mTitle);
    }


    @Override
    public void setDisplayShowCustomTitleEnabled(boolean showTitleEnabled) {
        TextView titleView = mContext.findViewById(R.id.title);
        titleView.setText(mTitle);
        if (showTitleEnabled) {
            setDisplayShowTitleEnabled(false);
        }

    }


    @Override
    public void setDisplayHomeAsUpEnabled(boolean showHomeEnabled) {
        if (showHomeEnabled) back.setVisibility(View.VISIBLE);
        else back.setVisibility(View.GONE);
    }


    @Override
    public void setDisplayShowTitleEnabled(boolean showTitleEnabled) {
        ActionBar actionBar = mContext.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(showTitleEnabled);
            if (showTitleEnabled) {
                setDisplayShowCustomTitleEnabled(false);
            }
        }
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            View view = inflater.inflate(layoutResID, null);
            setContentView(view);
        }
    }


    @Override
    public void setContentView(View view) {
        RelativeLayout r = mContext.findViewById(R.id.base_content);
        if (r != null) {
            r.addView(view, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        }
    }

    @Override
    public void setUpIndicator(int resId) {
        ActionBar actionBar = mContext.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(resId);
            setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void showMenu(int menuCode) {
        Menu menu = mToolbar.getMenu();
        switch (menuCode) {

            case MenuCode.SUBMIT:
                MenuItem submit = menu.findItem(R.id.action_submit);
                submit.setVisible(true);
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
       if (item.getItemId() == R.id.action_share) {
            mContext.onMenuClick(R.id.action_share);
        } else if (item.getItemId() == R.id.action_submit) {
            mContext.onMenuClick(R.id.action_submit);
        }else if (item.getItemId() == R.id.action_settings) {
            mContext.onMenuClick(R.id.action_settings);
        }else if (item.getItemId()==R.id.action_order_no){
            mContext.onMenuClick(R.id.action_order_no);
        }
        return false;
    }
}

