package com.lifecapable.vehicledriver.owner.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.Message;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;
import com.lifecapable.vehicledriver.owner.ui.slideshow.OwnerViewDriverFragment;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lifecapable.vehicledriver.owner.ui.slideshow.OwnerViewDriverFragment.driverOwnerData;

public class OwnerDeleteDriverPopup extends DialogFragment {

    Button button;
    Fragment fragment;
    View view;
    int id;
    ProgressBar progressBar;
    public OwnerDeleteDriverPopup(int id, Fragment fragment){
        this.id = id;
        this.fragment = fragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.owner_dialog_delete_driver, container, false);

        button = view.findViewById(R.id.ddelete);
        progressBar = view.findViewById(R.id.ddelete_progress);
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
        Call<Message> call = RestAdapter.createAPI().deleteDriver(id);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                getDialog().dismiss();
                NavHostFragment.findNavController(fragment).navigateUp();
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e("Something went wrong",t.getMessage());
            }
        });
    }
}
