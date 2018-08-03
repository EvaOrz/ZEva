package cn.com.zwwl.bayuwen.model;

/**
 * 团购信息
 */
public class GroupBuyModel extends Entry {


    private DiscountBean discount = new DiscountBean();
    /**
     * 0:未发起团购
     * 1:已生成订单并已生成订单但未支付
     * 2:已生成订单并已生成订单并且已支付
     * 3:已发起团购没生成订单
     */
    private int state = 0;
    private String oid;
    private String type = "";// 1：单独参团 2：垫付
    private String code = ""; // 开团码

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

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

}
