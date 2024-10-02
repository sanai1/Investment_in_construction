package com.example.investmentinconstruction.LogicClasses;

public class DescriptionShop {

    private String typeShop; // тип магазина (от него зависят характеристики, которые будем хардкодить)
    private Integer duration; // продолжительность стройки (хардкодим)
    private Integer startPeriod; // считается относительно currentPeriod
    private Integer priceMonth; // цена стройки 1 месяца (хардкодим)
    private Integer soldProfit; // общая прибыль с магазина (считается как сумма прибыли за все месяцы)

    public DescriptionShop(String typeShop, Integer duration, Integer startPeriod, Integer priceMonth, Integer soldProfit) {
        this.typeShop = typeShop;
        this.duration = duration;
        this.startPeriod = startPeriod;
        this.priceMonth = priceMonth;
        this.soldProfit = soldProfit;
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
}