package com.example.investmentinconstruction.DialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.investmentinconstruction.R;

public class AddInfoConstruction extends DialogFragment implements View.OnClickListener {
// TODO: добавить поля отвечающение за editText и если они не пустые выводить сразу их значение в editText на экран
    private DialogListenerAddInfo dialogListenerAddInfo;
    private String typeCID;
    private String progressBuild;
    private String fullApartment;
    private String soldApartment;
    private String key;
    private Integer number;

    public AddInfoConstruction() {}

    public String getTypeCID() {
        return typeCID;
    }

    public void setTypeCID(String typeCID) {
        this.typeCID = typeCID;
    }

    public String getFullApartment() {
        return fullApartment;
    }

    public void setFullApartment(String fullApartment) {
        this.fullApartment = fullApartment;
    }

    public String getProgressBuild() {
        return progressBuild;
    }

    public void setProgressBuild(String progressBuild) {
        this.progressBuild = progressBuild;
    }

    public String getSoldApartment() {
        return soldApartment;
    }

    public void setSoldApartment(String soldApartment) {
        this.soldApartment = soldApartment;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.dialog_add_info_construction, null);
        view.findViewById(R.id.buttonSaveInfo).setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        TextView textViewTypeCID = getDialog().findViewById(R.id.textViewTypeCID);
        TextView textViewProgressBuild = getDialog().findViewById(R.id.textViewProgressBuild);
        TextView textViewFullApartment = getDialog().findViewById(R.id.textViewFullApartment);
        TextView textViewSoldApartment = getDialog().findViewById(R.id.textViewSoldApartment);

        textViewTypeCID.setText(number.toString());
        textViewProgressBuild.setText(progressBuild);
        textViewFullApartment.setText(fullApartment);
        textViewSoldApartment.setText(soldApartment);
    }

    @Override
    public void onClick(View v) {
        EditText editTextCountSale = getDialog().findViewById(R.id.editTextNumberCountSale);
        EditText editTextPrice = getDialog().findViewById(R.id.editTextNumberPrice);
        if (!editTextCountSale.getText().toString().isEmpty() && !editTextPrice.getText().toString().isEmpty()) {
            dialogListenerAddInfo.onDialogClickListener(Integer.valueOf(editTextCountSale.getText().toString()), Integer.valueOf(editTextPrice.getText().toString()), key);
            editTextCountSale.setText("");
            editTextPrice.setText("");
            dismiss();
        }
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public interface DialogListenerAddInfo {
        void onDialogClickListener(Integer countSale, Integer price, String key);
    }

    public void setDialogListenerAddInfo(DialogListenerAddInfo listener) {
        dialogListenerAddInfo = listener;
    }
}