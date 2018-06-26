package cn.com.zwwl.bayuwen.model;

/**
 * 团购信息
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
        private Double discount_price;
        private Double sponsor_price;
        private Double join_price;
        private int delayed;
        private int limit_num;
        private String start_time;
        private String end_time;
        private int state;
        private String created_at;
        private String updated_at;
        private String operator;
        private Double material_price;
        private Double total_price;

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

        public Double getDiscount_price() {
            return discount_price;
        }

        public void setDiscount_price(Double discount_price) {
            this.discount_price = discount_price;
        }

        public Double getSponsor_price() {
            return sponsor_price;
        }

        public void setSponsor_price(Double sponsor_price) {
            this.sponsor_price = sponsor_price;
        }

        public Double getJoin_price() {
            return join_price;
        }

        public void setJoin_price(Double join_price) {
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

        public Double getMaterial_price() {
            return material_price;
        }

        public void setMaterial_price(Double material_price) {
            this.material_price = material_price;
        }

        public Double getTotal_price() {
            return total_price;
        }

        public void setTotal_price(Double total_price) {
            this.total_price = total_price;
        }
    }
}
