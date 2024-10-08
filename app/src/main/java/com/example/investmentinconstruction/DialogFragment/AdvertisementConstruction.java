package com.example.investmentinconstruction.DialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.investmentinconstruction.R;

public class AdvertisementConstruction extends DialogFragment implements View.OnClickListener {
    private DialogListenerAdvertisement dialogListenerAdvertisement;
    private Integer houseAdv;
    private Integer shopAdv;

    public AdvertisementConstruction() {
        this.houseAdv = 0;
        this.shopAdv = 0;
    }

    public Integer getHouseAdv() {
        return houseAdv;
    }

    public void setHouseAdv(Integer houseAdv) {
        this.houseAdv = houseAdv;
    }

    public Integer getShopAdv() {
        return shopAdv;
    }

    public void setShopAdv(Integer shopAdv) {
        this.shopAdv = shopAdv;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savesInstanceState) {
        View view = layoutInflater.inflate(R.layout.dialog_advertisement, null);
        view.findViewById(R.id.buttonSaveAdvertisement).setOnClickListener(this);

        return view;
    }

    public void onStart() {
        super.onStart();
        EditText editTextHouse = getDialog().findViewById(R.id.editTextNumberHouseAdvertisement);
        EditText editTextShop = getDialog().findViewById(R.id.editTextNumberShopAdvertisement);

        if (houseAdv == 0){
            editTextHouse.setText("");
        } else {
            editTextHouse.setText(String.valueOf(houseAdv));
        }
        if (shopAdv == 0) {
            editTextShop.setText("");
        } else {
            editTextShop.setText(String.valueOf(shopAdv));
        }
    }

    @Override
    public void onClick(View v) {
        EditText editTextHouse = getDialog().findViewById(R.id.editTextNumberHouseAdvertisement);
        EditText editTextShop = getDialog().findViewById(R.id.editTextNumberShopAdvertisement);

        if (!editTextHouse.getText().toString().isEmpty() && !editTextShop.getText().toString().isEmpty()) {
            houseAdv = Integer.valueOf(editTextHouse.getText().toString());
            shopAdv = Integer.valueOf(editTextShop.getText().toString());
            dialogListenerAdvertisement.onDialogClickListener(houseAdv, shopAdv);
            dismiss();
        }
    }

    public interface DialogListenerAdvertisement {
        void onDialogClickListener (Integer housesAdvertisement, Integer shopsAdvertisement);
    }

    public void setOnClickListener(DialogListenerAdvertisement listener) {
        dialogListenerAdvertisement = listener;
    }

}