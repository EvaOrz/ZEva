package cn.com.zwwl.bayuwen.model;


/**
 * 订单model
 * <p>
 * (我的订单页面列表数据)
 */
public class OrderModel extends Entry {

    /**
     * 支付时订单详情
     */
    private String trade_channel;
    private int trade_fee;
    private String bill_no;
    private String bill_title;
    private String trade_type;
    private String return_url;
    private OrderDetailModel orderDetailModel;


    public String getTrade_channel() {
        return trade_channel;
    }

    public void setTrade_channel(String trade_channel) {
        this.trade_channel = trade_channel;
    }

    public int getTrade_fee() {
        return trade_fee;
    }

    public void setTrade_fee(int trade_fee) {
        this.trade_fee = trade_fee;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getBill_title() {
        return bill_title;
    }

    public void setBill_title(String bill_title) {
        this.bill_title = bill_title;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public OrderDetailModel getOrderDetailModel() {
        return orderDetailModel;
    }

    public void setOrderDetailModel(OrderDetailModel orderDetailModel) {
        this.orderDetailModel = orderDetailModel;
    }

    /**
     * 订单价格详情
     * (支付页面订单detail dialog)
     */
    public static class OrderDetailModel extends Entry {

        private String oid;
        private double originPrice;
        private double amount;
        private double limitDiscount;
        private double couponDiscount;
        private double orderDiscount;
        private double promotionDiscount;
        private double groupBuyDiscount;
        private double assets;
        private String warn;// 警告

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public double getOriginPrice() {
            return originPrice;
        }

        public void setOriginPrice(double originPrice) {
            this.originPrice = originPrice;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getLimitDiscount() {
            return limitDiscount;
        }

        public void setLimitDiscount(double limitDiscount) {
            this.limitDiscount = limitDiscount;
        }

        public double getCouponDiscount() {
            return couponDiscount;
        }

        public void setCouponDiscount(double couponDiscount) {
            this.couponDiscount = couponDiscount;
        }

        public double getOrderDiscount() {
            return orderDiscount;
        }

        public void setOrderDiscount(double orderDiscount) {
            this.orderDiscount = orderDiscount;
        }

        public double getPromotionDiscount() {
            return promotionDiscount;
        }

        public void setPromotionDiscount(double promotionDiscount) {
            this.promotionDiscount = promotionDiscount;
        }

        public double getGroupBuyDiscount() {
            return groupBuyDiscount;
        }

        public void setGroupBuyDiscount(double groupBuyDiscount) {
            this.groupBuyDiscount = groupBuyDiscount;
        }

        public double getAssets() {
            return assets;
        }

        public void setAssets(double assets) {
            this.assets = assets;
        }

        public String getWarn() {
            return warn;
        }

        public void setWarn(String warn) {
            this.warn = warn;
        }
    }
}
