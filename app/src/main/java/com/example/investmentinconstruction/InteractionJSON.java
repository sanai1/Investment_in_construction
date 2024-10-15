package com.example.investmentinconstruction;

import android.content.Context;

import com.example.investmentinconstruction.LogicClasses.Room;
import com.google.gson.Gson;

import java.util.Map;

public class InteractionJSON {

    static {
        System.loadLibrary("investmentinconstruction");
    }

    private static InteractionJSON INSTANCE;
    private final String fileName = "room.json";
    private Context context;

    InteractionJSON(Context context) {
        this.context = context;
    }

    public synchronized static InteractionJSON getInstance(Context context) {
        if (INSTANCE == null) INSTANCE = new InteractionJSON(context);
        return INSTANCE;
    }

    public Room contract(Map<String, Object> jsonOld) {
        Gson gson = new Gson();
        String toJson = gson.toJson(jsonOld);
        System.out.println(toJson);

        System.out.println("start C++");
        String json = contractWithCJNI(toJson);
        System.out.println(json);
        System.out.println("finish C++");

        return gson.fromJson(json, Room.class);
    }

    public native String contractWithCJNI(String json);

}