package cn.com.zwwl.bayuwen.model;

import java.util.List;

import cn.com.zwwl.bayuwen.model.fm.PinglunModel;

public class EvalListModel extends Entry {
    private int totalCount;
    private int pageCount;
    private int perPage;
    private int currentPage;
    private List<PinglunModel> data;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<PinglunModel> getData() {
        return data;
    }

    public void setData(List<PinglunModel> data) {
        this.data = data;
    }

}
