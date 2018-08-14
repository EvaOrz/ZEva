package cn.com.zwwl.bayuwen.model;

import java.util.List;

/**
 * 新版选课
 */
public class EcModel extends Entry {
    private List<SubjectsModel> subjects;
    private List<MidIntroModel> midIntro;
    private List<LastIntromodel> lastIntro;

    public List<SubjectsModel> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectsModel> subjects) {
        this.subjects = subjects;
    }

    public List<MidIntroModel> getMidIntro() {
        return midIntro;
    }

    public void setMidIntro(List<MidIntroModel> midIntro) {
        this.midIntro = midIntro;
    }

    public List<LastIntromodel> getLastIntro() {
        return lastIntro;
    }

    public void setLastIntro(List<LastIntromodel> lastIntro) {
        this.lastIntro = lastIntro;
    }


}
