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
        // TODO: запись строки в файл с виде json


        stringFromJNI();
        // TODO: на основе файла формируем объект Room и возвращаем


        return null;
    }

    public native String stringFromJNI();

}