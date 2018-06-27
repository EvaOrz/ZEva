package cn.com.zwwl.bayuwen.model;

import org.json.JSONObject;

/**
 * 发票model
 */
public class FaPiaoModel extends Entry {

    private String id;
    private String uid;
    private String utype;// 1个人2企业
    private String kid;
    private String oid;
    private String invo_title;
    private String invo_type;
    private String invo_amount;
    private String invo_tax_no;
    private String rece_name;
    private String rece_phone;
    private String rece_address;
    private String created_at;
    private String email;
    private String apply_user;
    private Object roleusers;
    private PiaoType piaoType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getInvo_title() {
        return invo_title;
    }

    public void setInvo_title(String invo_title) {
        this.invo_title = invo_title;
    }

    public String getInvo_type() {
        return invo_type;
    }

    public void setInvo_type(String invo_type) {
        this.invo_type = invo_type;
    }

    public String getInvo_amount() {
        return invo_amount;
    }

    public void setInvo_amount(String invo_amount) {
        this.invo_amount = invo_amount;
    }

    public String getInvo_tax_no() {
        return invo_tax_no;
    }

    public void setInvo_tax_no(String invo_tax_no) {
        this.invo_tax_no = invo_tax_no;
    }

    public String getRece_name() {
        return rece_name;
    }

    public void setRece_name(String rece_name) {
        this.rece_name = rece_name;
    }

    public String getRece_phone() {
        return rece_phone;
    }

    public void setRece_phone(String rece_phone) {
        this.rece_phone = rece_phone;
    }

    public String getRece_address() {
        return rece_address;
    }

    public void setRece_address(String rece_address) {
        this.rece_address = rece_address;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApply_user() {
        return apply_user;
    }

    public void setApply_user(String apply_user) {
        this.apply_user = apply_user;
    }

    public Object getRoleusers() {
        return roleusers;
    }

    public void setRoleusers(Object roleusers) {
        this.roleusers = roleusers;
    }

    public PiaoType getPiaoType() {
        return piaoType;
    }

    public void setPiaoType(PiaoType piaoType) {
        this.piaoType = piaoType;
    }

    /**
     * 发票类型
     */
    public static class PiaoType extends Entry {

        /**
         * money : 4948.00
         * invoice_type : {"name":"非学历教育服务费","value":1}
         */

        private String money;
        private String name;
        private int value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public static PiaoType parsePiaoType(JSONObject jsonObject, PiaoType piaoType) {
            piaoType.setMoney(jsonObject.optString("money"));
            JSONObject ss = jsonObject.optJSONObject("invoice_type");
            if (!isNull(ss)) {
                piaoType.setName(ss.optString("name"));
                piaoType.setValue(ss.optInt("value"));
            }
            return piaoType;

        }
    }
}
