package cn.com.zwwl.bayuwen.model;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;

/**
 * 课程model
 */
public class KeModel extends Entry {
    private List<TeacherModel> teacherModels = new ArrayList<>();
    private String cartId = ""; // 购课单id，购课单列表用，其他场合不用解析
    private String detailId = "";// 退费id，（退费选子课列表，退费课程列表）用，其他场合不用解析
    /**
     * refund 0:未退款 1:部分退款 2:全额退款 3:退款中 4:退款被拒绝
     */
    private String refund = "0";
    /**
     * 是否被选中（购课单、开发票页面用）
     */
    private boolean hasSelect = false;
    /**
     * 订单model（发票历史页面用）
     */
    private OrderModel.OrderDetailModel orderDetailModel;

    private String id;
    private String kid;
    private String title;
    private String roomId;
    private String desc;
    private String content;
    private String pic;
    private String listpic;
    private String tname;
    private String tid;
    private String buyPrice;
    private String sellPrice;
    private String backPlayPrice;
    private String addtime;
    private String updateTime;
    private String start_at;
    private String end_at;
    private String class_start_at;
    private String class_end_at;
    private Object class_start_at_bak;
    private String startPtime = "0";
    private String endPtime = "0";
    private String startBtime;
    private String endBtime;
    private String buyNum;
    private String playNum;
    private String hit;
    private String adminId;
    private String adminName;
    private String users;
    private String state;
    private String sort;
    private String delete;
    private String tags;
    private String type;
    private Object subject_name;
    private Object type_count;
    private String term;
    private String window_isopen;
    private Object remark;
    private String source = "1";// source=1 直播（回放）source=2 点播课
    private String level;
    private Object service;
    private String model;
    private String single_price;
    private String material_price;
    private String weekday;
    private String school;
    private String city;
    private String num;
    private String online = "0";//  online=0 线下（面授）课  online=1 线上课
    private String hours;
    private String discount_amount;
    private String discount_start_time;
    private String discount_end_time;
    private String stock;
    private String origin_stock;
    private Object client_channel;
    private String onsale_time;
    private String nianbu;
    private String year;
    private String is_close;
    private String is_upmodel;
    private String upmodel;
    private String upmodelend;
    private String video;
    private String appear_time;
    private String disappear_time;
    private String can_refund;
    private String region;
    private String typeName;
    private String tagName;
    private String templet;
    private String templet_id;
    private String trule_id;
    private String c_target;
    private String c_trait;
    private int is_discount;
    private int is_promotion;
    private int is_groupbuy;
    private String name;
    private String img;
    private GroupBuyModel groupbuy = new GroupBuyModel();
    private List<PromotionModel> promotionModels = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getListpic() {
        return listpic;
    }

