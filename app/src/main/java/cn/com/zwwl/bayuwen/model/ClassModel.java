package cn.com.zwwl.bayuwen.model;

import java.util.List;

public class ClassModel extends Entry {

    private KeModel course;
    private List<TeacherModel> teacher;
    private PlanModel plan;
    private String midterm_report;
    private String end_term_report;
    private String signInRate;
    private int absenteeism;

    public String getMidterm_report() {
        return midterm_report;
    }

    public void setMidterm_report(String midterm_report) {
        this.midterm_report = midterm_report;
    }

    public String getEnd_term_report() {
        return end_term_report;
    }

    public void setEnd_term_report(String end_term_report) {
        this.end_term_report = end_term_report;
    }

    public String getSignInRate() {
        return signInRate;
    }

    public void setSignInRate(String signInRate) {
        this.signInRate = signInRate;
    }

    public int getAbsenteeism() {
        return absenteeism;
    }

    public void setAbsenteeism(int absenteeism) {
        this.absenteeism = absenteeism;
    }

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
