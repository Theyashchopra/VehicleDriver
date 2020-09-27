package com.lifecapable.vehicledriver.owner.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.badge.BadgeUtils;
import com.lifecapable.vehicledriver.MainActivity;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteAppointmentPopup extends DialogFragment {

    Button button;
    Fragment fragment;
    View view;
    int id;
    ProgressBar progressBar;
    public DeleteAppointmentPopup(int id, Fragment fragment){
        this.id = id;
        this.fragment = fragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.delete_appointment_popup, container, false);

        button = view.findViewById(R.id.delete);
        progressBar = view.findViewById(R.id.delete_progress);
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
                try {
                    deleteAppt(id,fragment);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    private void deleteAppt(int id, Fragment fragment) throws Exception{
        progressBar.setVisibility(View.VISIBLE);
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<Map> call = o.deleteAppointment(id);
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if(response.isSuccessful()){
                    Map map = response.body();
                    if(map != null){
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), map.get("message").toString(), Toast.LENGTH_SHORT).show();
                        NavHostFragment.findNavController(fragment).popBackStack();
                        getDialog().dismiss();
                    }else{
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
