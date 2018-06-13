package cn.com.zwwl.bayuwen.model;

import java.util.List;

public class BaseResponse{
    private String page;
    private String pagesize;
    private String total;
    private int total_count;
    private List<LessonModel> lectures;
    private List<KeModel>course;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public List<KeModel> getCourse() {
        return course;
    }

    public void setCourse(List<KeModel> course) {
        this.course = course;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<LessonModel> getLectures() {
        return lectures;
    }

    public void setLectures(List<LessonModel> lectures) {
        this.lectures = lectures;
    }
}
