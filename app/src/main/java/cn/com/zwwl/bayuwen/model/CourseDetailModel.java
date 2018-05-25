package cn.com.zwwl.bayuwen.model;

import java.util.List;

/**
 * Created by lousx on 2018/5/24.
 */

public class CourseDetailModel extends Entry {

    /**

     */

    private CourseEntity course;
    private CourseProgramEntity course_program;
    private List<TeacherModel> teacher;

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public void setCourse_program(CourseProgramEntity course_program) {
        this.course_program = course_program;
    }

    public void setTeacher(List<TeacherModel> teacher) {
        this.teacher = teacher;
    }

    public CourseEntity getCourse() {
        return course;
    }

    public CourseProgramEntity getCourse_program() {
        return course_program;
    }

    public List<TeacherModel> getTeacher() {
        return teacher;
    }

    public static class CourseEntity {
        /**
         */

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
        private String startPtime;
        private String endPtime;
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
        private Object tags;
        private String type;
        private Object subject_name;
        private String type_count;
        private String term;
        private String window_isopen;
        private Object remark;
        private String source;
        private String level;
        private Object service;
        private String model;
        private String single_price;
        private String material_price;
        private String weekday;
        private String school;
        private String city;
        private String num;
        private String online;
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
        private Object c_target;
        private Object c_trait;
        private int is_discount;
        private int is_promotion;
        private String is_groupbuy;
        private String id = "";
        private String kid = "";
        private String title = "";
        private String mime_type = "";
        private String duration = "";
        private String hd720 = "";
        private String size = "";
        private String width = "";
        private String height = "";
        private String display_aspect_ratio = "";
        private String status = "";
        private String tid = "";
        private String sort = "";
        private String grade = "";
        private String model = "";
        private String start_at = "";
        private String class_start_at_bak = "";
        private String tname = "";
        private String students = "";
        private String term = "";
        private String school_id = "";
        private String hours = "";
        private String class_end_at = "";
        private String class_start_at = "";
        private String substitute_tid = "";
        private String substitute_tname = "";
        private String class_id = "";
        private String summarize = "";
        private GroupBuyEntity groupBuy;

