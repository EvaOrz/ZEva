package cn.com.zwwl.bayuwen.model;

import org.json.JSONObject;

import java.util.List;

/**
 * 首页第二个fragment数据结构
 */
public class Index2Model extends Entry {
    private TagCourseModel partOne;
    private TagCourseModel partTwo;
    private TagCourseModel partThree;

    public TagCourseModel getPartOne() {
        return partOne;
    }

    public void setPartOne(TagCourseModel partOne) {
        this.partOne = partOne;
    }

    public TagCourseModel getPartTwo() {
        return partTwo;
    }

    public void setPartTwo(TagCourseModel partTwo) {
        this.partTwo = partTwo;
    }

    public TagCourseModel getPartThree() {
        return partThree;
    }

    public void setPartThree(TagCourseModel partThree) {
        this.partThree = partThree;
    }

    /**
     * 选课首页类别model
     */
    public static class TagCourseModel extends Entry {

        private ClassifyBean classify;
        private VideoBean video;

        public ClassifyBean getClassify() {
            return classify;
        }

        public void setClassify(ClassifyBean classify) {
            this.classify = classify;
        }

        public VideoBean getVideo() {
            return video;
        }

        public void setVideo(VideoBean video) {
            this.video = video;
        }

        public static class ClassifyBean {

            private String title;
            private List<DetailsBean> details;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<DetailsBean> getDetails() {
                return details;
            }

            public void setDetails(List<DetailsBean> details) {
                this.details = details;
            }

            public static class DetailsBean {

                private int id;
                private String name;
                private String desc;
                private String img;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }
            }
        }

        public static class VideoBean {


            private String title;
            private List<DetailsBeanX> details;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<DetailsBeanX> getDetails() {
                return details;
            }

            public void setDetails(List<DetailsBeanX> details) {
                this.details = details;
            }

            public static class DetailsBeanX {

                private String url;
                private String img;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }
            }
        }
    }
}
