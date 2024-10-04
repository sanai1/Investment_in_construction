package com.example.investmentinconstruction;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.investmentinconstruction.LogicClasses.Advertisement;
import com.example.investmentinconstruction.LogicClasses.House;
import com.example.investmentinconstruction.LogicClasses.Room;
import com.example.investmentinconstruction.LogicClasses.Shop;
import com.example.investmentinconstruction.LogicClasses.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConnectRealtimeDatabase {

    private static ConnectRealtimeDatabase INSTANCE;
    private DatabaseReference root, checkRoom;
    private Context context;
    private boolean result;

    ConnectRealtimeDatabase(Context context) {
        this.context = context;
        this.root = FirebaseDatabase.getInstance().getReference().getRoot();
        this.result = true;
    }

    public synchronized static ConnectRealtimeDatabase getInstance(Context context) {
        if (INSTANCE == null) INSTANCE = new ConnectRealtimeDatabase(context);
        return INSTANCE;
    }

    public void createRoom(Room room) {
        root.child(room.getRoomCode().toString()).setValue(room);
    }

    private void addUser(String roomCode, String nowPeople, User user) {
        // TODO: сделать проверку на кол-во людей в комнате (если уже все присоеденились запретить вход новых людей)
        root.child(roomCode).child("nowPeople").setValue(Integer.valueOf(nowPeople) + 1);
        root.child(roomCode).child("userMap").child(user.getUid()).setValue(user);
    }

    public boolean signInRoom(String roomCode, String uid, String nameDistrict, User user) {
        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(roomCode).getValue() != null) {
                    addUser(roomCode, snapshot.child(roomCode).child("nowPeople").getValue().toString(), user);
                } else {
                    result = false;
                }
                System.out.println(result + " listener");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Ошибка при проверке наличия комнаты");
            }
        });
        return result;
    }

    public void updateUser(String roomCode, String uid, User user) {
        root.child(roomCode).child("userMap").child(uid).setValue(user);
    }

    public void testString(Room room) { // запускается при нажатии на stepButton в MainActivity для получения тестовых данных
        root.child(room.getRoomCode().toString()).setValue(room);
        root.child(room.getRoomCode().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String json = snapshot.getValue().toString();
                System.out.println(json);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}