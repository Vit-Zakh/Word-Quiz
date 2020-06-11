package com.sab.wordquiz.models;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;

import com.sab.wordquiz.R;
import com.sab.wordquiz.databinding.LayoutAuthorizationBinding;

public class AuthorizationMechanism {
    public AuthorizationMechanism() {

    }

    public void showDialog(View view) {
        AlertDialog authorizationDialog;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutAuthorizationBinding authorizationBinding = LayoutAuthorizationBinding.inflate(inflater, null, false);

        mBuilder.setView(authorizationBinding.getRoot());
        authorizationDialog = mBuilder.create();
        authorizationBinding.confirmBtn.setOnClickListener(v -> {

            //Authorization stub here:
            if (authorizationBinding.passwordLine.getText().toString().equals("123")) {
                Navigation.findNavController(view).navigate(R.id.action_quizFragment_to_settingsFragment);
                authorizationDialog.hide();
            } else {
                Toast.makeText(view.getContext(), "wrong password", Toast.LENGTH_SHORT).show();
            }
        });
        authorizationBinding.backBtn.setOnClickListener(v -> authorizationDialog.hide());
        authorizationDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        authorizationDialog.show();
    }

}

