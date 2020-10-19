package com.lifecapable.vehicledriver.owner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.datamodel.Contact;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    public interface OnItemContact {
        void getPosition(int pos,Contact curr); //pass any things
    }

    private List<Contact> contactList;
    private Context context;
    private DialogFragment dialogFragment;
    OnItemContact onItemClick;


    public ContactAdapter(List<Contact> contactList, Context context, DialogFragment dialogFragment, OnItemContact onItemClick) {
        this.contactList = contactList;
        this.context = context;
        this.dialogFragment = dialogFragment;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_card,parent,false);
        return new ContactViewHolder(view,onItemClick);
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
            holder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.getPosition(position, curr);
                }
            });
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
        RelativeLayout rl;
        OnItemContact onItemContact;
        public ContactViewHolder(@NonNull View itemView,OnItemContact onItemContact) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            phone = itemView.findViewById(R.id.phone);
            name = itemView.findViewById(R.id.name);
            rl = itemView.findViewById(R.id.rl);
            this.onItemContact = onItemContact;
        }
    }

    public void filterList(ArrayList<Contact> arr){
        contactList = arr;
        notifyDataSetChanged();
    }
}
