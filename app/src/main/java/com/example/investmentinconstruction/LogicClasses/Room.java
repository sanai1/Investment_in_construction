package com.example.investmentinconstruction.LogicClasses;

public class Room {
    private Integer roomCode;
    private Integer cntPeople;
    private Integer nowPeople;
    private Integer currentPeriod;
    private User[] users;

    public Room(Integer roomCode, Integer cntPeople, Integer nowPeople, Integer currentPeriod, User[] users) {
        this.roomCode = roomCode;
        this.cntPeople = cntPeople;
        this.nowPeople = nowPeople;
        this.currentPeriod = currentPeriod;
        this.users = users;
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

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }
}