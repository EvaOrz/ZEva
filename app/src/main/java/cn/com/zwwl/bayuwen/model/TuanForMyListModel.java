package cn.com.zwwl.bayuwen.model;

/**
 * 我的团购页面model
 */
public class TuanForMyListModel extends Entry {

    private String id;
    private String purchase_code;
    private String uid;
    private String type;
    private String status;
    private String valid;
    private String end_time;
    private String discount_id;
    private String created_at;
    private GroupBuyModel.DiscountBean discount;
    private KeModel keModel;
    private int dianfu;
    private Object detail;
    /**
     * 0:未发起团购
     * 1:已生成订单并已生成订单但未支付
     * 2:已生成订单并已生成订单并且已支付
     * 3:已发起团购没生成订单
     */
    private int state;
    private String oid;

    public KeModel getKeModel() {
        return keModel;
    }

    public void setKeModel(KeModel keModel) {
        this.keModel = keModel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPurchase_code() {
        return purchase_code;
    }

    public void setPurchase_code(String purchase_code) {
        this.purchase_code = purchase_code;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDiscount_id() {
        return discount_id;
    }

    public void setDiscount_id(String discount_id) {
        this.discount_id = discount_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public GroupBuyModel.DiscountBean getDiscount() {
        return discount;
    }

    public void setDiscount(GroupBuyModel.DiscountBean discount) {
        this.discount = discount;
    }

    public int getDianfu() {
        return dianfu;
    }

    public void setDianfu(int dianfu) {
        this.dianfu = dianfu;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }


}
