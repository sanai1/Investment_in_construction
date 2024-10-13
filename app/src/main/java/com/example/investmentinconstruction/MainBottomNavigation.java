package com.example.investmentinconstruction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.example.investmentinconstruction.Authorization.WelcomeActivity;
import com.example.investmentinconstruction.Fragments.BottomMenu.FriendsFragment;
import com.example.investmentinconstruction.Fragments.BottomMenu.HomeFragment;
import com.example.investmentinconstruction.Fragments.BottomMenu.SettingsFragment;
import com.example.investmentinconstruction.Room.CreateRoomActivity;
import com.example.investmentinconstruction.Room.EnterRoomActivity;
import com.example.investmentinconstruction.databinding.MainLayoutBottomnavigationmenuBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MainBottomNavigation extends AppCompatActivity {

    private MainLayoutBottomnavigationmenuBinding binding;
    static final int GALLERY_REQUEST = 1;
    final String fileName = "imageUser.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MainLayoutBottomnavigationmenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.item_friends) {
            selectedFragment = new FriendsFragment();
        } else if (itemId == R.id.item_home) {
            selectedFragment = new HomeFragment();
        } else if (itemId == R.id.item_settings) {
            selectedFragment = new SettingsFragment();
        }
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        }
        return true;
    };

    public void goToEnterRoom() {
        Intent intent = new Intent(MainBottomNavigation.this, EnterRoomActivity.class);
        startActivity(intent);
    }

    public void goToCreateRoom() {
        Intent intent = new Intent(MainBottomNavigation.this, CreateRoomActivity.class);
        startActivity(intent);
    }

    public void exit() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainBottomNavigation.this, WelcomeActivity.class);
        startActivity(intent);
    }

    public void picture() {
        Intent photoIntent = new Intent(Intent.ACTION_PICK);
        photoIntent.setType("image/*");
        startActivityForResult(photoIntent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        System.out.println("111111111111111111111111");
        Bitmap bitmap = null;
        ImageView imageView = (ImageView) findViewById(R.id.imageViewProfilePicture);

        if (requestCode == GALLERY_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = imageReturnedIntent.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    byte[] bytes = getByteArrayFromBitmap(bitmap);
                    boolean writeFile = writeByteArray(bytes);
                    System.out.println(writeFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(bitmap);
            }
        }
        System.out.println("22222222222222222222");
    }

    private byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        System.out.println("33333333333333333333");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        System.out.println("4444444444444444444");
        return bos.toByteArray();
    }

    public Bitmap getBitmapFromByteArray(byte[] bitmap) {
        return BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
    }

    private boolean writeByteArray(@NonNull byte[] bytes) {
        try {
            OutputStream out = openFileOutput(fileName, 0);
//            out.write(bytes);
//            out.close();
            String str = "";
            System.out.println(bytes.length);
            System.out.println(str);
            System.out.println("++++++++++++++++++++++++++++++");
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public byte[] readByteFile() {
        try {
            InputStream inputStream = openFileInput(fileName);

//            Path path = Paths.get();
//            byte[] bytes = Files.readAllBytes(path);
//            System.out.println(bytes.length);
            return null;
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}