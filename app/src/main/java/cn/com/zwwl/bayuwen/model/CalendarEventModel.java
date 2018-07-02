package cn.com.zwwl.bayuwen.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 日历页面事件 model
 */
public class CalendarEventModel extends Entry {

    private String id = "";
    private String kid = "";
    private String orgName = "";
    private String outOrgId = "";
    private String courseDate = "";
    private String startTime = "";
    private String endTime = "";
    private String name = "";
    private String school = "";
    private int source;
    private int is_thirdorg;// 0：中文未来 1：第三方
    private int online;
    private List<TeacherBean> teacher = new ArrayList<>();

    // 第三方事件属性
    private String teacherName;
    private int totalWeeks;
    private int totalNumber;
    private String address = "";
    private String code;
    private List<CalendarEventModel> courseDates = new ArrayList<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(String courseDate) {
        this.courseDate = courseDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String getOutOrgId() {
        return outOrgId;
    }

    public void setOutOrgId(String outOrgId) {
        this.outOrgId = outOrgId;
    }

    public int getIs_thirdorg() {
        return is_thirdorg;
    }

    public void setIs_thirdorg(int is_thirdorg) {
        this.is_thirdorg = is_thirdorg;
    }

    public List<TeacherBean> getTeacher() {
        return teacher;
    }

    public void setTeacher(List<TeacherBean> teacher) {
        this.teacher = teacher;
    }

    public List<CalendarEventModel> getCourseDates() {
        return courseDates;
    }

    public void setCourseDates(List<CalendarEventModel> courseDates) {
        this.courseDates = courseDates;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getTotalWeeks() {
        return totalWeeks;
    }

    public void setTotalWeeks(int totalWeeks) {
        this.totalWeeks = totalWeeks;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static class TeacherBean {

        private String tid;
        private String name;
        private String pic;

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }

    public CalendarEventModel parseCalendarEventModel(JSONObject jsonObject, CalendarEventModel
            calendarEventModel) {
        calendarEventModel.setId(jsonObject.optString("id"));
        calendarEventModel.setName(jsonObject.optString("name"));
        calendarEventModel.setOutOrgId(jsonObject.optString("outOrgId"));
        calendarEventModel.setStartTime(jsonObject.optString("startTime"));
        calendarEventModel.setEndTime(jsonObject.optString("endTime"));
        calendarEventModel.setTotalWeeks(jsonObject.optInt("totalWeeks"));
        calendarEventModel.setTotalNumber(jsonObject.optInt("totalNumber"));
        calendarEventModel.setCode(jsonObject.optString("code"));
        calendarEventModel.setTeacherName(jsonObject.optString("teacher"));
        calendarEventModel.setSource(jsonObject.optInt("source"));
        calendarEventModel.setOrgName(jsonObject.optString("orgName"));
        calendarEventModel.setAddress(jsonObject.optString("address"));
        
        JSONArray array = jsonObject.optJSONArray("courseDates");
        List<CalendarEventModel> cs = new ArrayList<>();
        if (!isNull(array)) {
            for (int i = 0; i < array.length(); i++) {
                CalendarEventModel c = new CalendarEventModel();
                JSONObject json = array.optJSONObject(i);
                c.setId(json.optString("id"));
                c.setCourseDate(json.optString("courseDate"));
                cs.add(c);
            }
        }
        calendarEventModel.setCourseDates(cs);
        return calendarEventModel;

    }
}
