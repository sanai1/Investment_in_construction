package com.example.investmentinconstruction.AdapterState.FinalGame;

public class FinalGameState {

    private String name; // ныне uid
    private Long fullProfit;
    private int picture;
    public FinalGameState(String name, Long fullProfit, int picture) {
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

    public Long getFullProfit() {
        return fullProfit;
    }

    public void setFullProfit(Long fullProfit) {
        this.fullProfit = fullProfit;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }
}