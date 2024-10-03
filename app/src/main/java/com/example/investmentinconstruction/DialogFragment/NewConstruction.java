package com.example.investmentinconstruction.DialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.example.investmentinconstruction.R;

public class NewConstruction extends DialogFragment implements View.OnClickListener {

    private DialogListenerAdd dialogListenerAdd;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.dialog_add_construction, null);
        view.findViewById(R.id.buttonAddConstruction).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        System.out.println("click");
    }

    public interface DialogListenerAdd {
        void onDialogClickListener ();
    }

    public void setMyDialogListener(DialogListenerAdd listener) {
        dialogListenerAdd = listener;
    }
}
