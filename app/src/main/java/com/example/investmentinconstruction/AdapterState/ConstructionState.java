package com.example.investmentinconstruction.AdapterState;

public class ConstructionState {

    private String cid;
    private String type;
    private String progressBuild;
    private int picture;

    public ConstructionState(String hid, String type, String progress, int picture) {
        this.cid = hid;
        this.type = type;
        this.progressBuild = progress;
        this.picture = picture;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String hid) {
        this.cid = hid;
    }

    public String getProgressBuild() {
        return progressBuild;
    }

    public void setProgressBuild(String progressBuild) {
        this.progressBuild = progressBuild;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }
}