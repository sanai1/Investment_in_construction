package com.example.investmentinconstruction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

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
import com.example.investmentinconstruction.DialogFragment.QuestionAgain;
import com.example.investmentinconstruction.Fragments.LoadingFragment;
import com.example.investmentinconstruction.Fragments.MainFragment;
import com.example.investmentinconstruction.LogicClasses.Advertisement;
import com.example.investmentinconstruction.LogicClasses.House;
import com.example.investmentinconstruction.LogicClasses.Room;
import com.example.investmentinconstruction.LogicClasses.Shop;
import com.example.investmentinconstruction.LogicClasses.User;
import com.example.investmentinconstruction.databinding.ActivityMainBinding;
import com.example.investmentinconstruction.databinding.FragmentMainBinding;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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
                if (item.getItemId() == R.id.player1) {
                    binding.drawerLayout.close();
//                    Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
//                    startActivity(intent);
                } else if (item.getItemId() == R.id.exit) {
                    binding.drawerLayout.close();
                    Intent intent = new Intent(MainActivity.this, MainBottomNavigation.class);
                    startActivity(intent);
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
//        testJSON();
        advertisementConstruction.show(getSupportFragmentManager(), "advertisement");
    }

    public void step() {
        // TODO: сделать DialogFragment для уточнения намерений сделать шаг
//        test(); // необходимо запустить для получения String - пример входных данных от java к c++
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
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, mainFragment).commit();
    }

    private void goToCPlusPlus() {
        ConnectRealtimeDatabase.getInstance(this).updateUser(roomCode, user.getUid(), user);
        ConnectRealtimeDatabase.getInstance(this).checkRoom(roomCode, user.getUid(), this);
    }

    private void test() {
        Map<String, User> userMap = new HashMap<>();

        Map<String, House> houseMap_1 = new HashMap<>();
        Map<String, Shop> shopMap_1 = new HashMap<>();

        Map<String, House> houseMap_2 = new HashMap<>();
        Map<String, Shop> shopMap_2 = new HashMap<>();

        houseMap_1.put("436", new House("436", "Panel", 2, 40000, 0, 10, 0));
        houseMap_1.put("135", new House("135", "Monolithic", 1, 0, 0, 0, 0));

        houseMap_2.put("146", new House("146", "Brick", 3, 50000, 5, 5, 200000));
        houseMap_2.put("945", new House("945", "Panel", 2, 35000, 0, 7, 0));

        shopMap_1.put("425", new Shop("425", "Bakery", 1, 0));
        shopMap_1.put("154", new Shop("154", "Supermarket", 3, 0));

        shopMap_2.put("726", new Shop("726", "HardwareStore", 1, 45000));
        shopMap_2.put("426", new Shop("426", "Supermarket", 2, 0));

        String uid_1 = UUID.randomUUID().toString();
        String uid_2 = UUID.randomUUID().toString();

        userMap.put(uid_1, new User(uid_1, "Arbat", 0, new Advertisement(1000, 750), houseMap_1, shopMap_1));
        userMap.put(uid_2, new User(uid_2, "Sokolniki", 0, new Advertisement(800, 1100), houseMap_2, shopMap_2));

        Room room = new Room(15641, 2, 2, 1, userMap);

        ConnectRealtimeDatabase.getInstance(this).testString(room);
    }

    private void testJSON() {
        String json = "{nowPeople=2, cntPeople=2, roomCode=15641, userMap={8f7f1e64-bc06-4611-9fa9-8b3fe85218f2={uid=8f7f1e64-bc06-4611-9fa9-8b3fe85218f2, shopMap={154={duration=5, startPeriod=3, priceMonth=45000, soldProfit=0, sid=154, typeShop=Supermarket}, 425={duration=5, startPeriod=1, priceMonth=4, soldProfit=0, sid=425, typeShop=Bakery}}, houseMap={135={duration=5, startPeriod=1, hid=135, salePrice=0, typeHouse=Monolithic, saleApartments=0, countApartments=60, soldApartments=0, priceMonth=5000, soldProfit=0}, 436={duration=7, startPeriod=2, hid=436, salePrice=40000, typeHouse=Panel, saleApartments=10, countApartments=50, soldApartments=0, priceMonth=10000, soldProfit=0}}, district=Arbat, advertisement={shop=750, house=1000}, profitFull=0}, 5388c414-f268-46e2-8fb2-5fbf5a1519bd={uid=5388c414-f268-46e2-8fb2-5fbf5a1519bd, shopMap={426={duration=5, startPeriod=2, priceMonth=45000, soldProfit=0, sid=426, typeShop=Supermarket}, 726={duration=2, startPeriod=1, priceMonth=10000, soldProfit=45000, sid=726, typeShop=HardwareStore}}, houseMap={146={duration=6, startPeriod=3, hid=146, salePrice=50000, typeHouse=Brick, saleApartments=5, countApartments=40, soldApartments=5, priceMonth=7500, soldProfit=200000}, 945={duration=7, startPeriod=2, hid=945, salePrice=35000, typeHouse=Panel, saleApartments=7, countApartments=50, soldApartments=0, priceMonth=10000, soldProfit=0}}, district=Sokolniki, advertisement={shop=1100, house=800}, profitFull=0}}, currentPeriod=1}";
        Room room = null;
        try {
            room = new ObjectMapper().readValue(json, Room.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(room.toString());
    }
}