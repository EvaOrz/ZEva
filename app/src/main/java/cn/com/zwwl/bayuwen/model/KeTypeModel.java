package cn.com.zwwl.bayuwen.model;

import java.util.List;

import cn.com.zwwl.bayuwen.view.selectmenu.SelectTempModel;

/**
 * 选课类型model
 */
public class KeTypeModel extends Entry {
    private List<CourseTypeBean> courseType; // 项目（二级）
    private List<ClassTypeBean> classType;// 类型（一级）
    private List<SchoolsBean> schools;// 校区（二级）
    private List<GradesBean> grades;// 年级（一级）
    private List<SchooltimesBean> schooltimes; // 筛选（二级）

    public List<CourseTypeBean> getCourseType() {
        return courseType;
    }

    public void setCourseType(List<CourseTypeBean> courseType) {
        this.courseType = courseType;
    }

    public List<ClassTypeBean> getClassType() {
        return classType;
    }

    public void setClassType(List<ClassTypeBean> classType) {
        this.classType = classType;
    }

    public List<SchoolsBean> getSchools() {
        return schools;
    }

    public void setSchools(List<SchoolsBean> schools) {
        this.schools = schools;
    }

    public List<GradesBean> getGrades() {
        return grades;
    }

    public void setGrades(List<GradesBean> grades) {
        this.grades = grades;
    }

    public List<SchooltimesBean> getSchooltimes() {
        return schooltimes;
    }

    public void setSchooltimes(List<SchooltimesBean> schooltimes) {
        this.schooltimes = schooltimes;
    }

    public static class CourseTypeBean {
        /**
         * id : 0
         * name : 全部
         * remark : null
         * type : [{"id":"","name":"","subject_id":"","sort":"","status":"","type":"",
         * "create_at":"","img":null}]
         */

        private String id;
        private String name;
        private Object remark;
        private List<TypeBean> type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public List<TypeBean> getType() {
            return type;
        }

        public void setType(List<TypeBean> type) {
            this.type = type;
        }

        public SelectTempModel transToS() {
            SelectTempModel selectTempModel = new SelectTempModel();
            selectTempModel.setId(id);
            selectTempModel.setText(name);
            return selectTempModel;
        }

        public static class TypeBean {
            /**
             * id :
             * name :
             * subject_id :
             * sort :
             * status :
             * type :
             * create_at :
             * img : null
             */

            private String id;
            private String name;
            private String subject_id;
            private String sort;
            private String status;
            private String type;
            private String create_at;
            private Object img;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSubject_id() {
                return subject_id;
            }

            public void setSubject_id(String subject_id) {
                this.subject_id = subject_id;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getCreate_at() {
                return create_at;
            }

            public void setCreate_at(String create_at) {
                this.create_at = create_at;
            }

            public Object getImg() {
                return img;
            }

            public void setImg(Object img) {
                this.img = img;
            }

            public SelectTempModel transToS() {
                SelectTempModel selectTempModel = new SelectTempModel();
                selectTempModel.setId(id);
                selectTempModel.setText(name);
                return selectTempModel;
            }
        }
    }

    public static class ClassTypeBean {
        /**
         * name : 全部
         * value : 2
         */

        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public SelectTempModel transToS() {
            SelectTempModel selectTempModel = new SelectTempModel();
            selectTempModel.setId(value);
            selectTempModel.setText(name);
            return selectTempModel;
        }
    }

    public static class SchoolsBean {
        /**
         * district : {"province":"全部","city":"全部","area":"全部"}
         * list : [{"id":"","name":"","address":"","manager":"","phone":"","lng":"","lat":"",
         * "shortName":""}]
         */

        private DistrictBean district;
        private List<ListBean> list;

        public DistrictBean getDistrict() {
            return district;
        }

        public void setDistrict(DistrictBean district) {
            this.district = district;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }


        public static class DistrictBean {
            /**
             * province : 全部
             * city : 全部
             * area : 全部
             */

            private String province;
            private String city;
            private String area;

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public SelectTempModel transToS() {
                SelectTempModel selectTempModel = new SelectTempModel();
                selectTempModel.setId("");
                selectTempModel.setText(area);
                return selectTempModel;
            }

        }

        public static class ListBean {
            /**
             * id :
             * name :
             * address :
             * manager :
             * phone :
             * lng :
             * lat :
             * shortName :
             */

            private String id;
            private String name;
            private String address;
            private String manager;
            private String phone;
            private String lng;
            private String lat;
            private String shortName;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getManager() {
                return manager;
            }

            public void setManager(String manager) {
                this.manager = manager;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getShortName() {
                return shortName;
            }

            public void setShortName(String shortName) {
                this.shortName = shortName;
            }

            public SelectTempModel transToS() {
                SelectTempModel selectTempModel = new SelectTempModel();
                selectTempModel.setId(id);
                selectTempModel.setText(name);
                return selectTempModel;
            }
        }
    }

    public static class GradesBean {
        /**
         * id : 0
         * grade : 全部
         * type :
         */

        private String id;
        private String grade;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public SelectTempModel transToS() {
            SelectTempModel selectTempModel = new SelectTempModel();
            selectTempModel.setId(id);
            selectTempModel.setText(grade);
            return selectTempModel;
        }
    }

    public static class SchooltimesBean {
        /**
         * weekday : {"name":"全部","value":"0"}
         * time : [{"date":"","content":""},{"date":"","content":""},{"date":"","content":""}]
         */

        private WeekdayBean weekday;
        private List<TimeBean> time;

        public WeekdayBean getWeekday() {
            return weekday;
        }

        public void setWeekday(WeekdayBean weekday) {
            this.weekday = weekday;
        }

        public List<TimeBean> getTime() {
            return time;
        }

        public void setTime(List<TimeBean> time) {
            this.time = time;
        }


        public static class WeekdayBean {
            /**
             * name : 全部
             * value : 0
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public SelectTempModel transToS() {
                SelectTempModel selectTempModel = new SelectTempModel();
                selectTempModel.setId(value);
                selectTempModel.setText(name);
                return selectTempModel;
            }
        }

        public static class TimeBean {
            /**
             * date :
             * content :
             */

            private String date;
            private String content;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public SelectTempModel transToS() {
                SelectTempModel selectTempModel = new SelectTempModel();
                selectTempModel.setId(date);
                selectTempModel.setText(content);
                return selectTempModel;
            }
        }
    }
}
