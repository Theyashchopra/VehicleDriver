package com.lifecapable.vehicledriver.owner.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.lifecapable.vehicledriver.MainActivity;
import com.lifecapable.vehicledriver.R;


public class LogoutPopup extends DialogFragment {

    Button button;
    TextView textView;
    View view;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.deletepopup, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences("owner", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        textView = view.findViewById(R.id.question);
        button = view.findViewById(R.id.delete);
        Dialog dialog = getDialog();
        //Bundle args = getArguments();
        //String text = args.getString("token","");
        //textView.setText(text);
        try {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        }catch (Exception e){/*eat exceptions*/}
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("login",false);
                editor.apply();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getDialog().dismiss();
            }
        });
        return view;
    }
}
