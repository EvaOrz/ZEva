package cn.com.zwwl.bayuwen.model;

/**
 * 退费详情model
 */
public class TuifeeModel extends Entry {

    private int type;//1-原路退费 2-账户退费
    private String reason;
    private String refund_fee;
    private int state;//0-待审核 1-退款中 2-已完成 3-已拒绝
    private String created_at;
    private String refund_no;
    private KeModel keModel;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRefund_fee() {
        return refund_fee;
    }

    public void setRefund_fee(String refund_fee) {
        this.refund_fee = refund_fee;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getRefund_no() {
        return refund_no;
    }

    public void setRefund_no(String refund_no) {
        this.refund_no = refund_no;
    }

    public KeModel getKeModel() {
        return keModel;
    }

    public void setKeModel(KeModel keModel) {
        this.keModel = keModel;
    }
}
