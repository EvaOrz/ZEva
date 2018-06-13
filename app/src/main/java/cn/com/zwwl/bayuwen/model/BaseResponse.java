package cn.com.zwwl.bayuwen.model;

import java.util.List;

public class BaseResponse{
    private int page;
    private int pagesize;
    private int total;
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<LessonModel> getLectures() {
        return lectures;
    }

    public void setLectures(List<LessonModel> lectures) {
        this.lectures = lectures;
    }
}
