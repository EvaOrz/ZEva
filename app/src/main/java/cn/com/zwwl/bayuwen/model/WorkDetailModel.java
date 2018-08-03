package cn.com.zwwl.bayuwen.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class WorkDetailModel implements Parcelable {

    private String job_desc;
    private TDescBean t_desc;
    private int state;
    private List<CommonModel> job_img;

    public String getC_desc() {
        return job_desc;
    }

    public void setC_desc(String c_desc) {
        this.job_desc = c_desc;
    }

    public TDescBean getT_desc() {
        return t_desc;
    }

    public void setT_desc(TDescBean t_desc) {
        this.t_desc = t_desc;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<CommonModel> getC_img() {
        return job_img;
    }

    public void setC_img(List<CommonModel> c_img) {
        this.job_img = c_img;
    }

    public static class TDescBean implements Parcelable {

        private String uid;
        private int role;
        private String name;
        private String content;

        protected TDescBean(Parcel in) {
            uid = in.readString();
            role = in.readInt();
            name = in.readString();
            content = in.readString();
        }

        public static final Creator<TDescBean> CREATOR = new Creator<TDescBean>() {
            @Override
            public TDescBean createFromParcel(Parcel in) {
                return new TDescBean(in);
            }

            @Override
            public TDescBean[] newArray(int size) {
                return new TDescBean[size];
            }
        };

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(uid);
            dest.writeInt(role);
            dest.writeString(name);
            dest.writeString(content);
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.job_desc);
        dest.writeParcelable(this.t_desc, flags);
        dest.writeInt(this.state);
        dest.writeList(this.job_img);
    }

    public WorkDetailModel() {
    }

    protected WorkDetailModel(Parcel in) {
        this.job_desc = in.readString();
        this.t_desc = in.readParcelable(TDescBean.class.getClassLoader());
        this.state = in.readInt();
        this.job_img = new ArrayList<CommonModel>();
        in.readList(this.job_img, CommonModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<WorkDetailModel> CREATOR = new Parcelable.Creator<WorkDetailModel>() {
        @Override
        public WorkDetailModel createFromParcel(Parcel source) {
            return new WorkDetailModel(source);
        }

        @Override
        public WorkDetailModel[] newArray(int size) {
            return new WorkDetailModel[size];
        }
    };
}
