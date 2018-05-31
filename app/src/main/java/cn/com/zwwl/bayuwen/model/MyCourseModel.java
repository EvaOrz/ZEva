package cn.com.zwwl.bayuwen.model;

import java.util.List;
/**
 *  我的课程
 *  Created by zhumangmang at 2018/5/30 17:23
 */
public class MyCourseModel extends Entry{
        private List<KeModel> completed;
        private List<UnfinishedBean> unfinished;



    public List<KeModel> getCompleted() {
            return completed;
        }

        public void setCompleted(List<KeModel> completed) {
            this.completed = completed;
        }

        public List<UnfinishedBean> getUnfinished() {
            return unfinished;
        }

        public void setUnfinished(List<UnfinishedBean> unfinished) {
            this.unfinished = unfinished;
        }
        public static class UnfinishedBean {
            /**
             * id : 204516
             * uid : 35432
             * kid : 8391
             * student_no : 3021527
             * products : {"kid":"8391","title":"《窦神归来》第二季","pic":"http://img.zhugexuetang.com/Fr8fdySv1GwtPNxW3IXQxQcsyHNh","users":"三年级、四年级、五年级、六年级、初一、初二、初三、高一、高二、高三","listpic":"http://img.zhugexuetang.com/Fr8fdySv1GwtPNxW3IXQxQcsyHNh","startPtime":"1519559100","endPtime":"1551012300","term":"0","type":"15","online":"1","start_at":"2018-02-25 19:45:00","end_at":"2019-02-24 20:45:00","class_start_at":null,"class_end_at":null}
             * plan : {"online":1,"finish":false,"count":50,"current":13,"currentLectureId":"5943","next":14,"nextTime":"2018-06-03 19:45:00"}
             */

            private String id;
            private String uid;
            private String kid;
            private String student_no;
            private KeModel products;
            private PlanModel plan;
            private CommentModel comments;
            public CommentModel getComments() {
                return comments;
            }

            public void setComments(CommentModel comments) {
                this.comments = comments;
            }
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

            public KeModel getProducts() {
                return products;
            }

            public void setProducts(KeModel products) {
                this.products = products;
            }

            public PlanModel getPlan() {
                return plan;
            }

            public void setPlan(PlanModel plan) {
                this.plan = plan;
            }

            public static class ProductsBean {
                /**
                 * kid : 8391
                 * title : 《窦神归来》第二季
                 * pic : http://img.zhugexuetang.com/Fr8fdySv1GwtPNxW3IXQxQcsyHNh
                 * users : 三年级、四年级、五年级、六年级、初一、初二、初三、高一、高二、高三
                 * listpic : http://img.zhugexuetang.com/Fr8fdySv1GwtPNxW3IXQxQcsyHNh
                 * startPtime : 1519559100
                 * endPtime : 1551012300
                 * term : 0
                 * type : 15
                 * online : 1
                 * start_at : 2018-02-25 19:45:00
                 * end_at : 2019-02-24 20:45:00
                 * class_start_at : null
                 * class_end_at : null
                 */

                private String kid;
                private String title;
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
                private Object class_start_at;
                private Object class_end_at;

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

                public Object getClass_start_at() {
                    return class_start_at;
                }

                public void setClass_start_at(Object class_start_at) {
                    this.class_start_at = class_start_at;
                }

                public Object getClass_end_at() {
                    return class_end_at;
                }

                public void setClass_end_at(Object class_end_at) {
                    this.class_end_at = class_end_at;
                }
            }
        }
}
