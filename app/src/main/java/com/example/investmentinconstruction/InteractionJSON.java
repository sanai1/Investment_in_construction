package com.example.investmentinconstruction;

import android.content.Context;
import android.widget.Toast;

import com.example.investmentinconstruction.LogicClasses.Room;
import com.fasterxml.jackson.core.JsonParser;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        // TODO: запись строки в файл с виде json
        Gson gson = new Gson();
        String toJson = gson.toJson(jsonOld);
        System.out.println(toJson);

        try (FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fileOutputStream.write(toJson.getBytes());
        } catch (Exception e) {
            Toast.makeText(context, "Problem with write in room.json", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        stringFromJNI();
        // TODO: на основе файла формируем объект Room и возвращаем

        try (FileInputStream fileInputStream = context.openFileInput(fileName) ; InputStreamReader streamReader = new InputStreamReader(fileInputStream)) {
            Room room = gson.fromJson(streamReader, Room.class);
            System.out.println(room);
            return room;
        } catch (Exception e) {
            Toast.makeText(context, "Problem with read from room.json", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return null;
    }

    public native String stringFromJNI();

}