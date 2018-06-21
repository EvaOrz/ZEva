package cn.com.zwwl.bayuwen.model;

import java.util.List;

public class AnswerModel extends Entry {

    /**
     * puzzleId : 246
     * sectionId : 165
     * questionId : 60
     * ability :
     * point :
     * reason :
     * remark :
     * answer : A
     * studentAnswer : B
     * score : 10
     * studentScore : 0
     * questionLevel : 1
     * questionType : 1
     * select : [{"option":"A","content":"王之涣"},{"option":"B","content":"王翰"},{"option":"C","content":"王昌龄"},{"option":"D","content":"高适"}]
     */

    private int puzzleId;
    private int sectionId;
    private int questionId;
    private String ability;
    private String point;
    private String reason;
    private String remark;
    private String answer;
    private String studentAnswer;
    private String title;
    private int score;
    private int studentScore;
    private int questionLevel;
    private int questionType;
    private List<SelectBean> select;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPuzzleId() {
        return puzzleId;
    }

    public void setPuzzleId(int puzzleId) {
        this.puzzleId = puzzleId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(int studentScore) {
        this.studentScore = studentScore;
    }

    public int getQuestionLevel() {
        return questionLevel;
    }

    public void setQuestionLevel(int questionLevel) {
        this.questionLevel = questionLevel;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public List<SelectBean> getSelect() {
        return select;
    }

    public void setSelect(List<SelectBean> select) {
        this.select = select;
    }

    public static class SelectBean {
        /**
         * option : A
         * content : 王之涣
         */

        private String option;
        private String content;

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
