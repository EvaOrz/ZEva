package cn.com.zwwl.bayuwen.model;

import java.util.List;

public class BaseResponse{
    private String page;
    private String pagesize;
    private String total;
    private List<LessonModel> lectures;

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
