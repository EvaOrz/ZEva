package cn.com.zwwl.bayuwen.model;

import java.util.List;

/**
 * 首页第一个fragment数据结构
 */
public class Index1Model extends Entry {

    private CalendarCourseBean calendarCourse;
    private List<AdvBean> adv;
    private List<SelectedCourseBean> selectedCourse;

    public CalendarCourseBean getCalendarCourse() {
        return calendarCourse;
    }

    public void setCalendarCourse(CalendarCourseBean calendarCourse) {
        this.calendarCourse = calendarCourse;
    }

    public List<AdvBean> getAdv() {
        return adv;
    }

    public void setAdv(List<AdvBean> adv) {
        this.adv = adv;
    }

    public List<SelectedCourseBean> getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(List<SelectedCourseBean> selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public static class CalendarCourseBean {


        private String date;
        private List<CoursesBean> courses;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<CoursesBean> getCourses() {
            return courses;
        }

        public void setCourses(List<CoursesBean> courses) {
            this.courses = courses;
        }

        public static class CoursesBean {
            /**
             * title : 【古】孟郊和贾岛
             * start_at : 2018-06-19
             * class_start_at : 13:30:00
             * class_end_at : 15:20:00
             */

            private String title;
            private String start_at;
            private String class_start_at;
            private String class_end_at;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getStart_at() {
                return start_at;
            }

            public void setStart_at(String start_at) {
                this.start_at = start_at;
            }

            public String getClass_start_at() {
                return class_start_at;
            }

            public void setClass_start_at(String class_start_at) {
                this.class_start_at = class_start_at;
            }

            public String getClass_end_at() {
                return class_end_at;
            }

            public void setClass_end_at(String class_end_at) {
                this.class_end_at = class_end_at;
            }
        }
    }

    public static class AdvBean {

        private String parent;
        private String title;
        private String pic;
        private String link;
        private String type;
        private String sort;
        private Object teacher;
        private Object keinfo;

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public Object getTeacher() {
            return teacher;
        }

        public void setTeacher(Object teacher) {
            this.teacher = teacher;
        }

        public Object getKeinfo() {
            return keinfo;
        }

        public void setKeinfo(Object keinfo) {
            this.keinfo = keinfo;
        }
    }

    public static class SelectedCourseBean {

        private String id;
        private String uid;
        private String kid;
        private String student_no;
        private ProductsBean products;
        private PlanBean plan;
        private String goUpCourse;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getKid() {
            return kid;
        }

        public void setKid(String kid) {
            this.kid = kid;
        }

        public String getStudent_no() {
            return student_no;
        }

        public void setStudent_no(String student_no) {
            this.student_no = student_no;
        }

        public ProductsBean getProducts() {
            return products;
        }

        public void setProducts(ProductsBean products) {
            this.products = products;
        }

        public PlanBean getPlan() {
            return plan;
        }

        public void setPlan(PlanBean plan) {
            this.plan = plan;
        }

        public String getGoUpCourse() {
            return goUpCourse;
        }

        public void setGoUpCourse(String goUpCourse) {
            this.goUpCourse = goUpCourse;
        }

        public static class ProductsBean {
            private String kid;
            private String title;
            private String desc;
            private String model;
            private String pic;
            private String users;
            private String listpic;
            private String startPtime;
            private String endPtime;
            private String term;
            private String type;
            private String online;
            private String start_at;
            private String end_at;
            private String class_start_at;
            private String class_end_at;
            private String source;

            public String getKid() {
                return kid;
            }

            public void setKid(String kid) {
                this.kid = kid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getUsers() {
                return users;
            }

            public void setUsers(String users) {
                this.users = users;
            }

            public String getListpic() {
                return listpic;
            }

            public void setListpic(String listpic) {
                this.listpic = listpic;
            }

            public String getStartPtime() {
                return startPtime;
            }

            public void setStartPtime(String startPtime) {
                this.startPtime = startPtime;
            }

            public String getEndPtime() {
                return endPtime;
            }

            public void setEndPtime(String endPtime) {
                this.endPtime = endPtime;
            }

            public String getTerm() {
                return term;
            }

            public void setTerm(String term) {
                this.term = term;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getOnline() {
                return online;
            }

            public void setOnline(String online) {
                this.online = online;
            }

            public String getStart_at() {
                return start_at;
            }

            public void setStart_at(String start_at) {
                this.start_at = start_at;
            }

            public String getEnd_at() {
                return end_at;
            }

            public void setEnd_at(String end_at) {
                this.end_at = end_at;
            }

            public String getClass_start_at() {
                return class_start_at;
            }

            public void setClass_start_at(String class_start_at) {
                this.class_start_at = class_start_at;
            }

            public String getClass_end_at() {
                return class_end_at;
            }

            public void setClass_end_at(String class_end_at) {
                this.class_end_at = class_end_at;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }
        }

        public static class PlanBean {
            /**
             * online : 0
             * source : 2
             * finish : false
             * count : 16
             * current : 0
             * currentLectureId : 0
             * next : 1
             * nextTime : 2018-09-08 10:40:00
             * completeClass : []
             */

            private int online;
            private int source;
            private boolean finish;
            private int count;
            private int current;
            private int currentLectureId;
            private int next;
            private String nextTime;
            private List<?> completeClass;

            public int getOnline() {
                return online;
            }

            public void setOnline(int online) {
                this.online = online;
            }

            public int getSource() {
                return source;
            }

            public void setSource(int source) {
                this.source = source;
            }

            public boolean isFinish() {
                return finish;
            }

            public void setFinish(boolean finish) {
                this.finish = finish;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getCurrent() {
                return current;
            }

            public void setCurrent(int current) {
                this.current = current;
            }

            public int getCurrentLectureId() {
                return currentLectureId;
            }

            public void setCurrentLectureId(int currentLectureId) {
                this.currentLectureId = currentLectureId;
            }

            public int getNext() {
                return next;
            }

            public void setNext(int next) {
                this.next = next;
            }

            public String getNextTime() {
                return nextTime;
            }

            public void setNextTime(String nextTime) {
                this.nextTime = nextTime;
            }

            public List<?> getCompleteClass() {
                return completeClass;
            }

            public void setCompleteClass(List<?> completeClass) {
                this.completeClass = completeClass;
            }
        }
    }
}
