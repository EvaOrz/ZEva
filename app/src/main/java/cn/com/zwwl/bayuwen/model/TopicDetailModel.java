package cn.com.zwwl.bayuwen.model;

import java.util.List;

public class TopicDetailModel extends Entry {

    /**
     * user_name : 小破孩眯一会
     * user_relName : 18500048050
     * user_pic : http://img.zhugexuetang.com/FghOGTkP5gWPgz6WDU5T8Jo15ThJ
     * topic_id : 5
     * topic_name : Dhcjajco
     * topic_content : 说吧粗和速度和 i 额外从降温哦经常见到宋家才忙完么丛林常常你你吃捏今晚从进晚餐去玩电玩城摸我摸沉默去完成年初面膜出去哦吃不吃药 v 也 v 我要 v 蔡英文过一次 v
     * topic_create_at : 2018-06-09 13:12:24
     * user_uid : 260920
     * course_name : 一年级语文综合班
     * vote_num : 4
     * is_vote : 1
     * teacher_comment : [{"uid":"5763","content":"哈哈","create_at":"2018-06-12 11:56:49","user_name":"陈思","user_relName":"陈思","user_pic":"http://resource.zhugexuetang.com/upload/2016/08/12/57ad829d4bd1a.jpg"},{"uid":"5763","content":"哈哈2","create_at":"2018-06-12 11:59:12","user_name":"陈思","user_relName":"陈思","user_pic":"http://resource.zhugexuetang.com/upload/2016/08/12/57ad829d4bd1a.jpg"}]
     * user_comment : [{"uid":"260920","content":"测试","create_at":"2018-06-12 11:29:28","user_name":"小破孩眯一会","user_relName":"18500048050","user_pic":"http://img.zhugexuetang.com/FghOGTkP5gWPgz6WDU5T8Jo15ThJ"},{"uid":"260920","content":"测试2","create_at":"2018-06-12 11:42:50","user_name":"小破孩眯一会","user_relName":"18500048050","user_pic":"http://img.zhugexuetang.com/FghOGTkP5gWPgz6WDU5T8Jo15ThJ"}]
     */

    private String user_name;
    private String user_relName;
    private String user_pic;
    private int topic_id;
    private String topic_name;
    private String topic_content;
    private String topic_create_at;
    private int user_uid;
    private String course_name;
    private String vote_num;
    private int is_vote;
    private List<TeacherCommentBean> teacher_comment;
    private List<UserCommentBean> user_comment;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_relName() {
        return user_relName;
    }

    public void setUser_relName(String user_relName) {
        this.user_relName = user_relName;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getTopic_content() {
        return topic_content;
    }

    public void setTopic_content(String topic_content) {
        this.topic_content = topic_content;
    }

    public String getTopic_create_at() {
        return topic_create_at;
    }

    public void setTopic_create_at(String topic_create_at) {
        this.topic_create_at = topic_create_at;
    }

    public int getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(int user_uid) {
        this.user_uid = user_uid;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getVote_num() {
        return vote_num;
    }

    public void setVote_num(String vote_num) {
        this.vote_num = vote_num;
    }

    public int getIs_vote() {
        return is_vote;
    }

    public void setIs_vote(int is_vote) {
        this.is_vote = is_vote;
    }

    public List<TeacherCommentBean> getTeacher_comment() {
        return teacher_comment;
    }

    public void setTeacher_comment(List<TeacherCommentBean> teacher_comment) {
        this.teacher_comment = teacher_comment;
    }

    public List<UserCommentBean> getUser_comment() {
        return user_comment;
    }

    public void setUser_comment(List<UserCommentBean> user_comment) {
        this.user_comment = user_comment;
    }

    public static class TeacherCommentBean {
        /**
         * uid : 5763
         * content : 哈哈
         * create_at : 2018-06-12 11:56:49
         * user_name : 陈思
         * user_relName : 陈思
         * user_pic : http://resource.zhugexuetang.com/upload/2016/08/12/57ad829d4bd1a.jpg
         */

        private String uid;
        private String content;
        private String create_at;
        private String user_name;
        private String user_relName;
        private String user_pic;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_relName() {
            return user_relName;
        }

        public void setUser_relName(String user_relName) {
            this.user_relName = user_relName;
        }

        public String getUser_pic() {
            return user_pic;
        }

        public void setUser_pic(String user_pic) {
            this.user_pic = user_pic;
        }
    }

    public static class UserCommentBean {
        /**
         * uid : 260920
         * content : 测试
         * create_at : 2018-06-12 11:29:28
         * user_name : 小破孩眯一会
         * user_relName : 18500048050
         * user_pic : http://img.zhugexuetang.com/FghOGTkP5gWPgz6WDU5T8Jo15ThJ
         */

        private String uid;
        private String content;
        private String create_at;
        private String user_name;
        private String user_relName;
        private String user_pic;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_relName() {
            return user_relName;
        }

        public void setUser_relName(String user_relName) {
            this.user_relName = user_relName;
        }

        public String getUser_pic() {
            return user_pic;
        }

        public void setUser_pic(String user_pic) {
            this.user_pic = user_pic;
        }
    }
}
