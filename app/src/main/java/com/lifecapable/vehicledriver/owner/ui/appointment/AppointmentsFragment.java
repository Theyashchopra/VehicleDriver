package com.lifecapable.vehicledriver.owner.ui.appointment;

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
import android.widget.TextView;
import android.widget.Toast;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.AppointmentOwnerAdapter;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.AppointmentOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.ListAppointmentOwnerData;

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
    int oid;
    List<AppointmentOwnerData> appointments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_appointments, container, false);
        appointmentRecycle = root.findViewById(R.id.oappointmentrecycle);
        addappointment = root.findViewById(R.id.oaddappointment);
        appointmentcount = root.findViewById(R.id.oappointmentcount);
        owner = getActivity().getSharedPreferences("owner",MODE_PRIVATE);
        oid = owner.getInt("id",-1);
        initViews();
        return root;
    }
    private void initViews(){

        addappointment.setOnClickListener(view -> Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_nav_appointments_owner_to_nav_AddNewAppointment_owner));

        Call<ListAppointmentOwnerData> call = RestAdapter.createAPI().ogetListAppointment(oid);
        call.enqueue(new Callback<ListAppointmentOwnerData>() {
            @Override
            public void onResponse(Call<ListAppointmentOwnerData> call, Response<ListAppointmentOwnerData> response) {
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
                    appointmentRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
                    appointmentcount.setText(appointments.size()+"");
                }

            }

            @Override
            public void onFailure(Call<ListAppointmentOwnerData> call, Throwable t) {
                Toast.makeText(getContext(), "Somethings Wrong I can feel it"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}