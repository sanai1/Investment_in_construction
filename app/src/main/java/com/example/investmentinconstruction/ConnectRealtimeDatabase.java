package com.example.investmentinconstruction;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.investmentinconstruction.LogicClasses.Room;
import com.example.investmentinconstruction.LogicClasses.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectRealtimeDatabase {

    private static ConnectRealtimeDatabase INSTANCE;
    private DatabaseReference root, checkRoom;
    private Context context;
    private boolean result;
    private Lock lock;

    ConnectRealtimeDatabase(Context context) {
        this.context = context;
        this.root = FirebaseDatabase.getInstance().getReference().getRoot();
        this.result = true;
        lock = new ReentrantLock();
    }

    public synchronized static ConnectRealtimeDatabase getInstance(Context context) {
        if (INSTANCE == null) INSTANCE = new ConnectRealtimeDatabase(context);
        return INSTANCE;
    }

    public void createRoom(Room room) {
        root.child(room.getRoomCode().toString()).setValue(room);
    }

    public void getRoom(String roomCode, MainActivity mainActivity) {
        root.child(roomCode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
                Gson gson = new Gson();
                String room = gson.toJson(snapshot.getValue());
                System.out.println(room);
                mainActivity.setRoom(gson.fromJson(room, Room.class));
                System.out.println("unlock in getRoom");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Ошибка при чтении комнаты в getRoom");
            }
        });
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
        lock.lock();
        System.out.println("lock in updateUser");
        try {
            user.setNumberStep(1);
            root.child(roomCode).child("userMap").child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    lock.unlock();
                    System.out.println("unlock in updateUser");
                }
            });
        }
        finally {

        }
    }

    public void checkRoom(String roomCode, String uid, MainActivity mainActivity) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println("lock in checkRoom");
                try {
                    result = true;
                    while (result) {
                        try {
                            System.out.println("start new while");
                            root.child(roomCode).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Room room = snapshot.getValue(Room.class);
                                    Integer cntPeopleRoom = room.getCntPeople();
                                    Map<String, User> userMap = room.getUserMap();
                                    int cntNumberStepOne = 0;
                                    int cntNumberStepZero = 0;
                                    for (User user : userMap.values()) {
                                        if (user.getNumberStep() == 1) {
                                            cntNumberStepOne++;
                                        } else { // user.getNumberStep == 0
                                            cntNumberStepZero++;
                                        }
                                    }
                                    if (cntPeopleRoom == cntNumberStepOne) {
                                        // переход в contact
                                        System.out.println("переход в contact");
                                        result = false;
//                                        lock.unlock();
                                        contract(roomCode, mainActivity, uid);
                                    } else if (cntPeopleRoom == cntNumberStepZero) {
                                        // переход в MainActivity
                                        System.out.println("переход в MainActivity");
                                        result = false;
                                        mainActivity.replaceFragment(room.getUserMap().get(uid));
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {}
                            });
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                finally {
                    lock.unlock();
                }
            }
        });
        thread.start();
    }

    private void contract(String roomCode, MainActivity mainActivity, String uid) {
        root.child(roomCode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> json = (Map<String, Object>) snapshot.getValue();
                Room room = InteractionJSON.getInstance(context).contract(json);
                for(User user : room.getUserMap().values()) {
                    user.setNumberStep(0);
                }
                for (User user : room.getUserMap().values()) {
                    System.out.println(user.getNumberStep());
                }
                System.out.println("0000000000000000000000");
                System.out.println(room);
                System.out.println("99999999999999999999999");
                root.child(roomCode).setValue(room);
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