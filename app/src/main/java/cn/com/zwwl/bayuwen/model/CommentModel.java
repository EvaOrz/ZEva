package cn.com.zwwl.bayuwen.model;

public class CommentModel extends Entry{

    /**
     * student : 这是顾问对学生的评价
     * parent : 这是顾问对家长的评价
     * job : 这是对作业的评价
     */

    private String student;
    private String parent;
    private String job;

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
