package com.example.investmentinconstruction.LogicClasses;

public class User {

    private String uid;
    private String district;
    private Integer profitFull;
    private Construction construction;
    private Advertisement advertisement;

    public User(String uid, String district, Integer profitFull, Construction construction, Advertisement advertisement) {
        this.uid = uid;
        this.district = district;
        this.profitFull = profitFull;
        this.construction = construction;
        this.advertisement = advertisement;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getProfitFull() {
        return profitFull;
    }

    public void setProfitFull(Integer profitFull) {
        this.profitFull = profitFull;
    }

    public Construction getConstruction() {
        return construction;
    }

    public void setConstruction(Construction construction) {
        this.construction = construction;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }
}