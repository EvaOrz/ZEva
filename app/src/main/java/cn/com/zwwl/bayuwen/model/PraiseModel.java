package cn.com.zwwl.bayuwen.model;

import java.util.List;

/**
 * Created by lousx on 2018/5/23.
 */

public class PraiseModel extends Entry{

    /**
     * teachers : [{"to_uid":"58","vote_num":"2","theme":"教师","to_name":"吕书培","pic":"http://img.zhugexuetang.com/lpUXEPN85IvMwx8cqbUipLxcekgG","t_desc":"<div>低年级孩子的\u201c吕二萌\u201d<\/div><div>强壮的体格，柔软的内心<\/div><div>同期授课人数超200人以上<\/div>"},{"to_uid":"14","vote_num":"2","theme":"教师","to_name":"赵伯奇","pic":"http://resource.zhugexuetang.com/upload/2015/09/25/560526cb2ffd6.jpg","t_desc":"北京大学汉语言文学学士，文学、语言学功底扎实，有数年中小学语文辅导以及定期支教实践经验，能用自身的乐观开朗感染学生，让每一个人发现自己的闪光点，在帮助学生积累知识的同时，注重以身作则地培养其良好行为习惯，引导学生满怀热情的投入到文学的乐土中来。教学成果：1. 从教近五年，大语文体系主要搭建人，学生人次数超过5000人，小学学生大多考入市重点、区重点等理想的中学，并且不少进入重点中学实验班学习。部分也已经考入人大附、北大附、清华附等北京乃至全国最知名的高中。2. 春蕾杯、全国中小学生创新作文大赛等全国性作文赛事优秀辅导教师，所教学生大多获得赛事一二等奖。3. 多次在北京各小学主讲大型文学类国学类讲座，受众超过2000人，广受学生和老师的好评。"},{"to_uid":"18","vote_num":"2","theme":"教师","to_name":"窦昕","pic":"http://resource.zhugexuetang.com/upload/2015/10/13/561c69ab7b32b.jpg","t_desc":"教学经验：首届新浪五星金牌教师、桃李杯北京市优秀教育工作者、北京市海淀区优秀教育工作者。有丰富的播音、主持、表演经验与深厚的古文字、古代文学基础。最关注孩子性格的塑造、习惯的养成，以高引高，以敏导敏。教学成果：大语文创始人，大语文大在古今中外无所不包、大在渊博自信、大在体系庞大而又秩序井然、大在重视内涵气韵十足、大在透过现象直击文化本身、大在透视先贤伟人的灵魂、大在以美学和伟岸的人格勾勒孩子们的一生。孩子们，让我们一同在语文的海洋里遨游吧！"}]
     * stu_advisors : [{"to_uid":"260737","vote_num":"2","theme":"顾问","to_name":"阿依古","pic":null,"t_desc":""}]
     * tutors : [{"to_uid":"260689","vote_num":"1","theme":"助教","to_name":"刘瑞丰","pic":null,"t_desc":""}]
     */

    private List<TeachersEntity> teachers;
    private List<StuAdvisorsEntity> stu_advisors;
    private List<TutorsEntity> tutors;

    public void setTeachers(List<TeachersEntity> teachers) {
        this.teachers = teachers;
    }

    public void setStu_advisors(List<StuAdvisorsEntity> stu_advisors) {
        this.stu_advisors = stu_advisors;
    }

    public void setTutors(List<TutorsEntity> tutors) {
        this.tutors = tutors;
    }

    public List<TeachersEntity> getTeachers() {
        return teachers;
    }

    public List<StuAdvisorsEntity> getStu_advisors() {
        return stu_advisors;
    }

    public List<TutorsEntity> getTutors() {
        return tutors;
    }

    public static class TeachersEntity {
        /**
         * to_uid : 58
         * vote_num : 2
         * theme : 教师
         * to_name : 吕书培
         * pic : http://img.zhugexuetang.com/lpUXEPN85IvMwx8cqbUipLxcekgG
         * t_desc : <div>低年级孩子的“吕二萌”</div><div>强壮的体格，柔软的内心</div><div>同期授课人数超200人以上</div>
         */

        private String to_uid;
        private String vote_num;
        private String theme;
        private String to_name;
        private String pic;
        private String t_desc;

        public void setTo_uid(String to_uid) {
            this.to_uid = to_uid;
        }

        public void setVote_num(String vote_num) {
            this.vote_num = vote_num;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public void setTo_name(String to_name) {
            this.to_name = to_name;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public void setT_desc(String t_desc) {
            this.t_desc = t_desc;
        }

        public String getTo_uid() {
            return to_uid;
        }

        public String getVote_num() {
            return vote_num;
        }

        public String getTheme() {
            return theme;
        }

        public String getTo_name() {
            return to_name;
        }

        public String getPic() {
            return pic;
        }

        public String getT_desc() {
            return t_desc;
        }
    }

    public static class StuAdvisorsEntity {
        /**
         * to_uid : 260737
         * vote_num : 2
         * theme : 顾问
         * to_name : 阿依古
         * pic : null
         * t_desc :
         */

        private String to_uid;
        private String vote_num;
        private String theme;
        private String to_name;
        private String pic;
        private String t_desc;

        public void setTo_uid(String to_uid) {
            this.to_uid = to_uid;
        }

        public void setVote_num(String vote_num) {
            this.vote_num = vote_num;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public void setTo_name(String to_name) {
            this.to_name = to_name;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public void setT_desc(String t_desc) {
            this.t_desc = t_desc;
        }

        public String getTo_uid() {
            return to_uid;
        }

        public String getVote_num() {
            return vote_num;
        }

        public String getTheme() {
            return theme;
        }

        public String getTo_name() {
            return to_name;
        }

        public String getPic() {
            if (pic == null)
                return "";
            return pic;
        }

        public String getT_desc() {
            return t_desc;
        }
    }

    public static class TutorsEntity {
        /**
         * to_uid : 260689
         * vote_num : 1
         * theme : 助教
         * to_name : 刘瑞丰
         * pic : null
         * t_desc :
         */

        private String to_uid;
        private String vote_num;
        private String theme;
        private String to_name;
        private String pic;
        private String t_desc;

        public void setTo_uid(String to_uid) {
            this.to_uid = to_uid;
        }

        public void setVote_num(String vote_num) {
            this.vote_num = vote_num;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public void setTo_name(String to_name) {
            this.to_name = to_name;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public void setT_desc(String t_desc) {
            this.t_desc = t_desc;
        }

        public String getTo_uid() {
            return to_uid;
        }

        public String getVote_num() {
            return vote_num;
        }

        public String getTheme() {
            return theme;
        }

        public String getTo_name() {
            return to_name;
        }

        public String getPic() {
            if (pic == null)
                return "";
            return pic;
        }

        public String getT_desc() {
            return t_desc;
        }
    }
}
