package cn.com.zwwl.bayuwen.view.selectmenu;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.KeTypeModel.*;
import cn.com.zwwl.bayuwen.model.KeTypeModel.CourseTypeBean.*;
import cn.com.zwwl.bayuwen.model.KeTypeModel.SchoolsBean.*;
import cn.com.zwwl.bayuwen.model.KeTypeModel.SchooltimesBean.*;
import cn.com.zwwl.bayuwen.util.Tools;

/**
 * 筛选器通用model
 */
public class SelectTempModel extends Entry {
    private String id;
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * 解析 项目
     *
     * @param courseType
     * @return
     */
    public static List<List<SelectTempModel>> parseCourse(List<CourseTypeBean> courseType) {
        List<List<SelectTempModel>> ll = new ArrayList<>();
        List<SelectTempModel> l0 = new ArrayList<>();
        for (CourseTypeBean courseTypeBean : courseType) {
            SelectTempModel s = courseTypeBean.transToS();
            l0.add(s);

            if (Tools.listNotNull(courseTypeBean.getType())) {
                List<SelectTempModel> l1 = new ArrayList<>();
                for (TypeBean typeBean : courseTypeBean.getType()) {
                    SelectTempModel s1 = typeBean.transToS();
                    l1.add(s1);
                }
                ll.add(l1);
            }
        }
        ll.add(0, l0);
        return ll;
    }

    /**
     * 解析 类型
     *
     * @param classTypeBeans
     * @return
     */
    public static List<SelectTempModel> parseClass(List<ClassTypeBean> classTypeBeans) {
        List<SelectTempModel> l = new ArrayList<>();
        for (ClassTypeBean classTypeBean : classTypeBeans) {
            SelectTempModel s = classTypeBean.transToS();
            l.add(s);

        }
        return l;
    }

    /**
     * 解析 校区
     *
     * @param schoolsBeans
     * @return
     */
    public static List<List<SelectTempModel>> parseSchool(List<SchoolsBean> schoolsBeans) {
        List<List<SelectTempModel>> ll = new ArrayList<>();
        List<SelectTempModel> l0 = new ArrayList<>();
        for (SchoolsBean schoolsBean : schoolsBeans) {
            DistrictBean districtBean = schoolsBean.getDistrict();
            SelectTempModel s = districtBean.transToS();
            l0.add(s);

            if (Tools.listNotNull(schoolsBean.getList())) {
                List<SelectTempModel> l1 = new ArrayList<>();
                for (ListBean listBean : schoolsBean.getList()) {
                    SelectTempModel s1 = listBean.transToS();
                    l1.add(s1);
                }
                ll.add(l1);
            }
        }
        ll.add(0, l0);
        return ll;
    }

    /**
     * 解析 年级
     *
     * @param gradesBeans
     * @return
     */
    public static List<SelectTempModel> parseGrades(List<GradesBean> gradesBeans) {
        List<SelectTempModel> l = new ArrayList<>();
        for (GradesBean gradesBean : gradesBeans) {
            SelectTempModel s = gradesBean.transToS();
            l.add(s);
        }
        return l;
    }

    /**
     * 解析 时间段
     *
     * @param schooltimesBeans
     * @return
     */
    public static List<List<SelectTempModel>> parseTimes(List<SchooltimesBean> schooltimesBeans) {
        List<List<SelectTempModel>> ll = new ArrayList<>();
        List<SelectTempModel> l0 = new ArrayList<>();
        for (SchooltimesBean schooltimesBean : schooltimesBeans) {
            WeekdayBean weekdayBean = schooltimesBean.getWeekday();
            SelectTempModel s = weekdayBean.transToS();
            l0.add(s);

            if (Tools.listNotNull(schooltimesBean.getTime())) {
                List<SelectTempModel> l1 = new ArrayList<>();
                for (TimeBean timeBean : schooltimesBean.getTime()) {
                    SelectTempModel s1 = timeBean.transToS();
                    l1.add(s1);
                }
                ll.add(l1);
            }

        }
        ll.add(0, l0);
        return ll;
    }
}
