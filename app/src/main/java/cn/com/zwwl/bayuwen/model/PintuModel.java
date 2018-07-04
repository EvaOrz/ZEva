package cn.com.zwwl.bayuwen.model;

import java.io.Serializable;
import java.util.List;

/**
 * 拼图model
 */
public class PintuModel extends Entry implements Serializable{

    private String id;
    private String name;
    private ContentBean content;
    private CurriculaBean curricula;
    private String aliasname;
    private String url;
    private String type;
    private String grade;
    private String create_at;
    private String update_at;
    private StudentInfoBean student_info;
    private List<LectureinfoBean> lectureinfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public CurriculaBean getCurricula() {
        return curricula;
    }

    public void setCurricula(CurriculaBean curricula) {
        this.curricula = curricula;
    }

    public String getAliasname() {
        return aliasname;
    }

    public void setAliasname(String aliasname) {
        this.aliasname = aliasname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public StudentInfoBean getStudent_info() {
        return student_info;
    }

    public void setStudent_info(StudentInfoBean student_info) {
        this.student_info = student_info;
    }

    public List<LectureinfoBean> getLectureinfo() {
        return lectureinfo;
    }

    public void setLectureinfo(List<LectureinfoBean> lectureinfo) {
        this.lectureinfo = lectureinfo;
    }

    public static class ContentBean implements Serializable{
        /**
         * title : 课程内容
         * content :
         */

        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class CurriculaBean implements Serializable{
        /**
         * title : 体系介绍
         * content :
         */

        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class StudentInfoBean implements Serializable{
        /**
         * title : 学生情况
         * content :
         */

        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class LectureinfoBean implements Serializable{
        private int puzzleId;
        private int questionNum;
        private int rightNum;
        private int errorNum;
        private int totalScore;
        private int studentScore;
        private List<SectionListBean> sectionList;

        public int getPuzzleId() {
            return puzzleId;
        }

        public void setPuzzleId(int puzzleId) {
            this.puzzleId = puzzleId;
        }

        public int getQuestionNum() {
            return questionNum;
        }

        public void setQuestionNum(int questionNum) {
            this.questionNum = questionNum;
        }

        public int getRightNum() {
            return rightNum;
        }

        public void setRightNum(int rightNum) {
            this.rightNum = rightNum;
        }

        public int getErrorNum() {
            return errorNum;
        }

        public void setErrorNum(int errorNum) {
            this.errorNum = errorNum;
        }

        public int getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(int totalScore) {
            this.totalScore = totalScore;
        }

        public int getStudentScore() {
            return studentScore;
        }

        public void setStudentScore(int studentScore) {
            this.studentScore = studentScore;
        }

        public List<SectionListBean> getSectionList() {
            return sectionList;
        }

        public void setSectionList(List<SectionListBean> sectionList) {
            this.sectionList = sectionList;
        }

        public static class SectionListBean implements Serializable{
            /**
             * sectionId : 3133
             * questionNum : 0
             * rightNum : 0
             * errorNum : 0
             * totalScore : 0
             * studentScore : 0
             */

            private int sectionId;
            private int questionNum;
            private int rightNum;
            private int errorNum;
            private int totalScore;
            private int studentScore;

            public int getSectionId() {
                return sectionId;
            }

            public void setSectionId(int sectionId) {
                this.sectionId = sectionId;
            }

            public int getQuestionNum() {
                return questionNum;
            }

            public void setQuestionNum(int questionNum) {
                this.questionNum = questionNum;
            }

            public int getRightNum() {
                return rightNum;
            }

            public void setRightNum(int rightNum) {
                this.rightNum = rightNum;
            }

            public int getErrorNum() {
                return errorNum;
            }

            public void setErrorNum(int errorNum) {
                this.errorNum = errorNum;
            }

            public int getTotalScore() {
                return totalScore;
            }

            public void setTotalScore(int totalScore) {
                this.totalScore = totalScore;
            }

            public int getStudentScore() {
                return studentScore;
            }

            public void setStudentScore(int studentScore) {
                this.studentScore = studentScore;
            }
        }
    }
}
