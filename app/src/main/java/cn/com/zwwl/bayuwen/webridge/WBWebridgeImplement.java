package cn.com.zwwl.bayuwen.webridge;

import android.content.Context;

import org.json.JSONObject;

/**
 * 根据js发送的command注册的方法
 *
 * @author user
 */
public class WBWebridgeImplement implements WBWebridgeListener {

    private Context mContext;

    public WBWebridgeImplement(Context c) {
        mContext = c;
    }

    // ======================js调用的native方法======================

    /**
     * 分享 (异步) {"command":"share","sequence":2,"params":{"content":
     * "展望2016：一年大事一览:彭博新闻记者将继续报道2016年的重大事件，让我们按时间顺序一起展望2016热点事件。","thumb":
     * "http:\/\/s.qiniu.bb.bbwc.cn\/issue_0\/category\/2016\/0104\/5689cda80b488_90x90.jpg",
     * "link":"http:\/\/read.bbwc.cn\/dufabs.html
     * " } }
     * <p>
     * shareChannel: ShareTypeWeibo,        ShareTypeWeixin,        ShareTypeWeixinFriendCircle
     */
    public void share(JSONObject json, WBWebridge.AsynExecuteCommandListener listener) {
        if (listener != null) {

            listener.onCallBack(json.toString());
        }
    }


    public void domReady(JSONObject json, WBWebridge.AsynExecuteCommandListener listener) {

    }

}
