package com.lifecapable.vehicledriver.owner.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.fragment.NavHostFragment;

import com.lifecapable.vehicledriver.R;

public class ReminderPopup extends DialogFragment {
    Button ok;
    View view;
    TextView days;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.reminder_popup, container, false);
        ok = view.findViewById(R.id.ok);
        days = view.findViewById(R.id.daysLeft);
        Bundle args = getArguments();
        String day = args.getString("days");
        days.setText(day);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        return view;
    }
}
