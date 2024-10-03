package com.example.investmentinconstruction.LogicClasses;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private String uid;
    private String district;
    private Integer profitFull;
    private Advertisement advertisement;
    private List<House> houseList;
    private List<Shop> shopList;

    public User() {

    }

    public User(String uid, String district, Integer profitFull, Advertisement advertisement, List<House> houseList, List<Shop> shopList) {
        this.uid = uid;
        this.district = district;
        this.profitFull = profitFull;
        this.advertisement = advertisement;
        this.houseList = houseList;
        this.shopList = shopList;
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

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public List<House> getHouseList() {
        return houseList;
    }

    public void setHouseList(List<House> houseList) {
        this.houseList = houseList;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}