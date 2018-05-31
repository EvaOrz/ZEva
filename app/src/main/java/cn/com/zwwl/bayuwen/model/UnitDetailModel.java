package cn.com.zwwl.bayuwen.model;

import java.util.List;

public class UnitDetailModel extends Entry {

    /**
     * taSummary : {"content":"hhhhhfdsfsdfds","state":1}
     * accessory : {"data":[{"id":"6113","url":"http://img.zhugexuetang.com/%E5%8F%A4%E6%96%87%E4%BA%8C.jpg"},{"id":"6114","url":"http://img.zhugexuetang.com/FgeeEzlHXmfYpppvuSKaTG1fzhZ8"},{"id":"6115","url":"http://img.zhugexuetang.com/shaoxinyuedu.jpg"}],"state":1}
     * blackboard : {"data":[{"id":"6118","url":"http://img.zhugexuetang.com/%E5%8F%A4%E6%96%87%E4%BA%8C.jpg"},{"id":"6119","url":"http://img.zhugexuetang.com/20170714160925.jpg"}],"state":1}
     * courseLive : {"data":[{"id":"6116","url":"http://img.zhugexuetang.com/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20171213100111.jpg"},{"id":"6117","url":"http://img.zhugexuetang.com/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20180411093048.jpg"}],"state":1}
     * job : {"data":[],"state":0}
     * teachers : {"teacher":{"id":"18","name":"窦昕","state":1},"assistant":{"id":"21348","name":"王建新助教","state":1},"counselor":{"id":"260932","name":"耿少京顾问","state":1}}
     */

    private CommonModel taSummary;
    private PicBean accessory;
    private PicBean blackboard;
    private PicBean courseLive;
    private PicBean job;
    private TeachersBean teachers;

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

    public PicBean getJob() {
        return job;
    }

    public void setJob(PicBean job) {
        this.job = job;
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
