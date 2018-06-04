package cn.com.zwwl.bayuwen.model;

import java.util.List;

/**
 *  在学课程
 *  Created by zhumangmang at 2018/5/31 11:58
 */
public class StudyingModel extends Entry {

    /**
     * online : 1
     * finish : false
     * count : 50
     * current : 12
     * next : 13
     * nextTime : 2018-05-27 19:45:00
     * completeClass : [{"id":"5931","kid":"8391","title":"第1讲：中国古代神话传说及文化寓意","startClassTime":"1519556400","endClassTime":"1519562700"},{"id":"5932","kid":"8391","title":"第2讲：先秦诗歌及中国古代婚恋制度","startClassTime":"1520161200","endClassTime":"1520167500"},{"id":"5933","kid":"8391","title":"第3讲：先秦散文及中国古代礼仪制度","startClassTime":"1520766000","endClassTime":"1520772300"},{"id":"5934","kid":"8391","title":"第4讲：《论语》及中国古代儒家流派","startClassTime":"1521373500","endClassTime":"1521377100"},{"id":"5935","kid":"8391","title":"第5讲：两汉散文及中国古代骈散之争","startClassTime":"1521978300","endClassTime":"1521981900"},{"id":"5936","kid":"8391","title":"第6讲：两汉史学及中国古代刑罚制度","startClassTime":"1522583100","endClassTime":"1522586700"},{"id":"5937","kid":"8391","title":"第7讲：《巴黎圣母院》及法国大革命","startClassTime":"1523187900","endClassTime":"1523191500"},{"id":"5938","kid":"8391","title":"第8讲：《欧也妮·葛朗台》及资本主义崛起","startClassTime":"1523792700","endClassTime":"1523796300"},{"id":"5939","kid":"8391","title":"第9讲：两汉辞赋及中国古代建筑艺术","startClassTime":"1524397500","endClassTime":"1524401100"},{"id":"5940","kid":"8391","title":"第10讲：两汉乐府诗及中国古代美女","startClassTime":"1525607100","endClassTime":"1525610700"},{"id":"5941","kid":"8391","title":"第11讲:《三个火枪手》及法国宫廷秘史","startClassTime":"1526211900","endClassTime":"1526215500"},{"id":"5942","kid":"8391","title":"第12讲:《马铁奥·法尔哥尼》及地中海风情","startClassTime":"1526816700","endClassTime":"1526820300"}]
     */

    private int online;
    private boolean finish;
    private int count;
    private int current;
    private int next;
    private String nextTime;
    private List<LessonModel> completeClass;

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

    public List<LessonModel> getCompleteClass() {
        return completeClass;
    }

    public void setCompleteClass(List<LessonModel> completeClass) {
        this.completeClass = completeClass;
    }

}
