package cn.com.zwwl.bayuwen.model;

import org.json.JSONObject;

/**
 * 课程model
 * <p>
 * "substitute_tid":"0",
 * "substitute_tname":null,
 * "class_id":null,
 * "summarize":null
 */
public class KeModel {

    private String id = "";
    private String kid = "";
    private String title = "";
    private String mime_type = "";
    private String duration = "";
    private String hd720 = "";
    private String size = "";
    private String width = "";
    private String height = "";
    private String display_aspect_ratio = "";
    private String status = "";
    private String tid = "";
    private String sort = "";
    private String grade = "";
    private String model = "";
    private String start_at = "";
    private String class_start_at_bak = "";
    private String tname = "";
    private String students = "";
    private String term = "";
    private String school_id = "";
    private String hours = "";
    private String class_end_at = "";
    private String class_start_at = "";
    private String substitute_tid = "";
    private String substitute_tname = "";
    private String class_id = "";
    private String summarize = "";

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMime_type() {
        return mime_type;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getHd720() {
        return hd720;
    }

    public void setHd720(String hd720) {
        this.hd720 = hd720;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getDisplay_aspect_ratio() {
        return display_aspect_ratio;
    }

    public void setDisplay_aspect_ratio(String display_aspect_ratio) {
        this.display_aspect_ratio = display_aspect_ratio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStart_at() {
        return start_at;
    }

    public void setStart_at(String start_at) {
        this.start_at = start_at;
    }

    public String getClass_start_at_bak() {
        return class_start_at_bak;
    }

    public void setClass_start_at_bak(String class_start_at_bak) {
        this.class_start_at_bak = class_start_at_bak;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getStudents() {
        return students;
    }

    public void setStudents(String students) {
        this.students = students;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getClass_end_at() {
        return class_end_at;
    }

    public void setClass_end_at(String class_end_at) {
        this.class_end_at = class_end_at;
    }

    public String getClass_start_at() {
        return class_start_at;
    }

    public void setClass_start_at(String class_start_at) {
        this.class_start_at = class_start_at;
    }

    public String getSubstitute_tid() {
        return substitute_tid;
    }

    public void setSubstitute_tid(String substitute_tid) {
        this.substitute_tid = substitute_tid;
    }

    public String getSubstitute_tname() {
        return substitute_tname;
    }

    public void setSubstitute_tname(String substitute_tname) {
        this.substitute_tname = substitute_tname;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getSummarize() {
        return summarize;
    }

    public void setSummarize(String summarize) {
        this.summarize = summarize;
    }

    public KeModel(){
    }

    public KeModel parseKeModel(JSONObject jsonObject, KeModel keModel) {

        return keModel;
    }
}
