package com.lifecapable.vehicledriver.owner.ui.slideshow;

import android.Manifest;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.ContactAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.Contact;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleOwnerData;
import com.lifecapable.vehicledriver.owner.dialogs.OwnerSelectVehiclePopup;

import java.util.ArrayList;
import java.util.List;

public class GetContactsFragment extends DialogFragment implements ContactAdapter.OnItemContact {
    public interface OnClosePassContact{
        void getAssigned(int pos, Contact curr);
    }

    List<Contact> contactList;
    View view;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    ContactAdapter contactAdapter;
    SearchView searchView;
    int contactposition;
    Contact contact;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_get_contacts, container, false);
        init();
        return view;
    }

    private void init(){
        searchView = view.findViewById(R.id.contact_search);
        contactList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.contact_recycle);
        contactAdapter = new ContactAdapter(contactList,getContext(),GetContactsFragment.this,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(contactAdapter);
        progressBar = view.findViewById(R.id.contactProgress);
        listeners();
        getContacts();
    }

    private void getContacts(){
        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.READ_CONTACTS)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        if(permissionGrantedResponse.getPermissionName().equals(Manifest.permission.READ_CONTACTS)){
                            try {
                                getContactsList();
                            } catch (Exception e) {
                                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(getContext(), "Permission not granted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    private void getContactsList() throws Exception{
        progressBar.setVisibility(View.VISIBLE);
        Cursor cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                            ,null,null,null,null);
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String photo = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
            Contact contact = new Contact();
            contact.setName(name);
            contact.setPhone(phone);
            contact.setPhoto(photo);
            Log.i("Name",name);
            add(contact);
        }
        contactAdapter.notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void filter(String text){
        ArrayList<Contact> arr = new ArrayList<Contact>();
        for(Contact s : contactList){
            if(s.getName().toLowerCase().contains(text.toLowerCase())){
                arr.add(s);
            }
        }
        contactAdapter.filterList(arr);
    }

    private void listeners(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }

    void add(Contact contact){
        contactList.add(contact);
    }

    @Override
    public void getPosition(int pos, Contact curr) {
        contactposition = pos;
        contact = curr;
        getDialog().dismiss();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Fragment f1 = getTargetFragment();
        if (f1 instanceof OwnerSelectVehiclePopup.OnClosePassData)
            ((GetContactsFragment.OnClosePassContact)f1).getAssigned(contactposition,contact);
    }
}