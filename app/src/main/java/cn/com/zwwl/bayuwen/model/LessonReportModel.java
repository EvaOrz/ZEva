package cn.com.zwwl.bayuwen.model;

public class LessonReportModel {

    /**
     * report_name : 第1课：《<世说新语>两则》
     * kid : 209
     * lecture_id : 2107
     * type : 1
     * url : http://www.kaifaapi.com/v2/report?uid=3606&student_no=1201597&type=1&kid=209&lecture_id=2107
     */

    private String report_name;
    private String kid;
    private String lecture_id;
    private int type;
    private String url;

    public String getReport_name() {
        return report_name;
    }

    public void setReport_name(String report_name) {
        this.report_name = report_name;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getLecture_id() {
        return lecture_id;
    }

    public void setLecture_id(String lecture_id) {
        this.lecture_id = lecture_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
