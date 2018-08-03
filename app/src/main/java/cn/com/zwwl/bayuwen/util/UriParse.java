package cn.com.zwwl.bayuwen.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.com.zwwl.bayuwen.activity.AllXunzhangActivity;
import cn.com.zwwl.bayuwen.activity.CalendarActivity;
import cn.com.zwwl.bayuwen.activity.CourseDetailActivity;
import cn.com.zwwl.bayuwen.activity.MessageActivity;
import cn.com.zwwl.bayuwen.activity.MessageDetailActivity;
import cn.com.zwwl.bayuwen.activity.TeacherDetailActivity;
import cn.com.zwwl.bayuwen.activity.WebActivity;
import cn.com.zwwl.bayuwen.activity.fm.AlbumDetailActivity;

/**
 * 课程详情：                            zwwl://course/{{课程ID}}
 * 课程报告(课节报告,期中报告，期末报告)：   zwwl://course/report/{{reportid}}
 * 音频课程详情：                         zwwl://audio/{{音频id}}
 * 老师：                                zwwl://teacher/{{老师ID}}
 * WEB（交互类型 1:push  2:从底部弹出 3:调用外部浏览器打开）zwwl://web/{{交互类型}}/{{http地址}}
 * 消息(通知)：                           zwwl://message/{{messageid}}
 * 话题详情：                             zwwl://topic/{{topicid}}
 * 课程日历：                             zwwl://calendar/{{student_no}}/{{yyyy-mm-dd}}
 * 我的成就：                             zwwl://badge
 * 我的课程详情界面 (结课后，进入)：         zwwl://course/my/{{课程id}}
 */
public class UriParse {

    private static ArrayList<String> parser(String uri) {
        ArrayList<String> list = new ArrayList<>();
        if (TextUtils.isEmpty(uri)) return list;
        String[] array = uri.split("://");
        if (array.length > 1 && array[0].equals("zwwl")) {
            String[] param = array[1].split("/");
            list.addAll(Arrays.asList(param));
        }
        return list;
    }

    private static void goXunzhang(Context context) {
        context.startActivity(new Intent(context, AllXunzhangActivity.class));
    }

    private static void goKeDetail(Context context, String kid) {
        Intent i = new Intent(context, CourseDetailActivity.class);
        i.putExtra("CourseDetailActivity_id", kid);
        context.startActivity(i);
    }

    /**
     * 打开消息页面
     *
     * @param context
     * @param mid
     */
    private static void goMessage(Context context, String mid) {
        Intent intent =new Intent(context, MessageActivity.class);
        context.startActivity(intent);

    }

    /**
     * @param context
     * @param type    1:push  2:从底部弹出 3:调用外部浏览器打开
     * @param link
     */
    private static void goWeb(Context context, String type, String link) {
        if (type.equals("1")) {
            Intent i = new Intent(context, WebActivity.class);
            i.putExtra("WebActivity_data", link);
            context.startActivity(i);
        } else if (type.equals("2")) {
            Intent i = new Intent(context, WebActivity.class);
            i.putExtra("WebActivity_data", link);
            context.startActivity(i);
        } else if (type.equals("3")) {
            Uri uri = Uri.parse(link);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }

    }

    /**
     * 打开话题页面
     *
     * @param context
     * @param toid
     */
    private static void goTopic(Context context, String toid) {
        Intent intent =new Intent(context, MessageActivity.class);
        intent.putExtra("ass","ssd");
        context.startActivity(intent);
    }

    /**
     * 课程报告页面
     *
     * @param context
     * @param link
     */
    private static void goKeReport(Context context, String link) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("WebActivity_title", "");
        intent.putExtra("WebActivity_data", link);
        context.startActivity(intent);
    }

    /**
     * 课程日历页面
     *
     * @param context
     * @param studentNo
     * @param date
     */
    private static void goCalendar(Context context, String studentNo, String date) {
        Intent i = new Intent(context, CalendarActivity.class);
        context.startActivity(i);
    }

    /**
     * 专辑列表页面
     *
     * @param context
     * @param aid
     */
    private static void goAlbumDetail(Context context, String aid) {
        Intent i = new Intent(context, AlbumDetailActivity.class);
        i.putExtra("AlbumDetailActivity_data", aid);
        context.startActivity(i);
    }

    private static void goTeacherDetail(Context context, String tid) {
        Intent i = new Intent(context, TeacherDetailActivity.class);
        i.putExtra("tid", tid);
        context.startActivity(i);
    }

    /**
     * 我的课程详情页面
     *
     * @param context
     * @param rid
     */
    private static void goMyKe(Context context, String rid) {

    }

    public static void clickZwwl(Context context, String url) {
        List<String> pamalist = parser(url);
        if (!Tools.listNotNull(pamalist)) return;

        if (pamalist.get(0).equals("badge")) {
            goXunzhang(context);
        } else if (pamalist.get(0).equals("course")) {
            if (pamalist.size() == 2) {
                goKeDetail(context, pamalist.get(1));
            } else if (pamalist.size() == 3) {
                if (pamalist.get(1).equals("report")) {
                    goKeReport(context, pamalist.get(2));
                } else if (pamalist.get(1).equals("my")) {
                    goMyKe(context, pamalist.get(2));
                }
            }

        } else if (pamalist.get(0).equals("audio")) {
            if (pamalist.size() == 2)
                goAlbumDetail(context, pamalist.get(1));
        } else if (pamalist.get(0).equals("teacher")) {
            if (pamalist.size() == 2)
                goTeacherDetail(context, pamalist.get(1));
        } else if (pamalist.get(0).equals("message")) {
            if (pamalist.size() == 2)
                goMessage(context, pamalist.get(1));
        } else if (pamalist.get(0).equals("topic")) {
            if (pamalist.size() == 2)
                goTopic(context, pamalist.get(1));
        } else if (pamalist.get(0).equals("calendar")) {
            if (pamalist.size() == 3)
                goCalendar(context, pamalist.get(1), pamalist.get(2));
        } else if (pamalist.get(0).equals("web")) {
            if (pamalist.size() == 3) {
                goWeb(context, pamalist.get(1), pamalist.get(2));
            }
        }
    }
}
