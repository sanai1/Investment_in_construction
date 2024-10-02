package com.example.investmentinconstruction;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.investmentinconstruction.LogicClasses.Room;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConnectRealtimeDatabase {

    private static ConnectRealtimeDatabase INSTANCE;
    private DatabaseReference root;
    private Context context;

    ConnectRealtimeDatabase(Context context) {
        this.context = context;
        this.root = FirebaseDatabase.getInstance().getReference().getRoot();
    }

    public synchronized static ConnectRealtimeDatabase getInstance(Context context) {
        if (INSTANCE == null) INSTANCE = new ConnectRealtimeDatabase(context);
        return INSTANCE;
    }

    public void createRoom(Room room) {
        System.out.println(true);
    }

}