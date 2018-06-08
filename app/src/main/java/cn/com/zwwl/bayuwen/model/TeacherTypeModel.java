package cn.com.zwwl.bayuwen.model;

import java.util.List;

import cn.com.zwwl.bayuwen.view.selectmenu.SelectTempModel;

/**
 * 教师筛选条件model
 */
public class TeacherTypeModel extends Entry {
    private List<KeTypeModel.CourseTypeBean> courseType; // 项目（二级）
    private List<GradesModel> grades;// 年级（二级）


    public List<KeTypeModel.CourseTypeBean> getCourseType() {
        return courseType;
    }

    public void setCourseType(List<KeTypeModel.CourseTypeBean> courseType) {
        this.courseType = courseType;
    }

    public List<GradesModel> getGrades() {
        return grades;
    }

    public void setGrades(List<GradesModel> grades) {
        this.grades = grades;
    }

    public static class GradesModel {
        private String name;
        private List<KeTypeModel.GradesBean> grade;
        private String type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<KeTypeModel.GradesBean> getGrade() {
            return grade;
        }

        public void setGrade(List<KeTypeModel.GradesBean> grade) {
            this.grade = grade;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public SelectTempModel transToS() {
            SelectTempModel selectTempModel = new SelectTempModel();
            selectTempModel.setId(type);
            selectTempModel.setText(name);
            return selectTempModel;
        }
    }

}
