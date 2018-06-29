package cn.com.zwwl.bayuwen.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.AddTopicLabelModel;
import cn.com.zwwl.bayuwen.widget.CircleImageView;

public class AddTopicLabelAdapter extends BaseAdapter {

    private Activity mActivity;
    private List<AddTopicLabelModel> addTopicLabelModels;

    private int tempPosition = -1; //记录已经点击的CheckBox位置

    public AddTopicLabelAdapter(Activity activity, List<AddTopicLabelModel> addTopicLabelModels) {
        this.mActivity = activity;
        this.addTopicLabelModels = addTopicLabelModels;
    }

    @Override
    public int getCount() {
        return addTopicLabelModels.size() > 0 ? addTopicLabelModels.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return addTopicLabelModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        // if (convertView == null) {
        convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_topiclabel_layout, null);
        mViewHolder = new ViewHolder(convertView);

        //  }else {
        //    mViewHolder = (ViewHolder) convertView.getTag();
        //  }
        if (addTopicLabelModels.get(position).getUrl() != null || addTopicLabelModels.get
                (position).getUrl() != "") {
            ImageLoader.display(mActivity, mViewHolder.circle, addTopicLabelModels.get(position)
                    .getUrl());
        } else {
            ImageLoader.display(mActivity, mViewHolder.circle, R.mipmap.app_icon);
        }

        mViewHolder.labelName.setText(addTopicLabelModels.get(position).getName());

//        if (addTopicLabelModels.get(position).isCheck()){   //状态选中
//            mViewHolder.checkId.setChecked(true);
//
//        }else {
//             mViewHolder.checkId.setChecked(false);
//        }false
        mViewHolder.checkId.setId(position);

        mViewHolder.checkId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener
                () {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (tempPosition != -1) {
                        //根据id找到上次点击的CheckBox，将它设置为False
                        CheckBox tempCheckBox = mActivity.findViewById(tempPosition);
                        if (tempCheckBox != null) {
                            tempCheckBox.setChecked(false);
                        }
                    }
                    tempPosition = buttonView.getId();  //保存当前选中的CheckBox的id 值
                } else {
                    tempPosition = -1;
                }
            }
        });
        if (position == tempPosition) { //比较位置是否一样
            mViewHolder.checkId.setChecked(true);
        } else {
            mViewHolder.checkId.setChecked(false);
        }
        return convertView;
    }

    //返回当前CheckBox选中的位置，便于获取值
    public int getSelectPosition() {
        return tempPosition;
    }


    public static class ViewHolder {
        @BindView(R.id.circle)
        public CircleImageView circle;
        @BindView(R.id.label_name)
        public TextView labelName;
        @BindView(R.id.check_id)
        public CheckBox checkId;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
