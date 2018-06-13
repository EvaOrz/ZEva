package cn.com.zwwl.bayuwen.model;

import java.util.List;

/**
 *  选课搜索页model
 *  Created by zhumangmang at 2018/6/12 19:59
 */
public class SearchModel extends Entry {
    private LinksBean _links;
    private PageModel _meta;
    private List<KeModel> data;

    public LinksBean get_links() {
        return _links;
    }

    public void set_links(LinksBean _links) {
        this._links = _links;
    }

    public PageModel get_meta() {
        return _meta;
    }

    public void set_meta(PageModel _meta) {
        this._meta = _meta;
    }

    public List<KeModel> getData() {
        return data;
    }

    public void setData(List<KeModel> data) {
        this.data = data;
    }

    public static class LinksBean {
        /**
         * self : {"href":"http://www.kaifaapi.com/v2/course/search?weekday=2&users=5&school=11&online=0&page=1"}
         */

        private SelfBean self;

        public SelfBean getSelf() {
            return self;
        }

        public void setSelf(SelfBean self) {
            this.self = self;
        }

        public static class SelfBean {
            /**
             * href : http://www.kaifaapi.com/v2/course/search?weekday=2&users=5&school=11&online=0&page=1
             */

            private String href;

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }
        }
    }
}
