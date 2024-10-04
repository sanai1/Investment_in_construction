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
import com.example.investmentinconstruction.DialogFragment.NewConstruction;
import com.example.investmentinconstruction.LogicClasses.Advertisement;
import com.example.investmentinconstruction.LogicClasses.House;
import com.example.investmentinconstruction.LogicClasses.Room;
import com.example.investmentinconstruction.LogicClasses.Shop;
import com.example.investmentinconstruction.LogicClasses.User;
import com.example.investmentinconstruction.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements NewConstruction.DialogListenerAdd, AddInfoConstruction.DialogListenerAddInfo {

    private ActivityMainBinding binding_main;
    private String roomCode;
    private User user;
    protected NewConstruction newConstruction;
    protected AddInfoConstruction addInfoConstruction;
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
                    addInfoConstruction.setIndex(constructionState.getIndex());
                    addInfoConstruction.show(getSupportFragmentManager(), constructionState.getCid());
                }
            }
        };
        constructionStateList = new ArrayList<>();
        int picture = 0;
        // TODO: сделать правильно progress - проверить разницу между текущим месяцев и месяцев начала стройки, при разнице больше 1 вычислить % иначе 0%
        if (user.getHouseList() != null) {
            for (int index = 0; index < user.getHouseList().size(); index++) {
                if (user.getHouseList().get(index).getTypeHouse().equals("Brick")) picture = R.drawable.house_brick;
                else if (user.getHouseList().get(index).getTypeHouse().equals("Panel")) picture = R.drawable.house_panel;
                else if (user.getHouseList().get(index).getTypeHouse().equals("Monolithic")) picture = R.drawable.house_monolithic;
                constructionStateList.add(new ConstructionState(user.getHouseList().get(index).getHid(), user.getHouseList().get(index).getTypeHouse(), "0%", picture));
                constructionStateList.get(constructionStateList.size() - 1).setFullApartment(user.getHouseList().get(index).getCountApartments().toString());
                constructionStateList.get(constructionStateList.size() - 1).setSoldApartment(user.getHouseList().get(index).getSoldApartments().toString());
                constructionStateList.get(constructionStateList.size() - 1).setIndex(index);
            }
        }
        if (user.getShopList() != null) {
            for (Shop shop : user.getShopList()) {
                if (shop.getTypeShop().equals("Supermarket")) picture = R.drawable.shop_supermarket;
                else if (shop.getTypeShop().equals("Bakery")) picture = R.drawable.shop_bakery;
                else if (shop.getTypeShop().equals("HardwareStore")) picture = R.drawable.shop_hardware;
                constructionStateList.add(new ConstructionState(shop.getSid(), shop.getTypeShop(), "0%", picture));
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

    public void step(View view) {
        test();
    }

    @Override
    public void onDialogClickListener(boolean house, String typeHouse, boolean shop, String typeShop) {
        // TODO: аргумент startPeriod необходимо выставить правильно, взяв откуда-то
        if (house) {
            if (user.getHouseList() == null){
                user.setHouseList(new ArrayList<>());
            }
            user.getHouseList().add(new House(String.valueOf(100 + (int) (Math.random() * 900)), typeHouse, 1, 0, 0, 0, 0));
        }
        if (shop) {
            if (user.getShopList() == null) {
                user.setShopList(new ArrayList<>());
            }
            user.getShopList().add(new Shop(String.valueOf(100 + (int) (Math.random() * 900)), typeShop, 1, 0));
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

        userList.add(new User(UUID.randomUUID().toString(), "Arbat", 0, new Advertisement(1000, 750), houseList_1, shopList_1));
        userList.add(new User(UUID.randomUUID().toString(), "Sokolniki", 0, new Advertisement(800, 1100), houseList_2, shopList_2));

        Room room = new Room(15641, 2, 2, 1, userList);

        ConnectRealtimeDatabase.getInstance(this).testString(room);
    }

    @Override
    public void onDialogClickListener(Integer countSale, Integer price, int index) {
        user.getHouseList().get(index).setSaleApartments(countSale);
        user.getHouseList().get(index).setSalePrice(price);
        System.out.println(user.getHouseList().get(index).getSaleApartments());
        System.out.println(user.getHouseList().get(index).getSalePrice());
    }
}