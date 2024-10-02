package com.example.investmentinconstruction.LogicClasses;

public class Construction {

    private House[] houses;
    private Shop[] shops;

    public Construction(Shop[] shops, House[] houses) {
        this.shops = shops;
        this.houses = houses;
    }

    public House[] getHouses() {
        return houses;
    }

    public void setHouses(House[] houses) {
        this.houses = houses;
    }

    public Shop[] getShops() {
        return shops;
    }

    public void setShops(Shop[] shops) {
        this.shops = shops;
    }
}