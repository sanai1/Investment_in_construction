package com.example.investmentinconstruction.LogicClasses;

public class Shop {

    private String sid;
    private DescriptionShop descriptionShop;

    public Shop(String sid, DescriptionShop descriptionShop) {
        this.sid = sid;
        this.descriptionShop = descriptionShop;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public DescriptionShop getDescriptionShop() {
        return descriptionShop;
    }

    public void setDescriptionShop(DescriptionShop descriptionShop) {
        this.descriptionShop = descriptionShop;
    }
}