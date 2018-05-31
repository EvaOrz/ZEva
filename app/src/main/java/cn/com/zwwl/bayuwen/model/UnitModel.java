package cn.com.zwwl.bayuwen.model;
/**
 *  课程课次
 *  Created by zhumangmang at 2018/5/31 12:17
 */
public class UnitModel extends Entry{

    /**
     * id : 5933
     * kid : 8391
     * title : 第3讲：先秦散文及中国古代礼仪制度
     * startClassTime : 1520766000
     * endClassTime : 1520772300
     */

    private String id;
    private String kid;
    private String title;
    private String startClassTime;
    private String endClassTime;

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

    public String getStartClassTime() {
        return startClassTime;
    }

    public void setStartClassTime(String startClassTime) {
        this.startClassTime = startClassTime;
    }

    public String getEndClassTime() {
        return endClassTime;
    }

    public void setEndClassTime(String endClassTime) {
        this.endClassTime = endClassTime;
    }
}
