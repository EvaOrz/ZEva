package cn.com.zwwl.bayuwen.model;

import java.util.List;

public class ClassModel extends Entry {

    private KeModel course;
    private List<TeacherModel> teacher;
    private PlanModel plan;

    public NumBean getNum() {
        return num;
    }

    public void setNum(NumBean num) {
        this.num = num;
    }

    private NumBean num;

    public PlanModel getPlan() {
        return plan;
    }

    public void setPlan(PlanModel plan) {
        this.plan = plan;
    }

    public KeModel getCourse() {
        return course;
    }

    public void setCourse(KeModel course) {
        this.course = course;
    }

    public List<TeacherModel> getTeacher() {
        return teacher;
    }

    public void setTeacher(List<TeacherModel> teacher) {
        this.teacher = teacher;
    }

    public static class NumBean {
        private String now_num;
        private String full_num;
        public String getNow_num() {
            return now_num;
        }

        public void setNow_num(String now_num) {
            this.now_num = now_num;
        }

        public String getFull_num() {
            return full_num;
        }

        public void setFull_num(String full_num) {
            this.full_num = full_num;
        }


    }
}
