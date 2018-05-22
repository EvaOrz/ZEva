package cn.com.zwwl.bayuwen.model.fm;

import org.json.JSONObject;

import cn.com.zwwl.bayuwen.model.Entry;

/**
 * 音频model
 */
public class FmModel extends Entry {
    private String id;
    private String kid;
    private String title;
    private String content;
    private String free;
    private String play_num;
    private String created_at;
    private String audioName;
    private String audioUrl;
    private String audioMinetype;
    private String audioDuration;
    private String audioSize;


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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getPlay_num() {
        return play_num;
    }

    public void setPlay_num(String play_num) {
        this.play_num = play_num;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getAudioName() {
        return audioName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getAudioMinetype() {
        return audioMinetype;
    }

    public void setAudioMinetype(String audioMinetype) {
        this.audioMinetype = audioMinetype;
    }

    public String getAudioDuration() {
        return audioDuration;
    }

    public void setAudioDuration(String audioDuration) {
        this.audioDuration = audioDuration;
    }

    public String getAudioSize() {
        return audioSize;
    }

    public void setAudioSize(String audioSize) {
        this.audioSize = audioSize;
    }

    public FmModel() {
    }

    public FmModel parseFmModel(JSONObject jsonObject, FmModel fmModel) {
        fmModel.setId(jsonObject.optString("id"));
        fmModel.setKid(jsonObject.optString("kid"));
        fmModel.setTitle(jsonObject.optString("title"));
        fmModel.setContent(jsonObject.optString("content"));
        fmModel.setFree(jsonObject.optString("free"));
        fmModel.setPlay_num(jsonObject.optString("play_num"));
        fmModel.setCreated_at(jsonObject.optString("created_at"));
        JSONObject audio = jsonObject.optJSONObject("audio");
        if (!isNull(audio)) {
            fmModel.setAudioName(audio.optString("name"));
            fmModel.setAudioUrl(audio.optString("url"));
            fmModel.setAudioMinetype(audio.optString("mime_type"));
            fmModel.setAudioDuration(audio.optString("duration"));
            fmModel.setAudioSize(audio.optString("size"));
        }
        return fmModel;
    }
}
