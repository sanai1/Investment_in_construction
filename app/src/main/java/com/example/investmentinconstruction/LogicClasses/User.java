package com.example.investmentinconstruction.LogicClasses;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {

    private String uid;
    private String district;
    private Integer profitFull;
    private Advertisement advertisement;
    private Map<String, House> houseMap;
    private Map<String, Shop> shopMap;
    private Integer numberInRoom;

    public User(String uid, String district, Integer profitFull, Advertisement advertisement, Map<String, House> houseHashMap, Map<String, Shop> shopHashMap) {
        this.uid = uid;
        this.district = district;
        this.profitFull = profitFull;
        this.advertisement = advertisement;
        this.houseMap = houseHashMap;
        this.shopMap = shopHashMap;
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

    public Map<String, House> getHouseMap() {
        return houseMap;
    }

    public void setHouseMap(Map<String, House> houseMap) {
        this.houseMap = houseMap;
    }

    public Map<String, Shop> getShopMap() {
        return shopMap;
    }

    public void setShopMap(Map<String, Shop> shopMap) {
        this.shopMap = shopMap;
    }

    public Integer getNumberInRoom() {
        return numberInRoom;
    }

    public void setNumberInRoom(Integer numberInRoom) {
        this.numberInRoom = numberInRoom;
    }
}