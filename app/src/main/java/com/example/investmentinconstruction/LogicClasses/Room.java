package com.example.investmentinconstruction.LogicClasses;

import java.util.Map;

public class Room {
    private Integer roomCode; // приватный код комнаты
    private Integer cntPeople; // кол-во людей в комнате (на сколько расчитана)
    private Integer nowPeople; // кол-во людей в комнате (требует доработки)
    private Integer currentPeriod; // кол-во шагов моделирования
    private Map<String, User> userMap; // мапа игроков
    private Integer numberStep; // номер месяца, который сейчас идет
    private Integer startPeriod; // номер месяца в который началась игра

    public Room() {}

    public Room(Integer roomCode, Integer cntPeople, Integer nowPeople, Integer currentPeriod, Map<String, User> userHashMap, Integer startPeriod) {
        this.roomCode = roomCode;
        this.cntPeople = cntPeople;
        this.nowPeople = nowPeople;
        this.currentPeriod = currentPeriod;
        this.userMap = userHashMap;
        this.startPeriod = startPeriod;
        this.numberStep = 0;
    }

    public Integer getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(Integer roomCode) {
        this.roomCode = roomCode;
    }

    public Integer getCntPeople() {
        return cntPeople;
    }

    public void setCntPeople(Integer cntPeople) {
        this.cntPeople = cntPeople;
    }

    public Integer getNowPeople() {
        return nowPeople;
    }

    public void setNowPeople(Integer nowPeople) {
        this.nowPeople = nowPeople;
    }

    public Integer getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(Integer currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<String, User> userMap) {
        this.userMap = userMap;
    }

    public Integer getNumberStep() {
        return numberStep;
    }

    public void setNumberStep(Integer numberStep) {
        this.numberStep = numberStep;
    }

    public Integer getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(Integer startPeriod) {
        this.startPeriod = startPeriod;
    }
}