        public void setKid(String kid) {
            this.kid = kid;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public void setListpic(String listpic) {
            this.listpic = listpic;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public void setBuyPrice(String buyPrice) {
            this.buyPrice = buyPrice;
        }

        public void setSellPrice(String sellPrice) {
            this.sellPrice = sellPrice;
        }

        public void setBackPlayPrice(String backPlayPrice) {
            this.backPlayPrice = backPlayPrice;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public void setStart_at(String start_at) {
            this.start_at = start_at;
        }

        public void setEnd_at(String end_at) {
            this.end_at = end_at;
        }

        public void setClass_start_at(String class_start_at) {
            this.class_start_at = class_start_at;
        }

        public void setClass_end_at(String class_end_at) {
            this.class_end_at = class_end_at;
        }

        public void setClass_start_at_bak(Object class_start_at_bak) {
            this.class_start_at_bak = class_start_at_bak;
        }

        public void setStartPtime(String startPtime) {
            this.startPtime = startPtime;
        }

        public void setEndPtime(String endPtime) {
            this.endPtime = endPtime;
        }

        public void setStartBtime(String startBtime) {
            this.startBtime = startBtime;
        }

        public void setEndBtime(String endBtime) {
            this.endBtime = endBtime;
        }

        public void setBuyNum(String buyNum) {
            this.buyNum = buyNum;
        }

        public void setPlayNum(String playNum) {
            this.playNum = playNum;
        }

        public void setHit(String hit) {
            this.hit = hit;
        }

        public void setAdminId(String adminId) {
            this.adminId = adminId;
        }

        public void setAdminName(String adminName) {
            this.adminName = adminName;
        }

        public void setUsers(String users) {
            this.users = users;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public void setDelete(String delete) {
            this.delete = delete;
        }

        public void setTags(Object tags) {
            this.tags = tags;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setSubject_name(Object subject_name) {
            this.subject_name = subject_name;
        }

        public void setType_count(String type_count) {
            this.type_count = type_count;
        }

        public void setTerm(String term) {
            this.term = term;
        }

        public void setWindow_isopen(String window_isopen) {
            this.window_isopen = window_isopen;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public void setService(Object service) {
            this.service = service;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public void setSingle_price(String single_price) {
            this.single_price = single_price;
        }

        public void setMaterial_price(String material_price) {
            this.material_price = material_price;
        }

        public void setWeekday(String weekday) {
            this.weekday = weekday;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public void setOnline(String online) {
            this.online = online;
        }

        public void setHours(String hours) {
            this.hours = hours;
        }

        public void setDiscount_amount(String discount_amount) {
            this.discount_amount = discount_amount;
        }

        public void setDiscount_start_time(String discount_start_time) {
            this.discount_start_time = discount_start_time;
        }

        public void setDiscount_end_time(String discount_end_time) {
            this.discount_end_time = discount_end_time;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public void setOrigin_stock(String origin_stock) {
            this.origin_stock = origin_stock;
        }

        public void setClient_channel(Object client_channel) {
            this.client_channel = client_channel;
        }

        public void setOnsale_time(String onsale_time) {
            this.onsale_time = onsale_time;
        }

        public void setNianbu(String nianbu) {
            this.nianbu = nianbu;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public void setIs_close(String is_close) {
            this.is_close = is_close;
        }

        public void setIs_upmodel(String is_upmodel) {
            this.is_upmodel = is_upmodel;
        }

        public void setUpmodel(String upmodel) {
            this.upmodel = upmodel;
        }

        public void setUpmodelend(String upmodelend) {
            this.upmodelend = upmodelend;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public void setAppear_time(String appear_time) {
            this.appear_time = appear_time;
        }

        public void setDisappear_time(String disappear_time) {
            this.disappear_time = disappear_time;
        }

        public void setCan_refund(String can_refund) {
            this.can_refund = can_refund;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public void setTemplet(String templet) {
            this.templet = templet;
        }

        public void setTemplet_id(String templet_id) {
            this.templet_id = templet_id;
        }

        public void setTrule_id(String trule_id) {
            this.trule_id = trule_id;
        }

        public void setC_target(Object c_target) {
            this.c_target = c_target;
        }

        public void setC_trait(Object c_trait) {
            this.c_trait = c_trait;
        }

        public void setIs_discount(int is_discount) {
            this.is_discount = is_discount;
        }

        public void setIs_promotion(int is_promotion) {
            this.is_promotion = is_promotion;
        }

        public void setGroupBuy(GroupBuyEntity groupBuy) {
            this.groupBuy = groupBuy;
        }

        public String getKid() {
            return kid;
        }

        public String getTitle() {
            return title;
        }

        public String getRoomId() {
            return roomId;
        }

        public String getDesc() {
            return desc;
        }

        public String getContent() {
            return content;
        }

        public String getPic() {
            return pic;
        }

        public String getListpic() {
            return listpic;
        }

        public String getTname() {
            return tname;
        }

        public String getTid() {
            return tid;
        }

        public String getBuyPrice() {
            return buyPrice;
        }

        public String getSellPrice() {
            return sellPrice;
        }

        public String getBackPlayPrice() {
            return backPlayPrice;
        }

        public String getAddtime() {
            return addtime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public String getStart_at() {
            return start_at;
        }

        public String getEnd_at() {
            return end_at;
        }

        public String getClass_start_at() {
            return class_start_at;
        }

        public String getClass_end_at() {
            return class_end_at;
        }

        public Object getClass_start_at_bak() {
            return class_start_at_bak;
        }

        public String getStartPtime() {
            return startPtime;
        }

        public String getEndPtime() {
            return endPtime;
        }

        public String getStartBtime() {
            return startBtime;
        }

        public String getEndBtime() {
            return endBtime;
        }

        public String getBuyNum() {
            return buyNum;
        }

        public String getPlayNum() {
            return playNum;
        }

        public String getHit() {
            return hit;
        }

        public String getAdminId() {
            return adminId;
        }

        public String getAdminName() {
            return adminName;
        }

        public String getUsers() {
            return users;
        }

        public String getState() {
            return state;
        }

        public String getSort() {
            return sort;
        }

        public String getDelete() {
            return delete;
        }

        public Object getTags() {
            return tags;
        }

        public String getType() {
            return type;
        }

        public Object getSubject_name() {
            return subject_name;
        }

        public String getType_count() {
            return type_count;
        }

        public String getTerm() {
            return term;
        }

        public String getWindow_isopen() {
            return window_isopen;
        }

        public Object getRemark() {
            return remark;
        }

        public String getSource() {
            return source;
        }

        public String getLevel() {
            return level;
        }

        public Object getService() {
            return service;
        }

        public String getModel() {
            return model;
        }

        public String getSingle_price() {
            return single_price;
        }

        public String getMaterial_price() {
            return material_price;
        }

        public String getWeekday() {
            return weekday;
        }

        public String getSchool() {
            return school;
        }

        public String getCity() {
            return city;
        }

        public String getNum() {
            return num;
        }

        public String getOnline() {
            return online;
        }

        public String getHours() {
            return hours;
        }

        public String getDiscount_amount() {
            return discount_amount;
        }

        public String getDiscount_start_time() {
            return discount_start_time;
        }

        public String getDiscount_end_time() {
            return discount_end_time;
        }

        public String getStock() {
            return stock;
        }

        public String getOrigin_stock() {
            return origin_stock;
        }

        public Object getClient_channel() {
            return client_channel;
        }

        public String getOnsale_time() {
            return onsale_time;
        }

        public String getNianbu() {
            return nianbu;
        }

        public String getYear() {
            return year;
        }

        public String getIs_close() {
            return is_close;
        }

        public String getIs_upmodel() {
            return is_upmodel;
        }

        public String getUpmodel() {
            return upmodel;
        }

        public String getUpmodelend() {
            return upmodelend;
        }

        public String getVideo() {
            return video;
        }

        public String getAppear_time() {
            return appear_time;
        }

        public String getDisappear_time() {
            return disappear_time;
        }

        public String getCan_refund() {
            return can_refund;
        }

        public String getRegion() {
            return region;
        }

        public String getTypeName() {
            return typeName;
        }

        public String getTagName() {
            return tagName;
        }

        public String getTemplet() {
            return templet;
        }

        public String getTemplet_id() {
            return templet_id;
        }

        public String getTrule_id() {
            return trule_id;
        }

        public Object getC_target() {
            return c_target;
        }

        public Object getC_trait() {
            return c_trait;
        }

        public int getIs_discount() {
            return is_discount;
        }

        public int getIs_promotion() {
            return is_promotion;
        }

        public String getIs_groupbuy() {
            return is_groupbuy;
        }

        public void setIs_groupbuy(String is_groupbuy) {
            this.is_groupbuy = is_groupbuy;
        }

        public GroupBuyEntity getGroupBuy() {
            return groupBuy;
        }

        public static class GroupBuyEntity {
            /**
             * id : 1
             * title : sfsf
             * item_id : 7018
             * discount_price : 200
             * start_time : 2018-05-15 15:46:17
             * end_time : 2018-05-24 15:49:00
             * limit_num : 10
             * state : 1
             * created_at : 2018-05-21 15:46:17
             * updated_at : 2018-05-21 15:49:03
             * operator : 0
             */

            private String id;
            private String title;
            private String item_id;
            private int discount_price;
            private String start_time;
            private String end_time;
            private int limit_num;
            private int state;
            private String created_at;
            private String updated_at;
            private String operator;

            public void setId(String id) {
                this.id = id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setItem_id(String item_id) {
                this.item_id = item_id;
            }

            public void setDiscount_price(int discount_price) {
                this.discount_price = discount_price;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public void setLimit_num(int limit_num) {
                this.limit_num = limit_num;
            }

            public void setState(int state) {
                this.state = state;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public void setOperator(String operator) {
                this.operator = operator;
            }

            public String getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getItem_id() {
                return item_id;
            }

            public int getDiscount_price() {
                return discount_price;
            }

            public String getStart_time() {
                return start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public int getLimit_num() {
                return limit_num;
            }

            public int getState() {
                return state;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public String getOperator() {
                return operator;
            }
        }
    }

    public static class CourseProgramEntity {
        /**
         * kid : 7018
         * desc : 四年级大语文
         * users : 四年级
         * c_target :
         * c_trait :
         */

        private String kid;
        private String desc;
        private String users;
        private String c_target;
        private String c_trait;

        public void setKid(String kid) {
            this.kid = kid;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setUsers(String users) {
            this.users = users;
        }

        public void setC_target(String c_target) {
            this.c_target = c_target;
        }

        public void setC_trait(String c_trait) {
            this.c_trait = c_trait;
        }

        public String getKid() {
            return kid;
        }

        public String getDesc() {
            return desc;
        }

        public String getUsers() {
            return users;
        }

        public String getC_target() {
            return c_target;
        }

        public String getC_trait() {
            return c_trait;
        }
    }


}
