package cn.com.zwwl.bayuwen.model;

/**
 * 课程进度
 */
public class PlanModel {
    /**
     * online : 1
     * finish : false
     * count : 50
     * current : 13
     * currentLectureId : 5943
     * next : 14
     * nextTime : 2018-06-03 19:45:00
     */

    private int online;
    private boolean finish;
    private int count;
    private int current;
    private String currentLectureId;
    private int next;
    private String nextTime;
    private int source;
    private String playUrl;
    private String title;
    private boolean isOpen;
    private int is_submit_job;
    private WorkDetailModel job;

    public WorkDetailModel getJob() {
        return job;
    }

    public void setJob(WorkDetailModel job) {
        this.job = job;
    }

    public int getIs_submit_job() {
        return is_submit_job;
    }

    public void setIs_submit_job(int is_submit_job) {
        this.is_submit_job = is_submit_job;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public String getCurrentLectureId() {
        return currentLectureId;
    }

    public void setCurrentLectureId(String currentLectureId) {
        this.currentLectureId = currentLectureId;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public String getNextTime() {
        return nextTime;
    }

    public void setNextTime(String nextTime) {
        this.nextTime = nextTime;
    }
}
