package com.example.investmentinconstruction.LogicClasses;

public class Shop {

    private String sid;
    private String typeShop; // тип магазина (от него зависят характеристики, которые будем хардкодить)
    private Integer duration; // продолжительность стройки (хардкодим)
    private Integer startPeriod; // считается относительно currentPeriod
    private Integer priceMonth; // цена стройки 1 месяца (хардкодим)
    private Integer soldProfit; // общая прибыль с магазина (считается как сумма прибыли за все месяцы)
    private Integer percent; // процент постройки (в %)
    private Integer number; // номер постройки у конкретного пользователя

    public Shop() {}

    public Shop(String sid, String typeShop, Integer startPeriod, Integer soldProfit, Integer percent, Integer number) {
        this.sid = sid;
        this.typeShop = typeShop;
        this.startPeriod = startPeriod;
        this.soldProfit = soldProfit;
        this.percent = percent;
        this.number = number;
        initShop();
    }

    private void initShop() {
        if (typeShop.equals("Supermarket")) {
            this.duration = 5;
            this.priceMonth = 50000;
        } else if (typeShop.equals("Bakery")) {
            this.duration = 3;
            this.priceMonth = 30000;
        } else if (typeShop.equals("HardwareStore")) {
            this.duration = 5;
            this.priceMonth = 50000;
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}