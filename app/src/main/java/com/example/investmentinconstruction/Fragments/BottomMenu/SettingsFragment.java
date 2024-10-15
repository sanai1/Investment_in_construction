package com.example.investmentinconstruction.Fragments.BottomMenu;

import static com.example.investmentinconstruction.R.*;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.investmentinconstruction.MainBottomNavigation;
import com.example.investmentinconstruction.R;

import io.getstream.avatarview.AvatarView;


public class SettingsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        view.findViewById(R.id.imageViewExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainBottomNavigation) getActivity()).exit();
            }
        });

        ImageView imageView = view.findViewById(id.imageViewProfilePicture);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((MainBottomNavigation) getActivity()).picture();
                return true;
            }
        });

//        byte[] bytes = ((MainBottomNavigation) getActivity()).readByteFile();
//        Bitmap bitmap = ((MainBottomNavigation) getActivity()).getBitmapFromByteArray(bytes);
//        imageView.setImageBitmap(bitmap);
//        System.out.println("=================");

        return view;
    }
}