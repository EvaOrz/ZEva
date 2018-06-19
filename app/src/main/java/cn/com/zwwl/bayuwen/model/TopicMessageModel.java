package cn.com.zwwl.bayuwen.model;

public class TopicMessageModel extends Entry{

    /**
     * id : 2
     * uid : 260920
     * name : 测试话题
     * content : 哈哈哈哈哈哈哈哈哈哈哈哈哈
     * course_id : 1
     * is_delete : 0
     * is_anonymous : 0
     * create_at : 2018-06-08 19:02:30
     * update_at : 2018-06-08 19:02:30
     * course : {"id":"1","name":"一年级大语文","url":"","type":"2","create_at":"2018-06-06 11:11:28","update_at":"2018-06-06 11:11:28"}
     * vote_num : 0
     * comment_num : 0
     */

    private String id;
    private String uid;
    private String name;
    private String content;
    private String course_id;
    private String is_delete;
    private String is_anonymous;
    private String create_at;
    private String update_at;
    private CourseBean course;
    private String vote_num;
    private String comment_num;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public String getIs_anonymous() {
        return is_anonymous;
    }

    public void setIs_anonymous(String is_anonymous) {
        this.is_anonymous = is_anonymous;
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

    public CourseBean getCourse() {
        return course;
    }

    public void setCourse(CourseBean course) {
        this.course = course;
    }

    public String getVote_num() {
        return vote_num;
    }

    public void setVote_num(String vote_num) {
        this.vote_num = vote_num;
    }

    public String getComment_num() {
        return comment_num;
    }

    public void setComment_num(String comment_num) {
        this.comment_num = comment_num;
    }

    public static class CourseBean {
        /**
         * id : 1
         * name : 一年级大语文
         * url :
         * type : 2
         * create_at : 2018-06-06 11:11:28
         * update_at : 2018-06-06 11:11:28
         */

        private String id;
        private String name;
        private String url;
        private String type;
        private String create_at;
        private String update_at;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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
    }
}
