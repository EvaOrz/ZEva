package cn.com.zwwl.bayuwen.model;

import java.util.List;

public class UnitDetailModel extends Entry {

    private CommonModel taSummary;
    private PicBean accessory;
    private PicBean blackboard;
    private PicBean courseLive;
    private WorkDetailModel homework;
    private TeachersBean teachers;

    public WorkDetailModel getHomework() {
        return homework;
    }

    public void setHomework(WorkDetailModel homework) {
        this.homework = homework;
    }

    public CommonModel getTaSummary() {
        return taSummary;
    }

    public void setTaSummary(CommonModel taSummary) {
        this.taSummary = taSummary;
    }

    public PicBean getAccessory() {
        return accessory;
    }

    public void setAccessory(PicBean accessory) {
        this.accessory = accessory;
    }

    public PicBean getBlackboard() {
        return blackboard;
    }

    public void setBlackboard(PicBean blackboard) {
        this.blackboard = blackboard;
    }

    public PicBean getCourseLive() {
        return courseLive;
    }

    public void setCourseLive(PicBean courseLive) {
        this.courseLive = courseLive;
    }

    public TeachersBean getTeachers() {
        return teachers;
    }

    public void setTeachers(TeachersBean teachers) {
        this.teachers = teachers;
    }


    public static class PicBean {
        /**
         * data : [{"id":"6113","url":"http://img.zhugexuetang.com/%E5%8F%A4%E6%96%87%E4%BA%8C.jpg"},{"id":"6114","url":"http://img.zhugexuetang.com/FgeeEzlHXmfYpppvuSKaTG1fzhZ8"},{"id":"6115","url":"http://img.zhugexuetang.com/shaoxinyuedu.jpg"}]
         * state : 1
         */

        private int state;
        private List<CommonModel> data;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public List<CommonModel> getData() {
            return data;
        }

        public void setData(List<CommonModel> data) {
            this.data = data;
        }

    }

    public static class TeachersBean {
        /**
         * teacher : {"id":"18","name":"窦昕","state":1}
         * assistant : {"id":"21348","name":"王建新助教","state":1}
         * counselor : {"id":"260932","name":"耿少京顾问","state":1}
         */

        private CommonModel teacher;
        private CommonModel assistant;
        private CommonModel counselor;

        public CommonModel getTeacher() {
            return teacher;
        }

        public void setTeacher(CommonModel teacher) {
            this.teacher = teacher;
        }

        public CommonModel getAssistant() {
            return assistant;
        }

        public void setAssistant(CommonModel assistant) {
            this.assistant = assistant;
        }

        public CommonModel getCounselor() {
            return counselor;
        }

        public void setCounselor(CommonModel counselor) {
            this.counselor = counselor;
        }
    }
}
