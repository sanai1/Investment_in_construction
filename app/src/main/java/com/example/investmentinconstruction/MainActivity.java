package com.example.investmentinconstruction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.example.investmentinconstruction.AdapterState.ConstructionAdapter;
import com.example.investmentinconstruction.AdapterState.ConstructionState;
import com.example.investmentinconstruction.DialogFragment.AddInfoConstruction;
import com.example.investmentinconstruction.DialogFragment.AdvertisementConstruction;
import com.example.investmentinconstruction.DialogFragment.NewConstruction;
import com.example.investmentinconstruction.DialogFragment.QuestionAgain;
import com.example.investmentinconstruction.Fragments.LoadingFragment;
import com.example.investmentinconstruction.Fragments.MainFragment;
import com.example.investmentinconstruction.LogicClasses.Advertisement;
import com.example.investmentinconstruction.LogicClasses.House;
import com.example.investmentinconstruction.LogicClasses.Shop;
import com.example.investmentinconstruction.LogicClasses.User;
import com.example.investmentinconstruction.databinding.ActivityMainBinding;
import com.example.investmentinconstruction.databinding.FragmentMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity
        implements
        NewConstruction.DialogListenerAdd,
        AddInfoConstruction.DialogListenerAddInfo,
        AdvertisementConstruction.DialogListenerAdvertisement,
        QuestionAgain.OnDialogClickListenerQuestionAgain {

    private ActivityMainBinding binding;
    private FragmentMainBinding binding_main;
    private String roomCode;
    private User user;
    protected NewConstruction newConstruction;
    protected AddInfoConstruction addInfoConstruction;
    protected AdvertisementConstruction advertisementConstruction;
    protected QuestionAgain questionAgain;
    private ConstructionAdapter constructionAdapter;
    private List<ConstructionState> constructionStateList;
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        binding_main = FragmentMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        roomCode = bundle.get("roomCode").toString();
        user = (User) bundle.getSerializable(User.class.getSimpleName());
        if (user.getAdvertisement() == null) {
            user.setAdvertisement(new Advertisement());
        }
        ConnectRealtimeDatabase.getInstance(this).getRoom(roomCode, this);

        updateView();

        newConstruction = new NewConstruction();
        newConstruction.setMyDialogListener(this);

        addInfoConstruction = new AddInfoConstruction();
        addInfoConstruction.setDialogListenerAddInfo(this);

        advertisementConstruction = new AdvertisementConstruction();
        advertisementConstruction.setOnClickListener(this);

        questionAgain = new QuestionAgain();
        questionAgain.setMyDialogClickListener(this);

        binding.navigationMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.otherPlayers) {
                    binding.drawerLayout.close();
//                    OtherPlayersFragment otherPlayersFragment = new OtherPlayersFragment(user, this, room.getUserMap());
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, otherPlayersFragment).commit();
                } else if (item.getItemId() == R.id.exit) {
                    binding.drawerLayout.close();
                    Intent intent = new Intent(MainActivity.this, MainBottomNavigation.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.me) {
                    binding.drawerLayout.close();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, mainFragment).commit();
                }
                return true;
            }
        });
        mainFragment = new MainFragment(constructionAdapter, roomCode, user);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, mainFragment).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

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
            mainFragment.setConstructionAdapter(constructionAdapter);
            mainFragment.initRecycleView();
        }
    }

    public void menuPlayer() {
        binding.drawerLayout.openDrawer(GravityCompat.START);
    }

    public void addConstruction() {
        newConstruction.show(getSupportFragmentManager(), "newConstruction");
    }

    public void seo() {
        advertisementConstruction.show(getSupportFragmentManager(), "advertisement");
    }

    public void step() {
        questionAgain.show(getSupportFragmentManager(), "questionAgain");
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

    @Override
    public void onDialogClickListener(boolean result) {
        if (result) {
            goToCPlusPlus();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, new LoadingFragment()).commit();
        }
    }

    public void replaceFragment(User user) {
        this.user = user;
        updateView();
        mainFragment.setConstructionAdapter(constructionAdapter);
        mainFragment.setUser(user);
        advertisementConstruction.setHouseAdv(user.getAdvertisement().getHouse());
        advertisementConstruction.setShopAdv(user.getAdvertisement().getShop());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, mainFragment).commit();
    }

    private void goToCPlusPlus() {
        ConnectRealtimeDatabase.getInstance(this).updateUser(roomCode, user.getUid(), user);
        ConnectRealtimeDatabase.getInstance(this).checkRoom(roomCode, user.getUid(), this);
    }

    public void goToPlayerFragment(String name) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, new PlayerFragment()).commit();
    }
}