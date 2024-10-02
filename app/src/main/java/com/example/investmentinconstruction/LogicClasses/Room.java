package com.example.investmentinconstruction.LogicClasses;

import java.util.List;

public class Room {
    private Integer roomCode;
    private Integer cntPeople;
    private Integer nowPeople;
    private Integer currentPeriod;
    private List<User> userList;

    public Room() {}

    public Room(Integer roomCode, Integer cntPeople, Integer nowPeople, Integer currentPeriod, List<User> userList) {
        this.roomCode = roomCode;
        this.cntPeople = cntPeople;
        this.nowPeople = nowPeople;
        this.currentPeriod = currentPeriod;
        this.userList = userList;
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

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}