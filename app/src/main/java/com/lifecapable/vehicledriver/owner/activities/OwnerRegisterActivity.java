package com.lifecapable.vehicledriver.owner.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.StateModel;
import com.lifecapable.vehicledriver.owner.datamodel.StateModelRoot;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerRegisterActivity extends AppCompatActivity {

    List<StateModel> stateModelList;
    Spinner state,city;
    List<String> states;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_register);
        init();
        getStates();
    }
    private void init(){
        states = new ArrayList<>();
        stateModelList = new ArrayList<>();
        state = findViewById(R.id.state);
        city = findViewById(R.id.city);
    }
    private void getStates(){
        OwnerJsonPlaceHolder placeHolder = RestAdapter.createAPI();
        Call<StateModelRoot> call = placeHolder.getStateList();
        call.enqueue(new Callback<StateModelRoot>() {
            @Override
            public void onResponse(Call<StateModelRoot> call, Response<StateModelRoot> response) {
                if(response.isSuccessful()){
                    StateModelRoot st = response.body();
                    stateModelList = st.getStates();
                    for (StateModel s : stateModelList){
                        states.add(s.getName());
                    }
                    try{
                        ArrayAdapter<String> aa = new ArrayAdapter<String>(OwnerRegisterActivity.this,android.R.layout.simple_spinner_item,states);
                        aa.setDropDownViewResource(android.R.layout.simple_list_item_1);
                        state.setAdapter(aa);
                    }catch (Exception e){

                    }
                }
            }

            @Override
            public void onFailure(Call<StateModelRoot> call, Throwable t) {
                Toast.makeText(OwnerRegisterActivity.this, "No internet Access", Toast.LENGTH_SHORT).show();
            }
        });
    }
}