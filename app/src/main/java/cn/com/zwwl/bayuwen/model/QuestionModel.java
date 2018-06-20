package cn.com.zwwl.bayuwen.model;

import java.util.List;

public class QuestionModel extends Entry {

    private int puzzleId;
    private String sectionName;
    private QuestionBean question;

    public int getPuzzleId() {
        return puzzleId;
    }

    public void setPuzzleId(int puzzleId) {
        this.puzzleId = puzzleId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public QuestionBean getQuestion() {
        return question;
    }

    public void setQuestion(QuestionBean question) {
        this.question = question;
    }

    public static class QuestionBean {
        private List<ChoiceBean> choice;
        private List<ChoiceBean> mchoice;

        public List<ChoiceBean> getChoice() {
            return choice;
        }

        public void setChoice(List<ChoiceBean> choice) {
            this.choice = choice;
        }

        public List<ChoiceBean> getMchoice() {
            return mchoice;
        }

        public void setMchoice(List<ChoiceBean> mchoice) {
            this.mchoice = mchoice;
        }

        public static class ChoiceBean {
            /**
             * id : 214
             * sort : 3
             * score : 1
             * title : <p>sasdasdfasdfasdfasdfasdf</p>
             * answer : A,B,E
             * remark : <p>777asdf2asdfasdf</p>
             * select : [{"option":"A","content":"<p>111asdf<\/p>"},{"option":"B","content":"<p>222asdf<\/p>"},{"option":"C","content":"<p>3332asdf<\/p>"},{"option":"D","content":"<p>444asdf<\/p>"}]
             * code : D21000042
             * level : 3
             * ability : 理解,文言文阅读,现代文阅读,西方文学阅读,数学推理
             * point : 元曲
             * reason : 偏旁混淆
             */

            private int id;
            private int sort;
            private int score;
            private String title;
            private String answer;
            private String remark;
            private String code;
            private int level;
            private String ability;
            private String point;
            private String reason;
            private String choseTime;
            private List<CommonModel> select;

            public String getChoseTime() {
                return choseTime;
            }

            public void setChoseTime(String choseTime) {
                this.choseTime = choseTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
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

            public List<CommonModel> getSelect() {
                return select;
            }

            public void setSelect(List<CommonModel> select) {
                this.select = select;
            }
        }
    }
}
