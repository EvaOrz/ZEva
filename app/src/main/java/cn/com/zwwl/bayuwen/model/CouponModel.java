package cn.com.zwwl.bayuwen.model;

/**
 * 优惠券model
 */
public class CouponModel extends Entry {

    private String id;
    private int type;
    private Object price;
    private String name;
    private int limit;
    private int grant;
    private int superpose;
    private int state;
    private int isdisplay;
    private ConditionBean condition;
    private ReduceBean reduce;
    private String start_use_time;
    private String end_use_time;
    private String appear_time;
    private String disappear_time;
    private String create_at;
    private String update_at;
    private int expires;
    private String summary;
    private int use_scope;
    private int coupons_type;
    private int operate_user;
    private String coupon_code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getGrant() {
        return grant;
    }

    public void setGrant(int grant) {
        this.grant = grant;
    }

    public int getSuperpose() {
        return superpose;
    }

    public void setSuperpose(int superpose) {
        this.superpose = superpose;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getIsdisplay() {
        return isdisplay;
    }

    public void setIsdisplay(int isdisplay) {
        this.isdisplay = isdisplay;
    }

    public ConditionBean getCondition() {
        return condition;
    }

    public void setCondition(ConditionBean condition) {
        this.condition = condition;
    }

    public ReduceBean getReduce() {
        return reduce;
    }

    public void setReduce(ReduceBean reduce) {
        this.reduce = reduce;
    }

    public String getStart_use_time() {
        return start_use_time;
    }

    public void setStart_use_time(String start_use_time) {
        this.start_use_time = start_use_time;
    }

    public String getEnd_use_time() {
        return end_use_time;
    }

    public void setEnd_use_time(String end_use_time) {
        this.end_use_time = end_use_time;
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

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getUse_scope() {
        return use_scope;
    }

    public void setUse_scope(int use_scope) {
        this.use_scope = use_scope;
    }

    public int getCoupons_type() {
        return coupons_type;
    }

    public void setCoupons_type(int coupons_type) {
        this.coupons_type = coupons_type;
    }

    public int getOperate_user() {
        return operate_user;
    }

    public void setOperate_user(int operate_user) {
        this.operate_user = operate_user;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public static class ConditionBean {

        private AmountBean amount;
        private UserBean user;
        private CourseBean course;
        private AreaBean area;

        public AmountBean getAmount() {
            return amount;
        }

        public void setAmount(AmountBean amount) {
            this.amount = amount;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public CourseBean getCourse() {
            return course;
        }

        public void setCourse(CourseBean course) {
            this.course = course;
        }

        public AreaBean getArea() {
            return area;
        }

        public void setArea(AreaBean area) {
            this.area = area;
        }

        public static class AmountBean {
            /**
             * order : 0
             * total : 0
             */

            private int order;
            private int total;

            public int getOrder() {
                return order;
            }

            public void setOrder(int order) {
                this.order = order;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }
        }

        public static class UserBean {
            /**
             * level : all
             * type : all
             * uid :
             */

            private String level;
            private String type;
            private String uid;

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }
        }

        public static class CourseBean {
            /**
             * subject : all
             * type : all
             * grade : all
             * except :
             * include :
             * teacher : all
             * region :
             */

            private String subject;
            private String type;
            private String grade;
            private String except;
            private String include;
            private String teacher;
            private String region;

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getGrade() {
                return grade;
            }

            public void setGrade(String grade) {
                this.grade = grade;
            }

            public String getExcept() {
                return except;
            }

            public void setExcept(String except) {
                this.except = except;
            }

            public String getInclude() {
                return include;
            }

            public void setInclude(String include) {
                this.include = include;
            }

            public String getTeacher() {
                return teacher;
            }

            public void setTeacher(String teacher) {
                this.teacher = teacher;
            }

            public String getRegion() {
                return region;
            }

            public void setRegion(String region) {
                this.region = region;
            }
        }

        public static class AreaBean {
            /**
             * areas : all
             * except :
             */

            private String areas;
            private String except;

            public String getAreas() {
                return areas;
            }

            public void setAreas(String areas) {
                this.areas = areas;
            }

            public String getExcept() {
                return except;
            }

            public void setExcept(String except) {
                this.except = except;
            }
        }
    }

    public static class ReduceBean {
        /**
         * unit : 1
         * discount : 0
         * amount : 0
         * giving : 0
         * decrease : 200
         */

        private int unit;
        private double discount;
        private int amount;
        private int giving;
        private double decrease;

        public int getUnit() {
            return unit;
        }

        public void setUnit(int unit) {
            this.unit = unit;
        }

        public double getDiscount() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount = discount;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getGiving() {
            return giving;
        }

        public void setGiving(int giving) {
            this.giving = giving;
        }

        public double getDecrease() {
            return decrease;
        }

        public void setDecrease(double decrease) {
            this.decrease = decrease;
        }
    }
}
