package cn.com.zwwl.bayuwen.model;

/**
 * ReportModel
 * Created by zhumangmang at 2018/6/11 17:35
 */
public class ReportModel extends Entry {

    /**
     * keReport : {"comment_id":12,"url":"http://www.zhugexuetang.com?kid=4551&student_no=3021527"}
     * monthReport : {"comment_id":12,"url":"http://www.zhugexuetang.com?year=2018&month=6&student_no=3021527"}
     */

    private ReportBean keReport;
    private ReportBean monthReport;

    public ReportBean getKeReport() {
        return keReport;
    }

    public void setKeReport(ReportBean keReport) {
        this.keReport = keReport;
    }

    public ReportBean getMonthReport() {
        return monthReport;
    }

    public void setMonthReport(ReportBean monthReport) {
        this.monthReport = monthReport;
    }

    public static class ReportBean {
        /**
         * comment_id : 12
         * url : http://www.zhugexuetang.com?kid=4551&student_no=3021527
         */

        private String comment_id;
        private String url;
        private String kid;
        private String month;
        private String year;

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getKid() {
            return kid;
        }

        public void setKid(String kid) {
            this.kid = kid;
        }

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
