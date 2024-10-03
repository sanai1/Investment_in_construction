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
import com.example.investmentinconstruction.DialogFragment.NewConstruction;
import com.example.investmentinconstruction.LogicClasses.House;
import com.example.investmentinconstruction.LogicClasses.Shop;
import com.example.investmentinconstruction.LogicClasses.User;
import com.example.investmentinconstruction.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewConstruction.DialogListenerAdd     {

    private ActivityMainBinding binding_main;
    private String roomCode;
    private User user;
    protected NewConstruction newConstruction;
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
            public void onStateHouseClick(String tag, ConstructionState homeState, int position) {
                // TODO: открытие диалогового окна по нажатию на элемент RecycleView
            }
        };
        constructionStateList = new ArrayList<>();
        int picture = 0;
        // TODO: сделать правильно progress - проверить разницу между текущим месяцев и месяцев начала стройки, при разнице больше 1 вычислить % иначе 0%
        if (user.getHouseList() != null) {
            for (House house : user.getHouseList()) {
                if (house.getTypeHouse().equals("Brick")) picture = R.drawable.house_brick;
                else if (house.getTypeHouse().equals("Panel")) picture = R.drawable.house_panel;
                else if (house.getTypeHouse().equals("Monolithic")) picture = R.drawable.house_monolithic;
                constructionStateList.add(new ConstructionState(house.getHid(), house.getTypeHouse(), "0%", picture));
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
}