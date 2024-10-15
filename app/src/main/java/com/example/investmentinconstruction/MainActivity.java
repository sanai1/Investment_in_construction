package com.example.investmentinconstruction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.example.investmentinconstruction.AdapterState.Construction.ConstructionAdapter;
import com.example.investmentinconstruction.AdapterState.Construction.ConstructionState;
import com.example.investmentinconstruction.AdapterState.FinalGame.FinalGameAdapter;
import com.example.investmentinconstruction.AdapterState.FinalGame.FinalGameState;
import com.example.investmentinconstruction.AdapterState.OtherPlayers.PlayerAdapter;
import com.example.investmentinconstruction.AdapterState.OtherPlayers.PlayerState;
import com.example.investmentinconstruction.DialogFragment.AddInfoConstruction;
import com.example.investmentinconstruction.DialogFragment.AdvertisementConstruction;
import com.example.investmentinconstruction.DialogFragment.NewConstruction;
import com.example.investmentinconstruction.DialogFragment.QuestionAgain;
import com.example.investmentinconstruction.Fragments.MainFragments.FinalGameFragment;
import com.example.investmentinconstruction.Fragments.MainFragments.LoadingFragment;
import com.example.investmentinconstruction.Fragments.MainFragments.MainFragment;
import com.example.investmentinconstruction.Fragments.MainFragments.OtherPlayersFragment;
import com.example.investmentinconstruction.Fragments.MainFragments.PlayerFragment;
import com.example.investmentinconstruction.LogicClasses.Advertisement;
import com.example.investmentinconstruction.LogicClasses.House;
import com.example.investmentinconstruction.LogicClasses.Room;
import com.example.investmentinconstruction.LogicClasses.Shop;
import com.example.investmentinconstruction.LogicClasses.User;
import com.example.investmentinconstruction.databinding.ActivityMainBinding;
import com.example.investmentinconstruction.databinding.FragmentMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private Room room;
    protected NewConstruction newConstruction;
    protected AddInfoConstruction addInfoConstruction;
    protected AdvertisementConstruction advertisementConstruction;
    protected QuestionAgain questionAgain;
    private ConstructionAdapter constructionAdapter;
    private List<ConstructionState> constructionStateList;
    private MainFragment mainFragment;

    public void setRoom(Room room) {
        this.room = room;
    }

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
                    OtherPlayersFragment otherPlayersFragment = new OtherPlayersFragment(room.getUserMap(), user.getUid());
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, otherPlayersFragment).commit();
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
        if (user.getHouseMap() != null) {
            Set<String> integerSet = user.getHouseMap().keySet();
            for (String string : integerSet) {
                if (user.getHouseMap().get(string).getTypeHouse().equals("Brick")) picture = R.drawable.house_brick;
                else if (user.getHouseMap().get(string).getTypeHouse().equals("Panel")) picture = R.drawable.house_panel;
                else if (user.getHouseMap().get(string).getTypeHouse().equals("Monolithic")) picture = R.drawable.house_monolithic;

                constructionStateList.add(new ConstructionState(user.getHouseMap().get(string).getHid(), user.getHouseMap().get(string).getTypeHouse(), user.getHouseMap().get(string).getPercent()+ "%", picture));
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
                constructionStateList.add(new ConstructionState(user.getShopMap().get(string).getSid(), user.getShopMap().get(string).getTypeShop(), user.getShopMap().get(string).getPercent() + "%", picture));
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
        if (house) {
            if (user.getHouseMap() == null){
                user.setHouseMap(new HashMap<>());
            }
            String hid = String.valueOf(10000 + (int) (Math.random() * 90000));
            user.getHouseMap().put(hid, new House(hid, typeHouse, room.getStartPeriod() + room.getNumberStep(), 0, 0, 0, 0, 0));
        }
        if (shop) {
            if (user.getShopMap() == null) {
                user.setShopMap(new HashMap<>());
            }
            String sid = String.valueOf(10000 + (int) (Math.random() * 90000));
            user.getShopMap().put(sid, new Shop(sid, typeShop, room.getStartPeriod() + room.getNumberStep(), 0, 0));
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
        ConnectRealtimeDatabase.getInstance(this).getRoom(roomCode, this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, mainFragment).commit();
    }

    private void goToCPlusPlus() {
        ConnectRealtimeDatabase.getInstance(this).updateUser(roomCode, user.getUid(), user);
        ConnectRealtimeDatabase.getInstance(this).checkRoom(roomCode, user.getUid(), this);
    }

    public void goToMainFragment() {
        ConnectRealtimeDatabase.getInstance(this).getRoom(roomCode, this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, mainFragment).commit();
    }

    public void goToPlayerFragment(String name) {
        User userInfo = room.getUserMap().get(name);
        List<PlayerState> playerStateList = new ArrayList<>();
        int picture = 0;
        if (userInfo.getHouseMap() != null) {
            Set<String> integerSet = userInfo.getHouseMap().keySet();
            for (String string : integerSet) {
                if (userInfo.getHouseMap().get(string).getTypeHouse().equals("Brick")) picture = R.drawable.house_brick;
                else if (userInfo.getHouseMap().get(string).getTypeHouse().equals("Panel")) picture = R.drawable.house_panel;
                else if (userInfo.getHouseMap().get(string).getTypeHouse().equals("Monolithic")) picture = R.drawable.house_monolithic;

                playerStateList.add(new PlayerState(userInfo.getHouseMap().get(string).getHid(), userInfo.getHouseMap().get(string).getTypeHouse(), userInfo.getHouseMap().get(string).getPercent() + "%", picture));
                playerStateList.get(playerStateList.size() - 1).setSoldApartment(userInfo.getHouseMap().get(string).getSoldApartments());
            }
        }
        if (userInfo.getShopMap() != null) {
            Set<String> stringSet = userInfo.getShopMap().keySet();
            for (String string : stringSet) {
                if (userInfo.getShopMap().get(string).getTypeShop().equals("Supermarket")) picture = R.drawable.shop_supermarket;
                else if (userInfo.getShopMap().get(string).getTypeShop().equals("Bakery")) picture = R.drawable.shop_bakery;
                else if (userInfo.getShopMap().get(string).getTypeShop().equals("HardwareStore")) picture = R.drawable.shop_hardware;

                playerStateList.add(new PlayerState(userInfo.getShopMap().get(string).getSid(), userInfo.getShopMap().get(string).getTypeShop(), userInfo.getShopMap().get(string).getPercent() + "%", picture));
            }
        }
        if (!playerStateList.isEmpty()) {
            PlayerAdapter playerAdapter = new PlayerAdapter(playerStateList);
            PlayerFragment playerFragment = new PlayerFragment(playerAdapter, room.getUserMap().get(name));
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, playerFragment).commit();
        } else {
            Toast.makeText(this, "The beggar player!", Toast.LENGTH_SHORT).show();
        }
    }

    public void goHome() {
        Intent intent = new Intent(MainActivity.this, MainBottomNavigation.class);
        startActivity(intent);
    }

    public void finalGame(Room room) {
        String uid = user.getUid();
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (User user1 : room.getUserMap().values()) {
            hashMap.put(user1.getUid(), user1.getProfitFull());
        }
        hashMap.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed());

        List<FinalGameState> pairList = new ArrayList<>();
        Set<String> stringSet = hashMap.keySet();
        for (String string : stringSet) {
            if (string.equals(uid)) {
                pairList.add(new FinalGameState("YOU", hashMap.get(string), 0));
            } else {
                pairList.add(new FinalGameState(room.getUserMap().get(string).getName(), hashMap.get(string), 0));
            }
        }

        FinalGameAdapter finalGameAdapter = new FinalGameAdapter(pairList);
        FinalGameFragment finalGameFragment = new FinalGameFragment(finalGameAdapter);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, finalGameFragment).commit();
    }

}