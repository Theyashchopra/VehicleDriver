package com.lifecapable.vehicledriver.owner.dialogs;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.lifecapable.vehicledriver.R;

public class OwnerImageViewPopup extends DialogFragment {
    View root;
    ImageView imageView;
    Bitmap license;

    public OwnerImageViewPopup(Bitmap license) {
        this.license = license;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_image_view_popup, container, false);
        imageView = root.findViewById(R.id.viewimageiv);

        if(license == null) {
            imageView.setImageResource(R.drawable.ic_no_camera);
        }else{
            imageView.setImageBitmap(license);
        }
        Dialog dialog = getDialog();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        return root;
    }
}
