package com.example.investmentinconstruction.LogicClasses;

public class Shop {

    private String sid;
    private String typeShop; // тип магазина (от него зависят характеристики, которые будем хардкодить)
    private Integer duration; // продолжительность стройки (хардкодим)
    private Integer startPeriod; // считается относительно currentPeriod
    private Integer priceMonth; // цена стройки 1 месяца (хардкодим)
    private Integer soldProfit; // общая прибыль с магазина (считается как сумма прибыли за все месяцы)
    private Integer percent; // процент постройки (в %)

    public Shop() {}

    public Shop(String sid, String typeShop, Integer startPeriod, Integer soldProfit, Integer percent) {
        this.sid = sid;
        this.typeShop = typeShop;
        this.startPeriod = startPeriod;
        this.soldProfit = soldProfit;
        this.percent = percent;
        initShop();
    }

    private void initShop() {
        if (typeShop.equals("Supermarket")) {
            this.duration = 5;
            this.priceMonth = 45000;
        } else if (typeShop.equals("Bakery")) {
            this.duration = 5;
            this.priceMonth = 4;
        } else if (typeShop.equals("HardwareStore")) {
            this.duration = 2;
            this.priceMonth = 10000;
        }
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTypeShop() {
        return typeShop;
    }

    public void setTypeShop(String typeShop) {
        this.typeShop = typeShop;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPriceMonth() {
        return priceMonth;
    }

    public void setPriceMonth(Integer priceMonth) {
        this.priceMonth = priceMonth;
    }

    public Integer getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(Integer startPeriod) {
        this.startPeriod = startPeriod;
    }

    public Integer getSoldProfit() {
        return soldProfit;
    }

    public void setSoldProfit(Integer soldProfit) {
        this.soldProfit = soldProfit;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }
}