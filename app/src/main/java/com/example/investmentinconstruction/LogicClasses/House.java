package com.example.investmentinconstruction.LogicClasses;

public class House {

    private String hid;
    private String typeHouse; // тип дома (от него зависят характеристики, которые будем хардкодить)
    private Integer duration; // продолжительность стройки (хардкодим)
    private Integer startPeriod; // считается относительно currentPeriod
    private Integer priceMonth; // цена стройки 1 месяца (хардкодим)
    private Integer salePrice; // цена продажи 1 квартиры
    private Integer countApartments; // кол-во квартир в доме (хардкодим)
    private Integer soldApartments; // кол-во проданных квартир в доме
    private Integer saleApartments; // кол-во квартир, которые выставлены на продажу в этом месяце
    private Integer soldProfit; // общая прибыль с дома (считается сумма по проданным квартирам)
    private Integer percent; // процент выполненой стройки (в %)
    private Integer number; // номер постройки у конкретного пользователя

    public House() {}

    public House(String hid, String typeHouse, Integer startPeriod,
                 Integer salePrice, Integer soldApartments, Integer saleApartments, Integer soldProfit, Integer percent, Integer number) {
        this.hid = hid;
        this.typeHouse = typeHouse;
        this.startPeriod = startPeriod;
        this.salePrice = salePrice;
        this.soldApartments = soldApartments;
        this.saleApartments = saleApartments;
        this.soldProfit = soldProfit;
        this.percent = percent;
        this.number = number;
        initHouse();
    }

    private void initHouse() {
        if (typeHouse.equals("Panel")) {
            this.duration =6;
            this.priceMonth = 60000;
            this.countApartments = 30;
        } else if (typeHouse.equals("Brick")) {
            this.duration = 7;
            this.priceMonth = 70000;
            this.countApartments = 40;
        } else if (typeHouse.equals("Monolithic")) {
            this.duration = 8;
            this.priceMonth = 80000;
            this.countApartments = 50;
        }
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getTypeHouse() {
        return typeHouse;
    }

    public void setTypeHouse(String typeHouse) {
        this.typeHouse = typeHouse;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(Integer startPeriod) {
        this.startPeriod = startPeriod;
    }

    public Integer getPriceMonth() {
        return priceMonth;
    }

    public void setPriceMonth(Integer priceMonth) {
        this.priceMonth = priceMonth;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getCountApartments() {
        return countApartments;
    }

    public void setCountApartments(Integer countApartments) {
        this.countApartments = countApartments;
    }

    public Integer getSoldApartments() {
        return soldApartments;
    }

    public void setSoldApartments(Integer soldApartments) {
        this.soldApartments = soldApartments;
    }

    public Integer getSaleApartments() {
        return saleApartments;
    }

    public void setSaleApartments(Integer saleApartments) {
        this.saleApartments = saleApartments;
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