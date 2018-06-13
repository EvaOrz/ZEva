package cn.com.zwwl.bayuwen.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CommonModel extends  Entry{
    private String content;
    private int state;
    private String id;
    private String url;
    private String name;
    private String tid;
    private String kid;
    private String student_no;
    private String create_at;
    private String update_at;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
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

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }



    public CommonModel() {
    }

    protected CommonModel(Parcel in) {
        this.content = in.readString();
        this.state = in.readInt();
        this.id = in.readString();
        this.url = in.readString();
        this.name = in.readString();
        this.tid = in.readString();
        this.kid = in.readString();
        this.student_no = in.readString();
        this.create_at = in.readString();
        this.update_at = in.readString();
        this.status = in.readInt();
    }

    public static final Parcelable.Creator<CommonModel> CREATOR = new Parcelable.Creator<CommonModel>() {
        @Override
        public CommonModel createFromParcel(Parcel source) {
            return new CommonModel(source);
        }

        @Override
        public CommonModel[] newArray(int size) {
            return new CommonModel[size];
        }
    };
}
