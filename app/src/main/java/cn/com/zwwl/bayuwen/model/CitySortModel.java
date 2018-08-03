package cn.com.zwwl.bayuwen.model;

import java.util.List;

/**
 * lmj  on 2018/6/21
 */
public class CitySortModel extends Entry {

    private List<CityBean> city;
    private List<HotcityBean> hotcity;

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    public List<HotcityBean> getHotcity() {
        return hotcity;
    }

    public void setHotcity(List<HotcityBean> hotcity) {
        this.hotcity = hotcity;
    }

    public static class CityBean {
        /**
         * id : 1
         * name : 北京
         * initial : b
         * initials : bj
         * suffix : 市
         * code : 110000
         * area_code : 010
         */

        private String id;
        private String name;
        private String initial;
        private String initials;
        private String suffix;
        private String code;
        private String area_code;

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

        public String getInitial() {
            return initial;
        }

        public void setInitial(String initial) {
            this.initial = initial;
        }

        public String getInitials() {
            return initials;
        }

        public void setInitials(String initials) {
            this.initials = initials;
        }

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getArea_code() {
            return area_code;
        }

        public void setArea_code(String area_code) {
            this.area_code = area_code;
        }
    }

    public static class HotcityBean {
        /**
         * id : 1
         * name : 北京
         * initial : b
         * initials : bj
         * suffix : 市
         * code : 110000
         * area_code : 010
         */

        private String id;
        private String name;
        private String initial;
        private String initials;
        private String suffix;
        private String code;
        private String area_code;

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

        public String getInitial() {
            return initial;
        }

        public void setInitial(String initial) {
            this.initial = initial;
        }

        public String getInitials() {
            return initials;
        }

        public void setInitials(String initials) {
            this.initials = initials;
        }

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getArea_code() {
            return area_code;
        }

        public void setArea_code(String area_code) {
            this.area_code = area_code;
        }
    }
}
