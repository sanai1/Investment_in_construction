package com.example.investmentinconstruction;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.investmentinconstruction.LogicClasses.Room;
import com.example.investmentinconstruction.LogicClasses.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Objects;

public class ConnectRealtimeDatabase {

    private static ConnectRealtimeDatabase INSTANCE;
    private DatabaseReference root, checkRoom;
    private Context context;
    private boolean result;

    ConnectRealtimeDatabase(Context context) {
        this.context = context;
        this.root = FirebaseDatabase.getInstance().getReference().getRoot();
        this.result = false;
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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Ошибка при проверке наличия комнаты");
            }
        });
        return true;
    }

    public void updateUser(String roomCode, String uid, User user) {
        root.child(roomCode).child("userMap").child(uid).setValue(user);
        root.child(roomCode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int numberStepRoom = Integer.parseInt(snapshot.child("numberStep").getValue().toString());
                root.child(roomCode).child("userMap").child(uid).child("numberStep").setValue(numberStepRoom + 1);
                result = true;
                // TODO: выполнить проверку на наличия новых данных от всех игроков, если надо -> отправить данные в C++
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Ошибка при обновлении user");
            }
        });
    }

    public void checkUpdateRoom(String roomCode, String uid, MainActivity mainActivity) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (result) {
                    Room room = snapshot.getValue(Room.class);
                    Integer numberStepRoom = room.getNumberStep();
                    Map<String, User> userMap = room.getUserMap();
                    Integer cntPeople = room.getCntPeople();
                    int cntStepRoomRavn = 0;
                    int cntStepUser = 0;
                    for (User user : userMap.values()) {
                        if (user.getNumberStep().equals(numberStepRoom)) {
                            cntStepRoomRavn++;
                        } else {
                            cntStepUser++;
                        }
                    }
                    if (cntStepUser == cntPeople) {
                        contract(roomCode, mainActivity, uid);
                    } else if (cntStepRoomRavn == cntPeople) {
                        mainActivity.replaceFragment(room.getUserMap().get(uid));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "checkUpdateUserMap", error.toException());
            }
        };
        root.child(roomCode).addValueEventListener(valueEventListener);
    }

    private void contract(String roomCode, MainActivity mainActivity, String uid) {
        root.child(roomCode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> json = (Map<String, Object>) snapshot.getValue();
                Room room = InteractionJSON.getInstance(context).contract(json);
                System.out.println("0000000000000000000000");
                System.out.println(room);
                System.out.println("99999999999999999999999");
                root.child(roomCode).setValue(room);
                result = false;
                mainActivity.replaceFragment(room.getUserMap().get(uid));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "contract in ConnectRealtimeDatabase", error.toException());
            }
        });
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