package cn.com.zwwl.bayuwen.model;

import java.util.List;

/**
 * lmj  on 2018/6/27
 */
public class TeacherDetailStuentevaluateModel extends Entry {


    /**
     * comments : [{"uid":"1202","kid":"29","content":"好好好好好好好好好好好好好好好好好！","ctime":"1453288328","show":"满意","cond":null,"userinfo":{"uid":"1202","name":"韩雨彤","relName":"韩雨彤","pic":null,"userAccount":"734292336@qq.com"}},{"uid":"2751","kid":"29","content":"very good！喜欢这两位老师！","ctime":"1453288325","show":"满意","cond":null,"userinfo":{"uid":"2751","name":"杨千睿","relName":"杨千睿","pic":"http://img.zhugexuetang.com/o_1c8aei8ofa4eki5llh12bb1mnd7.jpg","userAccount":"13520480001"}},{"uid":"1202","kid":"29","content":"love it so much","ctime":"1453288296","show":"满意","cond":null,"userinfo":{"uid":"1202","name":"韩雨彤","relName":"韩雨彤","pic":null,"userAccount":"734292336@qq.com"}}]
     * page : 1
     * pagesize : 20
     * total_count : 3
     */

    private String page;
    private int pagesize;
    private String total_count;
    private List<CommentsBean> comments;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class CommentsBean {
        /**
         * uid : 1202
         * kid : 29
         * content : 好好好好好好好好好好好好好好好好好！
         * ctime : 1453288328
         * show : 满意
         * cond : null
         * userinfo : {"uid":"1202","name":"韩雨彤","relName":"韩雨彤","pic":null,"userAccount":"734292336@qq.com"}
         */

        private String uid;
        private String kid;
        private String content;
        private String ctime;
        private String show;
        private Object cond;
        private UserinfoBean userinfo;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getKid() {
            return kid;
        }

        public void setKid(String kid) {
            this.kid = kid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getShow() {
            return show;
        }

        public void setShow(String show) {
            this.show = show;
        }

        public Object getCond() {
            return cond;
        }

        public void setCond(Object cond) {
            this.cond = cond;
        }

        public UserinfoBean getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(UserinfoBean userinfo) {
            this.userinfo = userinfo;
        }

        public static class UserinfoBean {
            /**
             * uid : 1202
             * name : 韩雨彤
             * relName : 韩雨彤
             * pic : null
             * userAccount : 734292336@qq.com
             */

            private String uid;
            private String name;
            private String relName;
            private Object pic;
            private String userAccount;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRelName() {
                return relName;
            }

            public void setRelName(String relName) {
                this.relName = relName;
            }

            public Object getPic() {
                return pic;
            }

            public void setPic(Object pic) {
                this.pic = pic;
            }

            public String getUserAccount() {
                return userAccount;
            }

            public void setUserAccount(String userAccount) {
                this.userAccount = userAccount;
            }
        }
    }
}
