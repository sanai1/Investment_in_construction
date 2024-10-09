package com.example.investmentinconstruction.LogicClasses;

import java.io.Serializable;

public class Advertisement implements Serializable {

    private Integer house;
    private Integer shop;
    private Integer prevHouse;

    public Advertisement() {
        this.house = 0;
        this.shop = 0;
        this.prevHouse = 0;
    }

    public Advertisement(Integer house, Integer shop, Integer prevHouse) {
        this.house = house;
        this.shop = shop;
        this.prevHouse = prevHouse;
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

    public Integer getPrevHouse() {
        return prevHouse;
    }

    public void setPrevHouse(Integer prevHouse) {
        this.prevHouse = prevHouse;
    }
}