package cn.com.zwwl.bayuwen.model;

import java.util.List;

public class PuzzleModel extends Entry {

    /**
     * puzzleId : 22
     * puzzleName : test1
     * sectionList : [{"sectionId":379,"sectionName":"第一讲 修辞","chapterId":0,"totalScore":105,"totalCount":6},{"sectionId":380,"sectionName":"第一讲 文言文","chapterId":0,"totalScore":105,"totalCount":6},{"sectionId":381,"sectionName":"第三讲 唐诗","chapterId":0,"totalScore":105,"totalCount":6},{"sectionId":382,"sectionName":"第一讲 宋词","chapterId":0,"totalScore":105,"totalCount":6},{"sectionId":383,"sectionName":"综合训练1","chapterId":0,"totalScore":105,"totalCount":6},{"sectionId":384,"sectionName":"综合训练2","chapterId":0,"totalScore":105,"totalCount":6}]
     */

    private int puzzleId;
    private String puzzleName;
    private List<CommonModel> sectionList;

    public int getPuzzleId() {
        return puzzleId;
    }

    public void setPuzzleId(int puzzleId) {
        this.puzzleId = puzzleId;
    }

    public String getPuzzleName() {
        return puzzleName;
    }

    public void setPuzzleName(String puzzleName) {
        this.puzzleName = puzzleName;
    }

    public List<CommonModel> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<CommonModel> sectionList) {
        this.sectionList = sectionList;
    }

}
