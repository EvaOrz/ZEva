package cn.com.zwwl.bayuwen.model;

import java.util.List;

public class MessageModel extends Entry{


    /**
     * total : 10
     * totalPages : 1
     * currentPage : 1
     * pageSize : 10
     * isFirstPage : true
     * isLastPage : true
     * hasPreviousPage : false
     * hasNextPage : false
     * list : [{"id":148,"messageId":33,"title":"hah","content":"ccc","url":"","isRead":false,"type":"OTHER","extras":"","createdTime":"2018-06-02 17:19"},{"id":147,"messageId":15,"title":"标题","content":"内容","url":"","isRead":false,"type":"OTHER","extras":"","createdTime":"2018-05-30 17:41"},{"id":146,"messageId":14,"title":"标题","content":"内容","url":"","isRead":false,"type":"OTHER","extras":"","createdTime":"2018-05-30 16:43"},{"id":145,"messageId":13,"title":"标题","content":"内容","url":"","isRead":false,"type":"OTHER","extras":"","createdTime":"2018-05-30 16:42"},{"id":144,"messageId":12,"title":"标题","content":"内容","url":"","isRead":false,"type":"OTHER","extras":"","createdTime":"2018-05-30 16:20"},{"id":143,"messageId":12,"title":"标题","content":"内容","url":"","isRead":false,"type":"OTHER","extras":"","createdTime":"2018-05-30 16:20"},{"id":142,"messageId":12,"title":"标题","content":"内容","url":"","isRead":false,"type":"OTHER","extras":"","createdTime":"2018-05-30 16:20"},{"id":141,"messageId":11,"title":"标题","content":"内容","url":"","isRead":false,"type":"OTHER","extras":"","createdTime":"2018-05-30 16:19"},{"id":140,"messageId":10,"title":"标题","content":"内容","url":"","isRead":false,"type":"INTERACTIVE","extras":"","createdTime":"2018-05-30 16:16"},{"id":139,"messageId":4,"title":"标题","content":"内容","url":"","isRead":false,"type":"INTERACTIVE","extras":"","createdTime":"2018-05-29 17:58"}]
     */

    private int total;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 148
         * messageId : 33
         * title : hah
         * content : ccc
         * url :
         * isRead : false
         * type : OTHER
         * extras :
         * createdTime : 2018-06-02 17:19
         */

        private int id;
        private int messageId;
        private String title;
        private String content;
        private String url;
        private boolean isRead;
        private String type;
        private String extras;
        private String createdTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMessageId() {
            return messageId;
        }

        public void setMessageId(int messageId) {
            this.messageId = messageId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isIsRead() {
            return isRead;
        }

        public void setIsRead(boolean isRead) {
            this.isRead = isRead;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getExtras() {
            return extras;
        }

        public void setExtras(String extras) {
            this.extras = extras;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }
    }
}
