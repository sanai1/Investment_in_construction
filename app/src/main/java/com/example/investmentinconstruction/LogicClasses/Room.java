package com.example.investmentinconstruction.LogicClasses;

import java.util.HashMap;
import java.util.Map;

public class Room {
    private Integer roomCode;
    private Integer cntPeople;
    private Integer nowPeople;
    private Integer currentPeriod;
    private Map<String, User> userMap;

    public Room() {}

    public Room(Integer roomCode, Integer cntPeople, Integer nowPeople, Integer currentPeriod, Map<String, User> userHashMap) {
        this.roomCode = roomCode;
        this.cntPeople = cntPeople;
        this.nowPeople = nowPeople;
        this.currentPeriod = currentPeriod;
        this.userMap = userHashMap;
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
}