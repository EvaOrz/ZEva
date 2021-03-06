package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.security.cert.Certificate;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.util.ToastUtil;

public class CreateTopicActivity extends BaseActivity {


    TextView cancelId;
    TextView keepId;
    EditText entryEv;
    EditText entryTopicContent;
    CheckBox checkbox1;
    private String topictile, topic_content;
    private int is_anonymous = 0;
    public static CreateTopicActivity createTopicActivity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_topic);
        createTopicActivity = this;
        iniView();
    }

    @Override
    protected void initData() {

    }

    private void iniView() {
        cancelId = findViewById(R.id.cancel_id);
        keepId = findViewById(R.id.keep_id);
        entryEv = findViewById(R.id.entry_ev);
        entryTopicContent = findViewById(R.id.entry_topic_content);
        checkbox1 = findViewById(R.id.checkbox1);

        cancelId.setOnClickListener(this);
        keepId.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_id:
                finish();
                break;
            case R.id.keep_id:
                topictile = entryEv.getText().toString().trim();
                topic_content = entryTopicContent.getText().toString().trim();
                if (checkbox1.isChecked()) {
                    is_anonymous = 1;
                } else {
                    is_anonymous = 0;
                }
                if (topictile.length() > 0 && topic_content.length() > 0) {
                    Intent intent = new Intent(this, TopicLabelActivity.class);
                    intent.putExtra("topictile", topictile);
                    intent.putExtra("topic_content", topic_content);
                    intent.putExtra("is_anonymous", is_anonymous);
                    startActivity(intent);
//                    finish();
                } else {
                    ToastUtil.showShortToast("请填写话题标题或者内容");
                }
                break;

        }
    }


}
