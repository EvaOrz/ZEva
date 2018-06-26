package cn.com.zwwl.bayuwen.model;

/**
 * lmj  on 2018/6/26
 */
public class FmListhiistoryModel extends Entry {

    /**
     * id : 666
     * kid : 10589
     * lecture_id : 243
     * created_at : 2018-06-25 18:22:02
     * keInfo : {"kid":"10589","title":"蒋故事（成都）","pic":"http://img.zhugexuetang.com/FgwmSTo86F4zE7nERtDBHc2pRwCv","tname":"蒋楠"}
     * lectureInfo : {"id":"243","title":"106不因人热\u2014蒋故事","audio_id":"66","duration":172}
     */

    private String id;
    private String kid;
    private String lecture_id;
    private String created_at;
    private KeInfoBean keInfo;
    private LectureInfoBean lectureInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getLecture_id() {
        return lecture_id;
    }

    public void setLecture_id(String lecture_id) {
        this.lecture_id = lecture_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public KeInfoBean getKeInfo() {
        return keInfo;
    }

    public void setKeInfo(KeInfoBean keInfo) {
        this.keInfo = keInfo;
    }

    public LectureInfoBean getLectureInfo() {
        return lectureInfo;
    }

    public void setLectureInfo(LectureInfoBean lectureInfo) {
        this.lectureInfo = lectureInfo;
    }

    public static class KeInfoBean {
        /**
         * kid : 10589
         * title : 蒋故事（成都）
         * pic : http://img.zhugexuetang.com/FgwmSTo86F4zE7nERtDBHc2pRwCv
         * tname : 蒋楠
         */

        private String kid;
        private String title;
        private String pic;
        private String tname;

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

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }
    }

    public static class LectureInfoBean {
        /**
         * id : 243
         * title : 106不因人热—蒋故事
         * audio_id : 66
         * duration : 172
         */

        private String id;
        private String title;
        private String audio_id;
        private int duration;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAudio_id() {
            return audio_id;
        }

        public void setAudio_id(String audio_id) {
            this.audio_id = audio_id;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }
    }
}
