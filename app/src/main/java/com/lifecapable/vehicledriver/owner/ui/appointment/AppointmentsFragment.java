package com.lifecapable.vehicledriver.owner.ui.appointment;

import android.animation.ValueAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.AppointmentOwnerAdapter;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.AppointmentOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.ListAppointmentOwnerData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AppointmentsFragment extends Fragment {

    View root;
    RecyclerView appointmentRecycle;
    Button addappointment;
    TextView appointmentcount;
    SharedPreferences owner;
    ProgressBar progressBar;
    int oid;
    List<AppointmentOwnerData> appointments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_fragment_appointments, container, false);
        appointmentRecycle = root.findViewById(R.id.oappointmentrecycle);
        addappointment = root.findViewById(R.id.oaddappointment);
        appointmentcount = root.findViewById(R.id.oappointmentcount);
        progressBar = root.findViewById(R.id.oappointmentprogress);
        owner = requireActivity().getSharedPreferences("owner",MODE_PRIVATE);
        oid = owner.getInt("id",-1);
        initViews();
        return root;
    }
    private void initViews(){

        addappointment.setOnClickListener(view -> Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.action_nav_appointments_owner_to_nav_AddNewAppointment_owner));

        Call<ListAppointmentOwnerData> call = RestAdapter.createAPI().ogetListAppointment(oid);
        call.enqueue(new Callback<ListAppointmentOwnerData>() {
            @Override
            public void onResponse(@NotNull Call<ListAppointmentOwnerData> call, @NotNull Response<ListAppointmentOwnerData> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Somethings Wrong I can feel it"+response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                ListAppointmentOwnerData res = response.body();
                if(res != null){
                    appointments = new ArrayList<>();
                    appointments.addAll(res.getAppointments());
                    AppointmentOwnerAdapter adapter = new AppointmentOwnerAdapter(appointments,getContext(),AppointmentsFragment.this);
                    appointmentRecycle.setAdapter(adapter);
                    appointmentRecycle.scheduleLayoutAnimation();
                    appointmentRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
                    ValueAnimator animator = new ValueAnimator();
                    animator.setObjectValues(0, appointments.size());
                    animator.addUpdateListener(animation -> appointmentcount.setText(String.valueOf(animation.getAnimatedValue())));
                    animator.setDuration(500);
                    animator.start();
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(@NotNull Call<ListAppointmentOwnerData> call, @NotNull Throwable t) {
                Toast.makeText(getContext(), "Somethings Wrong I can feel it"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}