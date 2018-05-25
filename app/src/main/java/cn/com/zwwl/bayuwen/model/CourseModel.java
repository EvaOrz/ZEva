package cn.com.zwwl.bayuwen.model;

import java.util.List;

/**
 * Created by lousx on 2018/5/16.
 */

public class CourseModel extends Entry{

    /**
     * lectures : [{"id":"5931","kid":"8391","title":"第1讲：中国古代神话传说及文化寓意","tid":"18","hours":"3","start_at":"2018-02-25","class_start_at":"19:00:00","class_end_at":"20:45:00","tname":"窦昕"},{"id":"5932","kid":"8391","title":"第2讲：先秦诗歌及中国古代婚恋制度","tid":"18","hours":"3","start_at":"2018-03-04","class_start_at":"19:00:00","class_end_at":"20:45:00","tname":"窦昕"},{"id":"5933","kid":"8391","title":"第3讲：先秦散文及中国古代礼仪制度","tid":"18","hours":"3","start_at":"2018-03-11","class_start_at":"19:00:00","class_end_at":"20:45:00","tname":"窦昕"},{"id":"5934","kid":"8391","title":"第4讲：《论语》及中国古代儒家流派","tid":"18","hours":"3","start_at":"2018-03-18","class_start_at":"19:45:00","class_end_at":"20:45:00","tname":"窦昕"},{"id":"5935","kid":"8391","title":"第5讲：两汉散文及中国古代骈散之争","tid":"18","hours":"3","start_at":"2018-03-25","class_start_at":"19:45:00","class_end_at":"20:45:00","tname":"窦昕"},{"id":"5936","kid":"8391","title":"第6讲：两汉史学及中国古代刑罚制度","tid":"18","hours":"3","start_at":"2018-04-01","class_start_at":"19:45:00","class_end_at":"20:45:00","tname":"窦昕"},{"id":"5937","kid":"8391","title":"第7讲：《巴黎圣母院》及法国大革命","tid":"18","hours":"3","start_at":"2018-04-08","class_start_at":"19:45:00","class_end_at":"20:45:00","tname":"窦昕"},{"id":"5938","kid":"8391","title":"第8讲：《欧也妮·葛朗台》及资本主义崛起","tid":"18","hours":"3","start_at":"2018-04-15","class_start_at":"19:45:00","class_end_at":"20:45:00","tname":"窦昕"},{"id":"5939","kid":"8391","title":"第9讲：两汉辞赋及中国古代建筑艺术","tid":"18","hours":"3","start_at":"2018-04-22","class_start_at":"19:45:00","class_end_at":"20:45:00","tname":"窦昕"},{"id":"5940","kid":"8391","title":"第10讲：两汉乐府诗及中国古代美女","tid":"18","hours":"3","start_at":"2018-05-06","class_start_at":"19:45:00","class_end_at":"20:45:00","tname":"窦昕"}]
     * page : 1
     * pagesize : 10
     * total : 50
     */

    private String page;
    private String pagesize;
    private String total;
    private List<LecturesEntity> lectures;

    public void setPage(String page) {
        this.page = page;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setLectures(List<LecturesEntity> lectures) {
        this.lectures = lectures;
    }

    public String getPage() {
        return page;
    }

    public String getPagesize() {
        return pagesize;
    }

    public String getTotal() {
        return total;
    }

    public List<LecturesEntity> getLectures() {
        return lectures;
    }

    public static class LecturesEntity {
        /**
         * id : 5931
         * kid : 8391
         * title : 第1讲：中国古代神话传说及文化寓意
         * tid : 18
         * hours : 3
         * start_at : 2018-02-25
         * class_start_at : 19:00:00
         * class_end_at : 20:45:00
         * tname : 窦昕
         */

        private String id;
        private String kid;
        private String title;
        private String tid;
        private String hours;
        private String start_at;
        private String class_start_at;
        private String class_end_at;
        private String tname;

        public void setId(String id) {
            this.id = id;
        }

        public void setKid(String kid) {
            this.kid = kid;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public void setHours(String hours) {
            this.hours = hours;
        }

        public void setStart_at(String start_at) {
            this.start_at = start_at;
        }

        public void setClass_start_at(String class_start_at) {
            this.class_start_at = class_start_at;
        }

        public void setClass_end_at(String class_end_at) {
            this.class_end_at = class_end_at;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getId() {
            return id;
        }

        public String getKid() {
            return kid;
        }

        public String getTitle() {
            return title;
        }

        public String getTid() {
            return tid;
        }

        public String getHours() {
            return hours;
        }

        public String getStart_at() {
            return start_at;
        }

        public String getClass_start_at() {
            return class_start_at;
        }

        public String getClass_end_at() {
            return class_end_at;
        }

        public String getTname() {
            return tname;
        }
    }
}
