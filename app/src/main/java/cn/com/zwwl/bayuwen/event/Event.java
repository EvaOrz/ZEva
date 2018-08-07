package cn.com.zwwl.bayuwen.event;

import cn.com.zwwl.bayuwen.model.PintuModel;

/**
 * Event
 */

public class Event {
    public static class MessageEvent {

    }

    //拼图
    public static class JigsawEvent {
        public final PintuModel pintuModel;

        public JigsawEvent(PintuModel pintuModel) {
            this.pintuModel = pintuModel;
        }
    }

    public static class WalletEvent {
        public final String id;
        public final String name;

        public WalletEvent(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }


}
