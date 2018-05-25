package cn.com.zwwl.bayuwen.model;

import java.util.List;

/**
 * Created by lousx on 2018/5/24.
 */

public class TeacherDetailModel extends Entry {

    private TeacherEntity teacher;
    private List<CoursesEntity> courses;

    public void setTeacher(TeacherEntity teacher) {
        this.teacher = teacher;
    }

    public void setCourses(List<CoursesEntity> courses) {
        this.courses = courses;
    }

    public TeacherEntity getTeacher() {
        return teacher;
    }

    public List<CoursesEntity> getCourses() {
        return courses;
    }

    public static class TeacherEntity {
        /**
         * tid : 18
         * name : 窦昕
         * tel : 18001209668
         * pic : http://resource.zhugexuetang.com/upload/2015/10/13/561c69ab7b32b.jpg
         * t_desc : 教学经验：首届新浪五星金牌教师、桃李杯北京市优秀教育工作者、北京市海淀区优秀教育工作者。有丰富的播音、主持、表演经验与深厚的古文字、古代文学基础。最关注孩子性格的塑造、习惯的养成，以高引高，以敏导敏。教学成果：大语文创始人，大语文大在古今中外无所不包、大在渊博自信、大在体系庞大而又秩序井然、大在重视内涵气韵十足、大在透过现象直击文化本身、大在透视先贤伟人的灵魂、大在以美学和伟岸的人格勾勒孩子们的一生。孩子们，让我们一同在语文的海洋里遨游吧！
         * t_shortdesc : 大语文创始人，首届新浪五星金牌教师，讲课博大精深
         * t_style : 窦神、博大精深
         * t_idea : null
         * ke_main : null
         * state : 0
         */

        private String tid = "";
        private String name = "";
        private String tel = "";
        private String pic = "";
        private String t_desc = "";
        private String t_shortdesc = "";
        private String t_style = "";
        private String t_idea = "";
        private String ke_main = "";
        private int state;

        public void setTid(String tid) {
            this.tid = tid;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public void setT_desc(String t_desc) {
            this.t_desc = t_desc;
        }

        public void setT_shortdesc(String t_shortdesc) {
            this.t_shortdesc = t_shortdesc;
        }

        public void setT_style(String t_style) {
            this.t_style = t_style;
        }

        public void setT_idea(String t_idea) {
            this.t_idea = t_idea;
        }

        public void setKe_main(String ke_main) {
            this.ke_main = ke_main;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getTid() {
            return tid;
        }

        public String getName() {
            return name;
        }

        public String getTel() {
            return tel;
        }

        public String getPic() {
            return pic;
        }

        public String getT_desc() {
            return t_desc;
        }

        public String getT_shortdesc() {
            return t_shortdesc;
        }

        public String getT_style() {
            return t_style;
        }

        public String getT_idea() {
            return t_idea;
        }

        public String getKe_main() {
            return ke_main;
        }

        public int getState() {
            return state;
        }
    }

    public static class CoursesEntity {
        /**
         * kid : 10590
         * title : 2017盛夏
         * tid : 18
         * tname : 窦昕
         * model : FM_10590
         * school :
         * buyPrice : 0
         * start_at : 1980-01-01 00:00:00
         * end_at : 1980-01-01 00:00:00
         * class_start_at : null
         * class_end_at : null
         * listpic : http://img.zhugexuetang.com/ios-template-1024a.png
         */

        private String kid = "";
        private String title = "";
        private String tid = "";
        private String tname = "";
        private String model = "";
        private String school = "";
        private String buyPrice = "";
        private String start_at = "";
        private String end_at = "";
        private String class_start_at = "";
        private String class_end_at = "";
        private String listpic = "";

        public void setKid(String kid) {
            this.kid = kid;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public void setBuyPrice(String buyPrice) {
            this.buyPrice = buyPrice;
        }

        public void setStart_at(String start_at) {
            this.start_at = start_at;
        }

        public void setEnd_at(String end_at) {
            this.end_at = end_at;
        }

        public void setClass_start_at(String class_start_at) {
            this.class_start_at = class_start_at;
        }

        public void setClass_end_at(String class_end_at) {
            this.class_end_at = class_end_at;
        }

        public void setListpic(String listpic) {
            this.listpic = listpic;
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

        public String getTname() {
            return tname;
        }

        public String getModel() {
            return model;
        }

        public String getSchool() {
            return school;
        }

        public String getBuyPrice() {
            return buyPrice;
        }

        public String getStart_at() {
            return start_at;
        }

        public String getEnd_at() {
            return end_at;
        }

        public String getClass_start_at() {
            return class_start_at;
        }

        public String getClass_end_at() {
            return class_end_at;
        }

        public String getListpic() {
            return listpic;
        }
    }
}
