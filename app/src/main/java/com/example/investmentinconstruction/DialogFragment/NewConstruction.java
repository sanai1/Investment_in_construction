package com.example.investmentinconstruction.DialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

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
    public void onStart() {
        super.onStart();
        RadioGroup radioGroupHouse = getDialog().findViewById(R.id.radioGroupHouse);
        RadioGroup radioGroupShop = getDialog().findViewById(R.id.radioGroupShop);
        CheckBox checkBoxHouse = getDialog().findViewById(R.id.checkBoxHouse);
        CheckBox checkBoxShop = getDialog().findViewById(R.id.checkBoxShop);
        radioGroupHouse.clearCheck();
        radioGroupShop.clearCheck();
        checkBoxHouse.setChecked(false);
        checkBoxShop.setChecked(false);
    }

    @Override
    public void onClick(View v) {
        CheckBox checkBoxHouse = getDialog().findViewById(R.id.checkBoxHouse);
        CheckBox checkBoxShop = getDialog().findViewById(R.id.checkBoxShop);
        RadioGroup radioGroupHouse = getDialog().findViewById(R.id.radioGroupHouse);
        RadioGroup radioGroupShop = getDialog().findViewById(R.id.radioGroupShop);

        boolean house = false, shop = false;
        String typeHouse = "", typeShop = "";

        if ((checkBoxHouse.isChecked() && radioGroupHouse.getCheckedRadioButtonId() != -1) ||
                (checkBoxShop.isChecked() && radioGroupShop.getCheckedRadioButtonId() != -1)) {
            house = true;
            RadioButton radioButtonBrick = getDialog().findViewById(R.id.radioButtonBrick);
            RadioButton radioButtonPanel = getDialog().findViewById(R.id.radioButtonPanel);
            RadioButton radioButtonMonolithic = getDialog().findViewById(R.id.radioButtonMonolithic);

            if (radioButtonBrick.isChecked()) typeHouse = "Brick";
            else if (radioButtonPanel.isChecked()) typeHouse = "Panel";
            else if (radioButtonMonolithic.isChecked()) typeHouse = "Monolithic";

            shop = true;
            RadioButton radioButtonSupermarket = getDialog().findViewById(R.id.radioButtonSupermarket);
            RadioButton radioButtonBakery = getDialog().findViewById(R.id.radioButtonBakery);
            RadioButton radioButtonHardwareStore = getDialog().findViewById(R.id.radioButtonHardwareStore);

            if (radioButtonSupermarket.isChecked()) typeShop = "Supermarket";
            else if (radioButtonBakery.isChecked()) typeShop = "Bakery";
            else if (radioButtonHardwareStore.isChecked()) typeShop = "HardwareStore";

            dialogListenerAdd.onDialogClickListener(house, typeHouse, shop, typeShop);
            dismiss();
        }
    }

    public interface DialogListenerAdd {
        void onDialogClickListener (boolean house, String typeHouse, boolean shop, String typeShop);
    }

    public void setMyDialogListener(DialogListenerAdd listener) {
        dialogListenerAdd = listener;
    }
}