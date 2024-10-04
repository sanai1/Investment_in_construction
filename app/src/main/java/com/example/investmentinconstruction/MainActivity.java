package com.example.investmentinconstruction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.investmentinconstruction.AdapterState.ConstructionAdapter;
import com.example.investmentinconstruction.AdapterState.ConstructionState;
import com.example.investmentinconstruction.DialogFragment.AddInfoConstruction;
import com.example.investmentinconstruction.DialogFragment.AdvertisementConstruction;
import com.example.investmentinconstruction.DialogFragment.NewConstruction;
import com.example.investmentinconstruction.LogicClasses.Advertisement;
import com.example.investmentinconstruction.LogicClasses.House;
import com.example.investmentinconstruction.LogicClasses.Shop;
import com.example.investmentinconstruction.LogicClasses.User;
import com.example.investmentinconstruction.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity
        implements NewConstruction.DialogListenerAdd, AddInfoConstruction.DialogListenerAddInfo, AdvertisementConstruction.DialogListenerAdvertisement {

    private ActivityMainBinding binding_main;
    private String roomCode;
    private User user;
    protected NewConstruction newConstruction;
    protected AddInfoConstruction addInfoConstruction;
    protected AdvertisementConstruction advertisementConstruction;
    private RecyclerView.LayoutManager layoutManager;
    private ConstructionAdapter constructionAdapter;
    private List<ConstructionState> constructionStateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding_main = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding_main.getRoot());

        Bundle bundle = getIntent().getExtras();
        roomCode = bundle.get("roomCode").toString();
        user = (User) bundle.getSerializable(User.class.getSimpleName());

        binding_main.textViewNameGame.setText(roomCode);
        binding_main.textViewCountMoney.setText(user.getProfitFull().toString());

        newConstruction = new NewConstruction();
        newConstruction.setMyDialogListener(this);

        addInfoConstruction = new AddInfoConstruction();
        addInfoConstruction.setDialogListenerAddInfo(this);

        advertisementConstruction = new AdvertisementConstruction();
        advertisementConstruction.setOnClickListener(this);

        binding_main.navigationMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() != R.id.player1) {
                    binding_main.drawerLayout.close();
                    Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        layoutManager = new LinearLayoutManager(this);
        updateView();
    }

    private void updateView() {
        ConstructionAdapter.OnStateHouseClickListener onStateHouseClickListener = new ConstructionAdapter.OnStateHouseClickListener() {
            @Override
            public void onStateHouseClick(String tag, ConstructionState constructionState, int position) {
                if (constructionState.getType().equals("Brick") || constructionState.getType().equals("Panel") || constructionState.getType().equals("Monolithic")) {
                    addInfoConstruction.setTypeCID(constructionState.getCid());
                    addInfoConstruction.setProgressBuild(constructionState.getProgressBuild());
                    addInfoConstruction.setFullApartment(constructionState.getFullApartment());
                    addInfoConstruction.setSoldApartment(constructionState.getSoldApartment());
                    addInfoConstruction.setKey(constructionState.getKey());
                    addInfoConstruction.show(getSupportFragmentManager(), constructionState.getCid());
                }
            }
        };
        constructionStateList = new ArrayList<>();
        int picture = 0;
        // TODO: сделать правильно progress - проверить разницу между текущим месяцев и месяцев начала стройки, при разнице больше 1 вычислить % иначе 0%
        if (user.getHouseMap() != null) {
            Set<String> integerSet = user.getHouseMap().keySet();
            for (String string : integerSet) {
                if (user.getHouseMap().get(string).getTypeHouse().equals("Brick")) picture = R.drawable.house_brick;
                else if (user.getHouseMap().get(string).getTypeHouse().equals("Panel")) picture = R.drawable.house_panel;
                else if (user.getHouseMap().get(string).getTypeHouse().equals("Monolithic")) picture = R.drawable.house_monolithic;

                constructionStateList.add(new ConstructionState(user.getHouseMap().get(string).getHid(), user.getHouseMap().get(string).getTypeHouse(), "0%", picture));
                constructionStateList.get(constructionStateList.size() - 1).setFullApartment(user.getHouseMap().get(string).getCountApartments().toString());
                constructionStateList.get(constructionStateList.size() - 1).setSoldApartment(user.getHouseMap().get(string).getSoldApartments().toString());
                constructionStateList.get(constructionStateList.size() - 1).setKey(string);
            }
        }
        if (user.getShopMap() != null) {
            Set<String> stringSet = user.getShopMap().keySet();
            for (String string : stringSet) {
                if (user.getShopMap().get(string).getTypeShop().equals("Supermarket")) picture = R.drawable.shop_supermarket;
                else if (user.getShopMap().get(string).getTypeShop().equals("Bakery")) picture = R.drawable.shop_bakery;
                else if (user.getShopMap().get(string).getTypeShop().equals("HardwareStore")) picture = R.drawable.shop_hardware;
                constructionStateList.add(new ConstructionState(user.getShopMap().get(string).getSid(), user.getShopMap().get(string).getTypeShop(), "0%", picture));
            }
        }
        if (!constructionStateList.isEmpty()) {
            constructionAdapter = new ConstructionAdapter(this, constructionStateList, onStateHouseClickListener);
            binding_main.listConstruction.setAdapter(constructionAdapter);
            binding_main.listConstruction.setLayoutManager(layoutManager);
        }
    }

    public void menuPlayer(View view) {
        binding_main.drawerLayout.openDrawer(GravityCompat.START);
    }

    public void addConstruction(View view) {
        newConstruction.show(getSupportFragmentManager(), "newConstruction");
    }

    public void seo(View view) {
        advertisementConstruction.show(getSupportFragmentManager(), "advertisement");
    }

    public void step(View view) {
        // TODO: сделать DialogFragment для уточнения намерений сделать шаг
//        test(); необходимо запустить для получения String - пример входных данных от java к c++
        ConnectRealtimeDatabase.getInstance(this).updateUser(roomCode, user.getUid(), user);
    }

    @Override
    public void onDialogClickListener(boolean house, String typeHouse, boolean shop, String typeShop) {
        // TODO: аргумент startPeriod необходимо выставить правильно, взяв откуда-то
        if (house) {
            if (user.getHouseMap() == null){
                user.setHouseMap(new HashMap<>());
            }
            String hid = String.valueOf(100 + (int) (Math.random() * 900));
            user.getHouseMap().put(hid, new House(hid, typeHouse, 1, 0, 0, 0, 0));
        }
        if (shop) {
            if (user.getShopMap() == null) {
                user.setShopMap(new HashMap<>());
            }
            String sid = String.valueOf(100 + (int) (Math.random() * 900));
            user.getShopMap().put(sid, new Shop(sid, typeShop, 1, 0));
        }
        if (house || shop) {
            updateView();
        } else {
            Log.w("fail in MainActivity", "onDialogClickListener (DialogFragment)");
        }
    }

    private void test() {
        List<User> userList = new ArrayList<>();

        List<House> houseList_1 = new ArrayList<>();
        List<Shop> shopList_1 = new ArrayList<>();

        List<House> houseList_2 = new ArrayList<>();
        List<Shop> shopList_2 = new ArrayList<>();

        houseList_1.add(new House("436", "Panel", 2, 40000, 0, 10, 0));
        houseList_1.add(new House("135", "Monolithic", 1, 0, 0, 0, 0));

        houseList_2.add(new House("146", "Brick", 3, 50000, 5, 5, 200000));
        houseList_2.add(new House("945", "Panel", 2, 35000, 0, 7, 0));

        shopList_1.add(new Shop("425", "Bakery", 1, 0));
        shopList_1.add(new Shop("154", "Supermarket", 3, 0));

        shopList_2.add(new Shop("726", "HardwareStore", 1, 45000));
        shopList_2.add(new Shop("426", "Supermarket", 2, 0));

//        userList.add(new User(UUID.randomUUID().toString(), "Arbat", 0, new Advertisement(1000, 750), houseList_1, shopList_1));
//        userList.add(new User(UUID.randomUUID().toString(), "Sokolniki", 0, new Advertisement(800, 1100), houseList_2, shopList_2));

//        Room room = new Room(15641, 2, 2, 1, userList);
//
//        ConnectRealtimeDatabase.getInstance(this).testString(room);
    }

    @Override
    public void onDialogClickListener(Integer countSale, Integer price, String key) {
        user.getHouseMap().get(key).setSaleApartments(countSale);
        user.getHouseMap().get(key).setSalePrice(price);
    }

    @Override
    public void onDialogClickListener(Integer housesAdvertisement, Integer shopsAdvertisement) {
        if (user.getAdvertisement() == null) {
            user.setAdvertisement(new Advertisement());
        }
        user.getAdvertisement().setHouse(housesAdvertisement);
        user.getAdvertisement().setShop(shopsAdvertisement);
    }
}