package com.example.investmentinconstruction.Fragments.MainFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.investmentinconstruction.AdapterState.Construction.ConstructionAdapter;
import com.example.investmentinconstruction.LogicClasses.User;
import com.example.investmentinconstruction.MainActivity;
import com.example.investmentinconstruction.R;

public class MainFragment extends Fragment {

    private ConstructionAdapter constructionAdapter;
    private String roomCode;
    private User user;
    private View view;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MainFragment(ConstructionAdapter constructionAdapter, String roomCode, User user) {
        this.constructionAdapter = constructionAdapter;
        this.roomCode = roomCode;
        this.user = user;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public ConstructionAdapter getConstructionAdapter() {
        return constructionAdapter;
    }

    public void setConstructionAdapter(ConstructionAdapter constructionAdapter) {
        this.constructionAdapter = constructionAdapter;
    }

    public void initRecycleView() {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listConstruction);
        recyclerView.setAdapter(constructionAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        TextView textViewNameGame = view.findViewById(R.id.textViewNameGame);
        textViewNameGame.setText(roomCode);

        TextView textViewCountMoney = view.findViewById(R.id.textViewCountMoney);
        textViewCountMoney.setText(user.getProfitFull().toString());

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listConstruction);
        recyclerView.setAdapter(constructionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ImageView imageViewAddConstruction = (ImageView) view.findViewById(R.id.imageViewAddConstruction);
        imageViewAddConstruction.setOnClickListener(v -> {
            ((MainActivity) getActivity()).addConstruction();
        });

        ImageView imageViewSeo = (ImageView) view.findViewById(R.id.imageViewSeo);
        imageViewSeo.setOnClickListener(v -> {
            ((MainActivity) getActivity()).seo();
        });

        ImageView imageViewNavigationMenu = (ImageView) view.findViewById(R.id.imageViewNavigationMenu);
        imageViewNavigationMenu.setOnClickListener(v -> {
            ((MainActivity) getActivity()).menuPlayer();
        });

        Button buttonStep = (Button) view.findViewById(R.id.buttonStep);
        buttonStep.setOnClickListener(v -> {
            ((MainActivity) getActivity()).step();
        });

        return view;
    }
}