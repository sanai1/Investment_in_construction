package com.example.investmentinconstruction.AdapterState.FinalGame;

public class FinalGameState {

    private String name; // ныне uid
    private Integer fullProfit;
    private int picture;
    public FinalGameState(String name, Integer fullProfit, int picture) {
        this.name = name;
        this.fullProfit = fullProfit;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFullProfit() {
        return fullProfit;
    }

    public void setFullProfit(Integer fullProfit) {
        this.fullProfit = fullProfit;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }
}