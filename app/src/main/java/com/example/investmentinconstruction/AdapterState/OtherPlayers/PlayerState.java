package com.example.investmentinconstruction.AdapterState.OtherPlayers;

public class PlayerState {

    private String cid;
    private String type;
    private String progressBuild;
    private int picture;
    private Integer soldApartment;
    private Integer number;

    public PlayerState(String cid, String type, String progressBuild, int picture, Integer number) {
        this.cid = cid;
        this.type = type;
        this.progressBuild = progressBuild;
        this.picture = picture;
        this.number = number;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProgressBuild() {
        return progressBuild;
    }

    public void setProgressBuild(String progressBuild) {
        this.progressBuild = progressBuild;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public Integer getSoldApartment() {
        return soldApartment;
    }

    public void setSoldApartment(Integer soldApartment) {
        this.soldApartment = soldApartment;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}