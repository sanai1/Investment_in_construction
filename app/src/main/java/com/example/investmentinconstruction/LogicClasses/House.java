package com.example.investmentinconstruction.LogicClasses;

public class House {

    private String hid;
    private DescriptionHouse descriptionHouse;

    public House(String hid, DescriptionHouse descriptionHouse) {
        this.hid = hid;
        this.descriptionHouse = descriptionHouse;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public DescriptionHouse getDescriptionHouse() {
        return descriptionHouse;
    }

    public void setDescriptionHouse(DescriptionHouse descriptionHouse) {
        this.descriptionHouse = descriptionHouse;
    }
}