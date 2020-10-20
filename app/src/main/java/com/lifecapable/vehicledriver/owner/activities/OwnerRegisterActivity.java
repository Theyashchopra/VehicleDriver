package com.lifecapable.vehicledriver.owner.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textfield.TextInputEditText;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.CityModel;
import com.lifecapable.vehicledriver.owner.datamodel.CityModelRoot;
import com.lifecapable.vehicledriver.owner.datamodel.StateModel;
import com.lifecapable.vehicledriver.owner.datamodel.StateModelRoot;
import com.lifecapable.vehicledriver.owner.dialogs.RegisterSuccessPopup;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerRegisterActivity extends AppCompatActivity {

    List<StateModel> stateModelList;
    List<CityModel> cityModelList;
    Spinner state;
    AutoCompleteTextView city;
    List<String> states,cities;
    String stateName,cityName;
    TextInputEditText nameEt,emailEt,passEt,cpassEt,mobileEt,mobile2Et,addresssEt,tehsilEt,pinEt,panEt,gumastaEt,refEt,gstEt;
    ProgressBar progressBar;
    Button registerBtn;
    CheckBox checkBox;
    TextView read;
    SharedPreferences owner;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_activity_register);
        init();
        getStates();
        spinnerListeners();
        readTerms();
    }
    private void init(){
        owner = getSharedPreferences("owner",MODE_PRIVATE);
        editor = owner.edit();
        read = findViewById(R.id.read_terms);
        checkBox = findViewById(R.id.terms);
        cities = new ArrayList<>();
        cityModelList = new ArrayList<>();
        states = new ArrayList<>();
        stateModelList = new ArrayList<>();
        state = findViewById(R.id.state);
        city = findViewById(R.id.city);
        nameEt = findViewById(R.id.name);
        emailEt = findViewById(R.id.email);
        passEt = findViewById(R.id.password);
        cpassEt = findViewById(R.id.confirm);
        mobileEt = findViewById(R.id.mobile);
        mobile2Et = findViewById(R.id.mobile2);
        addresssEt = findViewById(R.id.address);
        tehsilEt = findViewById(R.id.tehsil);
        pinEt = findViewById(R.id.pincode);
        panEt = findViewById(R.id.pan);
       // tanEt = findViewById(R.id.tan);
        gumastaEt = findViewById(R.id.gumasta);
        refEt = findViewById(R.id.refcode);
        gstEt = findViewById(R.id.gst);
        progressBar = findViewById(R.id.progress);
        registerBtn = findViewById(R.id.register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAndRegister();
            }
        });
    }

    private void validateAndRegister(){
        if(!checkBox.isChecked()){
            Toast.makeText(this, "Please accept terms and conditions", Toast.LENGTH_SHORT).show();
            return;
        }
        String name = nameEt.getText().toString().trim();
        String email = emailEt.getText().toString().trim();
        String password = passEt.getText().toString().trim();
        String confirm = cpassEt.getText().toString().trim();
        String mobile = mobileEt.getText().toString().trim();
        String mobile2 = mobile2Et.getText().toString().trim();
        String address = addresssEt.getText().toString().trim();
        String tehsil = tehsilEt.getText().toString().trim();
        String pincode = pinEt.getText().toString().trim();
        String PAN = panEt.getText().toString().trim();
    //    String TAN = tanEt.getText().toString().trim();
        String GST = gstEt.getText().toString().trim();
        String gumasta = gumastaEt.getText().toString().trim();
        String reference_code = refEt.getText().toString().trim();
        cityName = city.getText().toString().trim();
        if(reference_code.isEmpty()){
            reference_code = "";
        }
        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty() || mobile.isEmpty() ||  address.isEmpty()
            || tehsil.isEmpty() || PAN.isEmpty()  || stateName.isEmpty() || cityName.isEmpty()){
            Toast.makeText(this, "You Left something empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mobile2.isEmpty()){
            mobile2 = "";
        }
        if (pincode.isEmpty()){
            pincode = "";
        }
        if (gumasta.isEmpty()){
            gumasta = "";
        }
        if (GST.isEmpty()){
            GST = "";
        }

        if(!password.equals(confirm)){
            Toast.makeText(this, "Password did not match", Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Shake)
                    .duration(2000)
                    .playOn(cpassEt);
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(cpassEt, InputMethodManager.SHOW_IMPLICIT);
            return;
        }else if(mobile.length() != 10){
            mobileEt.setError("Invalid Number");
            YoYo.with(Techniques.Shake)
                    .duration(2000)
                    .playOn(mobileEt);
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mobileEt, InputMethodManager.SHOW_IMPLICIT);
            return;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("email",email);
        map.put("password",password);
        map.put("name",name);
        map.put("mobile",mobile);
        map.put("ip_address",getWifiMacAddress());
        map.put("sub_id",4);   // free tier subscription hard coded
        map.put("full_address",address);
        map.put("pin_code",pincode);
        map.put("pan",PAN);
       // map.put("tan",TAN);
        map.put("gst",GST);
        map.put("gumasta",gumasta);
        map.put("mobile2",mobile2);
        map.put("state",stateName);
        map.put("city",cityName);
        map.put("refcode",reference_code);
        map.put("tehsil",tehsil);
        try {
            registrationCall(map);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(OwnerRegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void registrationCall(Map<String,Object> map) throws Exception{
        progressBar.setVisibility(View.VISIBLE);
        OwnerJsonPlaceHolder op = RestAdapter.createAPI();
        Call<Map> call = op.registerOwner(map);
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if(response.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    Map<String,Object> res = response.body();
                    try{
                        if(!res.get("id").toString().isEmpty()){
                            // registration successful
                            //save the id for future purposes if required
                            editor.putBoolean("login",true);
                            editor.apply();
                            editor.putInt("id",(int)Math.round((double)res.get("id")));
                            editor.apply();
                            RegisterSuccessPopup rg = new RegisterSuccessPopup();
                            rg.show(getSupportFragmentManager(),"success");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(OwnerRegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(OwnerRegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static String getWifiMacAddress() {
        try {
            String interfaceName = "wlan0";
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (!intf.getName().equalsIgnoreCase(interfaceName)){
                    continue;
                }

                byte[] mac = intf.getHardwareAddress();
                if (mac==null){
                    return "";
                }

                StringBuilder buf = new StringBuilder();
                for (byte aMac : mac) {
                    buf.append(String.format("%02X:", aMac));
                }
                if (buf.length()>0) {
                    buf.deleteCharAt(buf.length() - 1);
                }
                return buf.toString();
            }
        } catch (Exception ex) {

        } // for now eat exceptions
        return "";
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
                        state.setSelection(aa.getPosition("Maharashtra"));
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

    private void spinnerListeners(){
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = adapterView.getItemAtPosition(i);
                if(o == null){
                    return;
                }
                stateName = o.toString();
                Log.i("STATE",stateName);
                for(StateModel s : stateModelList){
                    if(s.getName().equals(stateName)){
                        getCities(s.getId());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = adapterView.getItemAtPosition(i);
                if(o == null){
                    return;
                }
                cityName = o.toString();
                Log.i("CITY",cityName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getCities(int id) {
        cityModelList.clear();
        cities.clear();
        OwnerJsonPlaceHolder op = RestAdapter.createAPI();
        Call<CityModelRoot> call = op.getCities(id);
        call.enqueue(new Callback<CityModelRoot>() {
            @Override
            public void onResponse(Call<CityModelRoot> call, Response<CityModelRoot> response) {
                if(response.isSuccessful()){
                    CityModelRoot cty = response.body();
                    cityModelList = cty.getCities();
                    if(cityModelList.isEmpty()){
                        cityName = "";
                    }
                    for(CityModel c : cityModelList){
                        cities.add(c.getName());
                    }
                    try{
                        ArrayAdapter<String> aa = new ArrayAdapter<String>(OwnerRegisterActivity.this,android.R.layout.simple_spinner_item,cities);
                        aa.setDropDownViewResource(android.R.layout.simple_list_item_1);
                        city.setAdapter(aa);
                    }catch (Exception e){

                    }
                }
            }

            @Override
            public void onFailure(Call<CityModelRoot> call, Throwable t) {

            }
        });
    }

    private void readTerms(){
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri uri = Uri.parse("http://rentbygps.epizy.com/hireonmap/term_of_service.html"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(OwnerRegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}