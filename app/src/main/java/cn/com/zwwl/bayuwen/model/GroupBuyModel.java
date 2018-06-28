package cn.com.zwwl.bayuwen.model;

/**
 * 团购信息
 *
 *  "id":"195",
 "purchase_code":"5B32F71E771AA",
 "uid":"260935",
 "type":"1",
 "status":"0",
 "valid":"1",
 "end_time":"2018-06-29 00:00:00",
 "discount_id":"10",
 "created_at":"2018-06-27 10:31:58",
 "discount":Object{...},
 "course":Object{...},
 "dianfu":0,
 "detail":null,
 "state":2,
 "oid":"10180627172736477932"
 */
public class GroupBuyModel extends Entry {


    private DiscountBean discount = new DiscountBean();
    /**
     * 0:未发起团购或已发起团购没生成订单
     * 1:已生成订单并已生成订单但未支付
     * 2:已生成订单并已生成订单并且已支付
     */
    private int state = 0;
    private int oid;
    private String code = ""; // 开团码
    private String purchase_code = ""; // 拼团码
    private KeModel keModel;

    public DiscountBean getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountBean discount) {
        this.discount = discount;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class DiscountBean extends Entry {
        private int id;
        private String title;
        private int kid;
        private double discount_price;
        private double sponsor_price;
        private double join_price;
        private int delayed;
        private int limit_num;
        private String start_time;
        private String end_time;
        private int state;
        private String created_at;
        private String updated_at;
        private String operator;
        private double material_price;
        private double total_price;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getKid() {
            return kid;
        }

        public void setKid(int kid) {
            this.kid = kid;
        }

        public double getDiscount_price() {
            return discount_price;
        }

        public void setDiscount_price(double discount_price) {
            this.discount_price = discount_price;
        }

        public double getSponsor_price() {
            return sponsor_price;
        }

        public void setSponsor_price(double sponsor_price) {
            this.sponsor_price = sponsor_price;
        }

        public double getJoin_price() {
            return join_price;
        }

        public void setJoin_price(double join_price) {
            this.join_price = join_price;
        }

        public int getDelayed() {
            return delayed;
        }

        public void setDelayed(int delayed) {
            this.delayed = delayed;
        }

        public int getLimit_num() {
            return limit_num;
        }

        public void setLimit_num(int limit_num) {
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

        public double getMaterial_price() {
            return material_price;
        }

        public void setMaterial_price(double material_price) {
            this.material_price = material_price;
        }

        public double getTotal_price() {
            return total_price;
        }

        public void setTotal_price(double total_price) {
            this.total_price = total_price;
        }
    }

    public KeModel getKeModel() {
        return keModel;
    }

    public String getPurchase_code() {
        return purchase_code;
    }

    public void setPurchase_code(String purchase_code) {
        this.purchase_code = purchase_code;
    }

    public void setKeModel(KeModel keModel) {
        this.keModel = keModel;
    }
}
