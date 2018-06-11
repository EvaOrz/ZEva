package cn.com.zwwl.bayuwen.model;

/**
 * 我的团购页面model
 */
public class TuanForMyListModel extends Entry{
    private KeModel keModel;
    private String id;
    private String purchase_code;
    private String uid;
    private String type;
    private String status;
    private String valid;
    private String discount_id;
    private String created_at;
    private DiscountBean discount;
    private int dianfu;

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

    public DiscountBean getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountBean discount) {
        this.discount = discount;
    }

    public int getDianfu() {
        return dianfu;
    }

    public void setDianfu(int dianfu) {
        this.dianfu = dianfu;
    }

    public KeModel getKeModel() {
        return keModel;
    }

    public void setKeModel(KeModel keModel) {
        this.keModel = keModel;
    }

    public static class DiscountBean {

        private String id;
        private String title;
        private String kid;
        private String discount_price;
        private String sponsor_price;
        private String join_price;
        private String delayed;
        private String limit_num;
        private String start_time;
        private String end_time;
        private String state;
        private String created_at;
        private String updated_at;
        private String operator;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getKid() {
            return kid;
        }

        public void setKid(String kid) {
            this.kid = kid;
        }

        public String getDiscount_price() {
            return discount_price;
        }

        public void setDiscount_price(String discount_price) {
            this.discount_price = discount_price;
        }

        public String getSponsor_price() {
            return sponsor_price;
        }

        public void setSponsor_price(String sponsor_price) {
            this.sponsor_price = sponsor_price;
        }

        public String getJoin_price() {
            return join_price;
        }

        public void setJoin_price(String join_price) {
            this.join_price = join_price;
        }

        public String getDelayed() {
            return delayed;
        }

        public void setDelayed(String delayed) {
            this.delayed = delayed;
        }

        public String getLimit_num() {
            return limit_num;
        }

        public void setLimit_num(String limit_num) {
            this.limit_num = limit_num;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }
    }
}
