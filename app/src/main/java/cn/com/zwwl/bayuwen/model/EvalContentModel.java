package cn.com.zwwl.bayuwen.model;

import java.util.List;
/**
 *  月度、课程评价内容
 *  Created by zhumangmang at 2018/6/9 13:30
 */
public class EvalContentModel extends Entry {

    /**
     * show : {"title":"老师上课是否活跃3","data":[{"name":"活跃","value":0},{"name":"沉闷","value":1}]}
     * score_rule : [{"score":"0","title":"","cond":[{"name":"","value":0},{"name":"","value":0},{"name":"","value":0},{"name":"","value":0}]},{"score":"2","title":"努力2","cond":[{"name":"上课需要更幽默","value":1},{"name":"备课需要更充分","value":2},{"name":"上课需要提前到","value":3},{"name":"上课需要有板书","value":4}]},{"score":"4","title":"努力4","cond":[{"name":"上课需要更幽默","value":1},{"name":"备课需要更充分","value":2},{"name":"上课需要提前到","value":3},{"name":"上课需要有板书","value":4}]},{"score":"6","title":"努力6","cond":[{"name":"上课需要更幽默","value":1},{"name":"备课需要更充分","value":2},{"name":"上课需要提前到","value":3},{"name":"上课需要有板书","value":4}]},{"score":"8","title":"努力8","cond":[{"name":"上课需要更幽默","value":1},{"name":"备课需要更充分","value":2},{"name":"上课需要提前到","value":3},{"name":"上课需要有板书","value":4}]},{"score":"10","title":"努力10","cond":[{"name":"上课需要更幽默","value":1},{"name":"备课需要更充分","value":2},{"name":"上课需要提前到","value":3},{"name":"上课需要有板书","value":4}]}]
     * teacher : [{"uid":"85","name":"陈恒舒"}]
     * tutors : [{"uid":"","name":""}]
     * stu_advisors : [{"uid":"","name":""}]
     */

    private ScoreRuleBean show;
    private List<ScoreRuleBean> score_rule;
    private List<DataBean> teacher;
    private List<DataBean> tutors;
    private List<DataBean> stu_advisors;

    public ScoreRuleBean getShow() {
        return show;
    }

    public void setShow(ScoreRuleBean show) {
        this.show = show;
    }

    public List<ScoreRuleBean> getScore_rule() {
        return score_rule;
    }

    public void setScore_rule(List<ScoreRuleBean> score_rule) {
        this.score_rule = score_rule;
    }

    public List<DataBean> getTeacher() {
        return teacher;
    }

    public void setTeacher(List<DataBean> teacher) {
        this.teacher = teacher;
    }

    public List<DataBean> getTutors() {
        return tutors;
    }

    public void setTutors(List<DataBean> tutors) {
        this.tutors = tutors;
    }

    public List<DataBean> getStu_advisors() {
        return stu_advisors;
    }

    public void setStu_advisors(List<DataBean> stu_advisors) {
        this.stu_advisors = stu_advisors;
    }

    public static class DataBean {
        /**
         * name : 活跃
         * value : 0
         */

        private String name;
        private int value;
        private String uid;

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

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
    public static class ScoreRuleBean {


        private String score;
        private String title;
        private List<DataBean> cond;
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<DataBean> getCond() {
            return cond;
        }

        public void setCond(List<DataBean> cond) {
            this.cond = cond;
        }
    }
}
