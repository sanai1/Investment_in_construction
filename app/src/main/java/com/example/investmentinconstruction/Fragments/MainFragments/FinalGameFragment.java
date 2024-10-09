package com.example.investmentinconstruction.Fragments.MainFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.investmentinconstruction.AdapterState.FinalGame.FinalGameAdapter;
import com.example.investmentinconstruction.MainActivity;
import com.example.investmentinconstruction.R;

public class FinalGameFragment extends Fragment {

    private FinalGameAdapter finalGameAdapter;
    private View view;

    public FinalGameFragment(FinalGameAdapter finalGameAdapter) {
        this.finalGameAdapter = finalGameAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_final_game, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.listPlayer);
        recyclerView.setAdapter(finalGameAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Button button = view.findViewById(R.id.buttonHome);
        button.setOnClickListener(v -> {
            ((MainActivity) getActivity()).goHome();
        });

        return view;
    }
}