    public void setListpic(String listpic) {
        this.listpic = listpic;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getBackPlayPrice() {
        return backPlayPrice;
    }

    public void setBackPlayPrice(String backPlayPrice) {
        this.backPlayPrice = backPlayPrice;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStart_at() {
        return start_at;
    }

    public void setStart_at(String start_at) {
        this.start_at = start_at;
    }

    public String getEnd_at() {
        return end_at;
    }

    public void setEnd_at(String end_at) {
        this.end_at = end_at;
    }

    public String getClass_start_at() {
        return class_start_at;
    }

    public void setClass_start_at(String class_start_at) {
        this.class_start_at = class_start_at;
    }

    public String getClass_end_at() {
        return class_end_at;
    }

    public void setClass_end_at(String class_end_at) {
        this.class_end_at = class_end_at;
    }

    public Object getClass_start_at_bak() {
        return class_start_at_bak;
    }

    public void setClass_start_at_bak(Object class_start_at_bak) {
        this.class_start_at_bak = class_start_at_bak;
    }

    public String getStartPtime() {
        return startPtime;
    }

    public void setStartPtime(String startPtime) {
        this.startPtime = startPtime;
    }

    public String getEndPtime() {
        return endPtime;
    }

    public void setEndPtime(String endPtime) {
        this.endPtime = endPtime;
    }

    public String getStartBtime() {
        return startBtime;
    }

    public void setStartBtime(String startBtime) {
        this.startBtime = startBtime;
    }

    public String getEndBtime() {
        return endBtime;
    }

    public void setEndBtime(String endBtime) {
        this.endBtime = endBtime;
    }

    public String getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(String buyNum) {
        this.buyNum = buyNum;
    }

    public String getPlayNum() {
        return playNum;
    }

    public void setPlayNum(String playNum) {
        this.playNum = playNum;
    }

    public String getHit() {
        return hit;
    }

    public void setHit(String hit) {
        this.hit = hit;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(Object subject_name) {
        this.subject_name = subject_name;
    }

    public Object getType_count() {
        return type_count;
    }

    public void setType_count(Object type_count) {
        this.type_count = type_count;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getWindow_isopen() {
        return window_isopen;
    }

    public void setWindow_isopen(String window_isopen) {
        this.window_isopen = window_isopen;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public String getSource() {
        return source;
    }

    public int getTagImg() {
        if (online.equals("0")) {//面授
            return R.mipmap.icon_face_teach;
        } else {
            if (source.equals("1")) {
                if (Long.valueOf(endPtime) > System.currentTimeMillis()) {//直播
                    return R.mipmap.icon_live;
                } else//回放
                    return R.mipmap.icon_replay;
            } else if (source.equals("2")) {//录播
                return R.mipmap.icon_record;
            }
        }
        return R.mipmap.icon_face_teach;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Object getService() {
        return service;
    }

    public void setService(Object service) {
        this.service = service;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSingle_price() {
        return single_price;
    }

    public void setSingle_price(String single_price) {
        this.single_price = single_price;
    }

    public String getMaterial_price() {
        return material_price;
    }

    public void setMaterial_price(String material_price) {
        this.material_price = material_price;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(String discount_amount) {
        this.discount_amount = discount_amount;
    }

    public String getDiscount_start_time() {
        return discount_start_time;
    }

    public void setDiscount_start_time(String discount_start_time) {
        this.discount_start_time = discount_start_time;
    }

    public String getDiscount_end_time() {
        return discount_end_time;
    }

    public void setDiscount_end_time(String discount_end_time) {
        this.discount_end_time = discount_end_time;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getOrigin_stock() {
        return origin_stock;
    }

    public void setOrigin_stock(String origin_stock) {
        this.origin_stock = origin_stock;
    }

    public Object getClient_channel() {
        return client_channel;
    }

    public void setClient_channel(Object client_channel) {
        this.client_channel = client_channel;
    }

    public String getOnsale_time() {
        return onsale_time;
    }

    public void setOnsale_time(String onsale_time) {
        this.onsale_time = onsale_time;
    }

    public String getNianbu() {
        return nianbu;
    }

    public void setNianbu(String nianbu) {
        this.nianbu = nianbu;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getIs_close() {
        return is_close;
    }

    public void setIs_close(String is_close) {
        this.is_close = is_close;
    }

    public String getIs_upmodel() {
        return is_upmodel;
    }

    public void setIs_upmodel(String is_upmodel) {
        this.is_upmodel = is_upmodel;
    }

    public String getUpmodel() {
        return upmodel;
    }

    public void setUpmodel(String upmodel) {
        this.upmodel = upmodel;
    }

    public String getUpmodelend() {
        return upmodelend;
    }

    public void setUpmodelend(String upmodelend) {
        this.upmodelend = upmodelend;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getAppear_time() {
        return appear_time;
    }

    public void setAppear_time(String appear_time) {
        this.appear_time = appear_time;
    }

    public String getDisappear_time() {
        return disappear_time;
    }

    public void setDisappear_time(String disappear_time) {
        this.disappear_time = disappear_time;
    }

    public String getCan_refund() {
        return can_refund;
    }

    public void setCan_refund(String can_refund) {
        this.can_refund = can_refund;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTemplet() {
        return templet;
    }

    public void setTemplet(String templet) {
        this.templet = templet;
    }

    public String getTemplet_id() {
        return templet_id;
    }

    public void setTemplet_id(String templet_id) {
        this.templet_id = templet_id;
    }

    public String getTrule_id() {
        return trule_id;
    }

    public void setTrule_id(String trule_id) {
        this.trule_id = trule_id;
    }

    public String getC_target() {
        return c_target;
    }

    public void setC_target(String c_target) {
        this.c_target = c_target;
    }

    public String getC_trait() {
        return c_trait;
    }

    public void setC_trait(String c_trait) {
        this.c_trait = c_trait;
    }


    public int getIs_discount() {
        return is_discount;
    }

    public void setIs_discount(int is_discount) {
        this.is_discount = is_discount;
    }

    public int getIs_promotion() {
        return is_promotion;
    }

    public void setIs_promotion(int is_promotion) {
        this.is_promotion = is_promotion;
    }

    public int getIs_groupbuy() {
        return is_groupbuy;
    }

    public void setIs_groupbuy(int is_groupbuy) {
        this.is_groupbuy = is_groupbuy;
    }

    public GroupBuyModel getGroupbuy() {
        return groupbuy;
    }

    public void setGroupbuy(GroupBuyModel groupbuy) {
        this.groupbuy = groupbuy;
    }

    public List<TeacherModel> getTeacherModels() {
        return teacherModels;
    }

    public void setTeacherModels(List<TeacherModel> teacherModels) {
        this.teacherModels = teacherModels;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getRefund() {
        return refund;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public List<PromotionModel> getPromotionModels() {
        return promotionModels;
    }

    public void setPromotionModels(List<PromotionModel> promotionModels) {
        this.promotionModels = promotionModels;
    }

    public boolean isHasSelect() {
        return hasSelect;
    }

    public void setHasSelect(boolean hasSelect) {
        this.hasSelect = hasSelect;
    }

    public OrderModel.OrderDetailModel getOrderDetailModel() {
        return orderDetailModel;
    }

    public void setOrderDetailModel(OrderModel.OrderDetailModel orderDetailModel) {
        this.orderDetailModel = orderDetailModel;
    }
}
