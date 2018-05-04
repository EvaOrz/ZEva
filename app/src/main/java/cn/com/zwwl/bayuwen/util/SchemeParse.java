package cn.com.zwwl.bayuwen.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.activity.WebActivity;
import cn.com.zwwl.bayuwen.model.Entry;

public class SchemeParse {

    public static final String SCHEME = "zwwl://";
    public static final String WEB = "web";// 打开内部浏览器
    public static final String COURSE = "course";// 打开课程首页


    public static boolean clickZwwl(Context context, String link, Entry[] entries, View view,
                                    Class<?>... cls) {
        if (TextUtils.isEmpty(link)) {
            //            doLinkNull(context, entries, cls);
        } else if (link.toLowerCase().startsWith(SCHEME)) {
            List<String> list = parser(link);
            String key = list.size() > 0 ? list.get(0) : "";
            if (key.equals(WEB)) {
                if (list.size() > 1) {
                    Intent i = new Intent(context, WebActivity.class);
                    i.putExtra("WebActivity_data", list.get(1));
                    context.startActivity(i);
                }

            } else if (key.equals(COURSE)) {

            }
        }
        return true;
    }

    public static ArrayList<String> parser(String uri) {
        ArrayList<String> list = new ArrayList<>();
        if (TextUtils.isEmpty(uri)) return list;
        String[] array = uri.split("://");
        if (array.length > 1) {
            String[] param = array[1].split("/");
            for (String p : param) {
                list.add(p);
            }
        }
        return list;
    }
}
