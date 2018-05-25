package cn.com.zwwl.bayuwen.model;

import java.util.List;

/**
 * Created by lousx on 2018/5/24.
 */

public class CourseDetailModel extends Entry{

    /**
     * teacher : [{"tid":"35","pic":"http://resource.zhugexuetang.com/upload/2015/12/18/5674173eee75d.jpg","name":"王邓红","t_desc":"毕业于四川师范大学新闻系。大语文5年教龄金牌教师、一对一及游学负责人。从小酷爱语文，小升初、中考、高考语文成绩都名列全市前三。毕业后从报社编辑到走上讲台，我的启蒙老师教导我，老师只是引导，孩子才是课堂主体，所以从教以来，一直非常注重孩子自我思考能力和表达力的培养。孩子的答案，从不直接给予否定，让孩子敢于思考，敢于表达，再进行原则上的引导。每一节课都是孩子和我一起思考和探讨结束的。","t_style":"热情、细心、花仙子","t_harvest":null,"t_words":"热情、细心、花仙子"},{"tid":"1024","pic":"http://img.zhugexuetang.com/FtmTL3-yDSn7dCjY9vwk8Ovv1nKJ","name":"马睿启","t_desc":"<p>复旦大学生物科学本科，北京大学中文系硕士，功底扎实，文理兼备；兼有歌词篡改师、段子创作者、真人表情包、打油诗人等多种隐藏职业。<\/p>","t_style":"段子创作者","t_harvest":null,"t_words":null}]
     * course : {"kid":"7018","title":"四年级大语文","roomId":"0","desc":"四年级大语文","content":"四年级大语文","pic":"http://img.zhugexuetang.com/çº¿ä¸\u008bè¯¾é\u0085\u008då\u009b¾-800x450-04.jpg","listpic":"http://img.zhugexuetang.com/çº¿ä¸\u008bè¯¾é\u0085\u008då\u009b¾-800x450-04.jpg","tname":"王邓红、马睿启","tid":"35,1024","buyPrice":"2050","sellPrice":"3250","backPlayPrice":"3250","addtime":"0","updateTime":"0","start_at":"2018-03-02 00:00:00","end_at":"2018-06-29 00:00:00","class_start_at":"15:00:00","class_end_at":"17:00:00","class_start_at_bak":null,"startPtime":"1519974000","endPtime":"1530262800","startBtime":"0","endBtime":"0","buyNum":"6","playNum":"601","hit":"0","adminId":"0","adminName":"","users":"四年级","state":"0","sort":"2","delete":"0","tags":null,"type":"2","subject_name":null,"type_count":"2","term":"1","window_isopen":"0","remark":null,"source":"2","level":"1","service":null,"model":"C18452","single_price":"100","material_price":"50","weekday":"周五","school":"大钟寺校区","city":"010","num":"10","online":"0","hours":"2","discount_amount":"0","discount_start_time":"0000-00-00 00:00:00","discount_end_time":"0000-00-00 00:00:00","stock":"24","origin_stock":"30","client_channel":null,"onsale_time":"1980-01-01 00:00:00","nianbu":"小学部","year":"2018","is_close":"0","is_upmodel":"0","upmodel":"Q480","upmodelend":"2018-06-29 00:00:00","video":"","appear_time":"1980-01-01 00:00:00","disappear_time":"2030-01-01 08:00:00","can_refund":"0","region":"2001","typeName":" ","tagName":" ","templet":"tpl_payment_notice","templet_id":"0","trule_id":"0","c_target":null,"c_trait":null,"is_discount":0,"is_promotion":0,"groupBuy":{"id":"1","title":"sfsf","item_id":"7018","discount_price":200,"start_time":"2018-05-15 15:46:17","end_time":"2018-05-24 15:49:00","limit_num":10,"state":1,"created_at":"2018-05-21 15:46:17","updated_at":"2018-05-21 15:49:03","operator":"0"}}
     * course_program : {"kid":"7018","desc":"四年级大语文","users":"四年级","c_target":"","c_trait":""}
     */

    private CourseEntity course;
    private CourseProgramEntity course_program;
    private List<TeacherEntity> teacher;

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public void setCourse_program(CourseProgramEntity course_program) {
        this.course_program = course_program;
    }

    public void setTeacher(List<TeacherEntity> teacher) {
        this.teacher = teacher;
    }

    public CourseEntity getCourse() {
        return course;
    }

    public CourseProgramEntity getCourse_program() {
        return course_program;
    }

    public List<TeacherEntity> getTeacher() {
        return teacher;
    }

