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
        private List<OptionModel> choice;
        private List<OptionModel> mchoice;

        public List<OptionModel> getChoice() {
            return choice;
        }

        public void setChoice(List<OptionModel> choice) {
            this.choice = choice;
        }

        public List<OptionModel> getMchoice() {
            return mchoice;
        }

        public void setMchoice(List<OptionModel> mchoice) {
            this.mchoice = mchoice;
        }

    }
}
