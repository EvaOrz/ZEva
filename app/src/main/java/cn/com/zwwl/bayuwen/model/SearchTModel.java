package cn.com.zwwl.bayuwen.model;

import java.util.List;

/**
 * 全部教师页面刷新用
 */
public class SearchTModel extends Entry {

    private List<TeacherModel> data;
    private int page;
    private int pagesize;
    private int total;

    public List<TeacherModel> getData() {
        return data;
    }

    public void setData(List<TeacherModel> data) {
        this.data = data;
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
}

