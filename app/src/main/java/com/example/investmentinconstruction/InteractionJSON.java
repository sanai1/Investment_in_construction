package com.example.investmentinconstruction;

import android.content.Context;

import com.example.investmentinconstruction.LogicClasses.Room;

public class InteractionJSON {

    static {
        System.loadLibrary("investmentinconstruction");
    }

    private static InteractionJSON INSTANCE;
    private Context context;

    public InteractionJSON(Context context) {
        this.context = context;
    }

    public synchronized static InteractionJSON getInstance(Context context) {
        if (INSTANCE == null) INSTANCE = new InteractionJSON(context);
        return INSTANCE;
    }

    public Room contract(String jsonOld) {
        String jsonNew = contractWithCJNI(jsonOld);
        System.out.println(jsonNew);
        return null;
    }

    public native String contractWithCJNI(String jsonOld);

}