package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.db.UserDataHelper;
import cn.com.zwwl.bayuwen.model.AlbumModel;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 返回model为ErrorMsg的动作接口
 */
public class ActionApi extends BaseApi {
    private String url;
    private FetchEntryListener listener;
    private Map<String, String> pamas = new HashMap<>();
    private ActionType actionType;

    public enum ActionType {
        //喜欢
        ACTION_LIKE,
        //增加播放量
        ACTION_PLAY,
        //修改密码
        ACTION_CHANGE_PWD,
        //添加评论
        ACTION_ADD_COMMENT,

    }

    /**
     * 喜欢接口
     *
     * @param context
     * @param kid      操作的fm
     * @param type     0：取消喜欢 1：喜欢
     *
     *
     * 收藏接口
     *
     * @param context
     * @param kid - content
     * @param type     1-课程  2-文本  3-图片  4-链接
     * @param listener
     */
    public ActionApi(Context context, String kid, int type, FetchEntryListener listener) {
        super(context);
        actionType = ActionType.ACTION_LIKE;
        mContext = context;
        pamas.put("kid", kid);
        pamas.put("type", type + "");
        this.url = UrlUtil.likeUrl();
        this.listener = listener;
        post();
    }

    /**
     * 增加播放量
     *
     * @param context
     * @param kid
     * @param listener
     */
    public ActionApi(Context context, String kid, FetchEntryListener listener) {
        super(context);
        actionType = ActionType.ACTION_PLAY;
        mContext = context;
        pamas.put("kid", kid);
        pamas.put("lecture_id", "");
        this.url = UrlUtil.addPlayUrl();
        this.listener = listener;
        post();
    }

    /**
     * 修改密码
     *
     * @param context
     * @param username
     * @param password
     * @param code
     * @param listener
     */
    public ActionApi(Context context, String username, String password, String code, FetchEntryListener listener) {
        super(context);
        actionType = ActionType.ACTION_CHANGE_PWD;
        mContext = context;
        pamas.put("username", username);
        pamas.put("password", password);
        pamas.put("code", code);
        this.url = UrlUtil.changePwd();
        this.listener = listener;
        post();
    }

    /**
     * 添加评论
     *
     * @param context
     * @param kid
     * @param cid
     * @param commentid
     * @param content
     * @param type
     * @param listener
     */
    public ActionApi(Context context, String kid, String cid, String commentid, String content, String type, FetchEntryListener listener) {
        super(context);
        actionType = ActionType.ACTION_ADD_COMMENT;
        mContext = context;
        pamas.put("kid", kid);
        pamas.put("cid", cid);
        pamas.put("commentid", commentid);
        pamas.put("content", content);
        pamas.put("type", type);
        this.url = UrlUtil.getPingListurl(null);
        this.listener = listener;
        post();

    }

    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }

    /**
     * success = true表示操作成功
     *
     * @param jsonObject
     * @param errorMsg
     */
    @Override
    protected void handler(JSONObject jsonObject, ErrorMsg errorMsg) {
        if (errorMsg == null) {
            boolean success = jsonObject.optBoolean("success", false);
            if (success) {
                if (actionType == ActionType.ACTION_LIKE) {// 喜欢接口需要一个count 数目
                    JSONObject data = jsonObject.optJSONObject("data");
                    AlbumModel e = new AlbumModel();
                    e.setLikeNum(data.optInt("count"));
                    listener.setData(e);
                } else
                    listener.setData(null);
            } else
                listener.setData(new ErrorMsg());
        } else {
            listener.setData(errorMsg);
        }

    }

    @Override
    protected String getUrl() {
        return url;

    }
}