    public static class CourseEntity {
        /**
         * kid : 7018
         * title : 四年级大语文
         * roomId : 0
         * desc : 四年级大语文
         * content : 四年级大语文
         * pic : http://img.zhugexuetang.com/çº¿ä¸è¯¾éå¾-800x450-04.jpg
         * listpic : http://img.zhugexuetang.com/çº¿ä¸è¯¾éå¾-800x450-04.jpg
         * tname : 王邓红、马睿启
         * tid : 35,1024
         * buyPrice : 2050
         * sellPrice : 3250
         * backPlayPrice : 3250
         * addtime : 0
         * updateTime : 0
         * start_at : 2018-03-02 00:00:00
         * end_at : 2018-06-29 00:00:00
         * class_start_at : 15:00:00
         * class_end_at : 17:00:00
         * class_start_at_bak : null
         * startPtime : 1519974000
         * endPtime : 1530262800
         * startBtime : 0
         * endBtime : 0
         * buyNum : 6
         * playNum : 601
         * hit : 0
         * adminId : 0
         * adminName :
         * users : 四年级
         * state : 0
         * sort : 2
         * delete : 0
         * tags : null
         * type : 2
         * subject_name : null
         * type_count : 2
         * term : 1
         * window_isopen : 0
         * remark : null
         * source : 2
         * level : 1
         * service : null
         * model : C18452
         * single_price : 100
         * material_price : 50
         * weekday : 周五
         * school : 大钟寺校区
         * city : 010
         * num : 10
         * online : 0
         * hours : 2
         * discount_amount : 0
         * discount_start_time : 0000-00-00 00:00:00
         * discount_end_time : 0000-00-00 00:00:00
         * stock : 24
         * origin_stock : 30
         * client_channel : null
         * onsale_time : 1980-01-01 00:00:00
         * nianbu : 小学部
         * year : 2018
         * is_close : 0
         * is_upmodel : 0
         * upmodel : Q480
         * upmodelend : 2018-06-29 00:00:00
         * video :
         * appear_time : 1980-01-01 00:00:00
         * disappear_time : 2030-01-01 08:00:00
         * can_refund : 0
         * region : 2001
         * typeName :
         * tagName :
         * templet : tpl_payment_notice
         * templet_id : 0
         * trule_id : 0
         * c_target : null
         * c_trait : null
         * is_discount : 0
         * is_promotion : 0
         * groupBuy : {"id":"1","title":"sfsf","item_id":"7018","discount_price":200,"start_time":"2018-05-15 15:46:17","end_time":"2018-05-24 15:49:00","limit_num":10,"state":1,"created_at":"2018-05-21 15:46:17","updated_at":"2018-05-21 15:49:03","operator":"0"}
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

    public static class TeacherEntity {
        /**
         * tid : 35
         * pic : http://resource.zhugexuetang.com/upload/2015/12/18/5674173eee75d.jpg
         * name : 王邓红
         * t_desc : 毕业于四川师范大学新闻系。大语文5年教龄金牌教师、一对一及游学负责人。从小酷爱语文，小升初、中考、高考语文成绩都名列全市前三。毕业后从报社编辑到走上讲台，我的启蒙老师教导我，老师只是引导，孩子才是课堂主体，所以从教以来，一直非常注重孩子自我思考能力和表达力的培养。孩子的答案，从不直接给予否定，让孩子敢于思考，敢于表达，再进行原则上的引导。每一节课都是孩子和我一起思考和探讨结束的。
         * t_style : 热情、细心、花仙子
         * t_harvest : null
         * t_words : 热情、细心、花仙子
         */

        private String tid;
        private String pic;
        private String name;
        private String t_desc;
        private String t_style;
        private Object t_harvest;
        private String t_words;

        public void setTid(String tid) {
            this.tid = tid;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setT_desc(String t_desc) {
            this.t_desc = t_desc;
        }

        public void setT_style(String t_style) {
            this.t_style = t_style;
        }

        public void setT_harvest(Object t_harvest) {
            this.t_harvest = t_harvest;
        }

        public void setT_words(String t_words) {
            this.t_words = t_words;
        }

        public String getTid() {
            return tid;
        }

        public String getPic() {
            return pic;
        }

        public String getName() {
            return name;
        }

        public String getT_desc() {
            return t_desc;
        }

        public String getT_style() {
            return t_style;
        }

        public Object getT_harvest() {
            return t_harvest;
        }

        public String getT_words() {
            return t_words;
        }
    }
}
