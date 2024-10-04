package com.example.investmentinconstruction.DialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import com.example.investmentinconstruction.R;

public class QuestionAgain extends DialogFragment implements View.OnClickListener {

    private OnDialogClickListenerQuestionAgain onDialogClickListenerQuestionAgain;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.dialog_question, null);
        view.findViewById(R.id.buttonConfirm).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        onDialogClickListenerQuestionAgain.onDialogClickListener(true);
        dismiss();
    }

    public interface OnDialogClickListenerQuestionAgain {
        void onDialogClickListener(boolean result);
    }

    public void setMyDialogClickListener(OnDialogClickListenerQuestionAgain listener) {
        onDialogClickListenerQuestionAgain = listener;
    }
}