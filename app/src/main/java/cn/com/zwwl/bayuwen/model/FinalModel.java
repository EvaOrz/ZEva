package cn.com.zwwl.bayuwen.model;

import java.util.List;

/**
 * 期末考试详情
 * Created by zhumangmang at 2018/6/2 14:19
 */
public class FinalModel extends Entry {

    /**
     * exam : [{"id":"9","tid":"3606","kid":"209","student_no":"3021527","url":"http://img.zhugexuetang.com//upload/2017/09/26/59c9f7f97e194.jpg","create_at":"2018-05-31 13:27:54","update_at":"2018-05-29 15:34:03","state":"1"},{"id":"10","tid":"3606","kid":"209","student_no":"3021527","url":"http://img.zhugexuetang.com//upload/2017/09/26/59c9f7f97e194.jpg","create_at":"2018-05-31 13:27:50","update_at":"2018-05-29 15:37:41","state":"1"}]
     * teachers : {"content":"努力认真","state":1}
     * tutors : {"content":"加油，继续努力","state":1}
     * stu_advisors : {"content":"不错","state":1}
     */

    private CommonModel teachers;
    private CommonModel tutors;
    private CommonModel stu_advisors;
    private List<CommonModel> exam;

    public CommonModel getTeachers() {
        return teachers;
    }

    public void setTeachers(CommonModel teachers) {
        this.teachers = teachers;
    }

    public CommonModel getTutors() {
        return tutors;
    }

    public void setTutors(CommonModel tutors) {
        this.tutors = tutors;
    }

    public CommonModel getStu_advisors() {
        return stu_advisors;
    }

    public void setStu_advisors(CommonModel stu_advisors) {
        this.stu_advisors = stu_advisors;
    }

    public List<CommonModel> getExam() {
        return exam;
    }

    public void setExam(List<CommonModel> exam) {
        this.exam = exam;
    }
}
