package com.example.investmentinconstruction.LogicClasses;

public class Advertisement {

    private Integer house;
    private Integer shop;

    public Advertisement(Integer house, Integer shop) {
        this.house = house;
        this.shop = shop;
    }

    public Integer getHouse() {
        return house;
    }

    public void setHouse(Integer house) {
        this.house = house;
    }

    public Integer getShop() {
        return shop;
    }

    public void setShop(Integer shop) {
        this.shop = shop;
    }
}