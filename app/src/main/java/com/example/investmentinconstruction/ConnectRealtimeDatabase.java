package com.example.investmentinconstruction;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.investmentinconstruction.LogicClasses.Room;
import com.example.investmentinconstruction.LogicClasses.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ConnectRealtimeDatabase {

    private static ConnectRealtimeDatabase INSTANCE;
    private DatabaseReference root, checkRoom;
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
        root.child(room.getRoomCode().toString()).setValue(room);
    }

    private void addUser(String roomCode, String uid, String nameDistrict, String nowPeople) {
        User user = new User(uid, nameDistrict, 0, null, null, null);
        root.child(roomCode).child("nowPeople").setValue(Integer.valueOf(nowPeople) + 1);
        root.child(roomCode).child("userList").child(nowPeople).setValue(user);
    }

    public boolean signInRoom(String roomCode, String uid, String nameDistrict) {
        final boolean[] result = {true};
        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(roomCode).getValue() != null) {
                    addUser(roomCode, uid, nameDistrict, snapshot.child(roomCode).child("nowPeople").getValue().toString());
                } else {
                    result[0] = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Ошибка при проверке наличия комнаты");
            }
        });
        return result[0];
    }
}