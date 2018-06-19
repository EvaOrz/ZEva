package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.AddTopicLabelAdapter;
import cn.com.zwwl.bayuwen.api.AddTopicContrimApi;
import cn.com.zwwl.bayuwen.api.AddTopicLabelApi;
import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.AddTopicLabelModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.ToastUtil;

public class TopicLabelActivity extends AppCompatActivity implements View.OnClickListener ,AdapterView.OnItemClickListener{

    @BindView(R.id.feed_back)
    ImageView feedBack;
    @BindView(R.id.feed_commit)
    TextView feedCommit;
   // @BindView(R.id.list_lebal)
    private ListView listlebal;
    private String url = UrlUtil.getAddTopicTabel();
    private List<AddTopicLabelModel> addTopicLabelModelss;
    private AddTopicLabelAdapter addTopicLabelAdapter;
    private  String topictile,topic_content;
    private  int is_anonymous =0;

    private HashMap<String ,String >  params;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_label);
        ButterKnife.bind(this);
       params=new HashMap<>();
        addTopicLabelModelss =new ArrayList<>();
        Intent intent =getIntent();
        topictile =intent.getStringExtra("topictile");
        topic_content=intent.getStringExtra("topic_content");
        is_anonymous =intent.getIntExtra("is_anonymous",0);


        initView();
        HttpData();



    }


    private void HttpData() {
        new AddTopicLabelApi(this,url,new ResponseCallBack<List<AddTopicLabelModel>>() {
            @Override
            public void result(List<AddTopicLabelModel> addTopicLabelModels, ErrorMsg errorMsg) {
                if (addTopicLabelModels != null) {
                    addTopicLabelModelss=addTopicLabelModels;
                    addTopicLabelAdapter =new AddTopicLabelAdapter(TopicLabelActivity.this,addTopicLabelModelss);
                    listlebal.setAdapter(addTopicLabelAdapter);

                } else {
                    ToastUtil.showShortToast("暂无数据");
                }
            }


        });
    }



    private void initView() {
        listlebal=findViewById(R.id.list_lebal);
        listlebal.setOnItemClickListener(this);

        feedBack.setOnClickListener(this);
        feedCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.feed_back:
               finish();
               break;
           case R.id.feed_commit:


               //根据得到的位置，获取选中的item的数据Bean
               int selectionPosition =addTopicLabelAdapter.getSelectPosition();
                String cource_id=addTopicLabelModelss.get(selectionPosition).getId();
                params.put("name",topictile);
                params.put("content",topic_content);
                params.put("is_anonymous",is_anonymous+"");
                params.put("course_id",cource_id+"");

                ContirmData();
                break;

       }
    }

    private void ContirmData() {
        new AddTopicContrimApi(this,params,new ResponseCallBack<String>() {
            @Override
            public void result(String message, ErrorMsg errorMsg) {

                if (errorMsg==null){

                    ToastUtil.showShortToast("创建话题成功");
                } else {
                    ToastUtil.showShortToast(errorMsg.getDesc());
                }
            }


        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         AddTopicLabelAdapter.ViewHolder  holder = (AddTopicLabelAdapter.ViewHolder) view.getTag();
         holder.checkId.toggle();
    }
}
