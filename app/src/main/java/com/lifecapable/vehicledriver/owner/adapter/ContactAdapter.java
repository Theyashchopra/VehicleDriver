package com.lifecapable.vehicledriver.owner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.datamodel.Contact;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> contactList;
    private Context context;

    public ContactAdapter(List<Contact> contactList, Context context) {
        this.contactList = contactList;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_card,parent,false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactViewHolder holder, int position) {
        Contact curr = contactList.get(position);
        if(curr.getPhoto() != null){
            Glide.with(context)
                    .load(curr.getPhoto())
                    .placeholder(R.drawable.ic_person)
                    .into(holder.photo);
        }
        try{
            holder.name.setText(curr.getName());
            holder.phone.setText(curr.getPhone());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView name,phone;
        CircleImageView photo;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            phone = itemView.findViewById(R.id.phone);
            name = itemView.findViewById(R.id.name);
        }
    }

    public void filterList(ArrayList<Contact> arr){
        contactList = arr;
        notifyDataSetChanged();
    }
}
