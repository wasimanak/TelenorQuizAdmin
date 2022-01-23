package com.softarena.telenorquizadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {
    Switch sw_adds;
    KProgressHUD kProgressHUD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initProgressDialog();
        sw_adds=findViewById(R.id.sw_adds);
       if (getIntent().getStringExtra("showadd").equals("true"))
       {
           sw_adds.setChecked(true);
       }else {
           sw_adds.setChecked(false);
       }

        sw_adds.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    sendDataToFB("true");
                }else {
                    sendDataToFB("false");
                }
            }
        });
    }
    public void sendDataToFB(String status){
        kProgressHUD.show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("settings").child("showadds");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                reference.setValue(status);

                kProgressHUD.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(SettingsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initProgressDialog() {
        kProgressHUD = KProgressHUD.create(SettingsActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(4)
                .setBackgroundColor(getResources().getColor(R.color.kprogresshud_default_color))
                .setDimAmount(0.5f);
    }
}