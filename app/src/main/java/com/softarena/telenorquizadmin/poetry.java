package com.softarena.telenorquizadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

public class poetry extends AppCompatActivity {
    Button btn_submit;
    KProgressHUD kProgressHUD;
    EditText ptry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poetry);
        initProgressDialog();
        btn_submit=findViewById(R.id.btn_submit);
        ptry=findViewById(R.id.ptry);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                                sendDataToFB();

                           
            }
        });
    }

    public void sendDataToFB(){
        kProgressHUD.show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Poetry");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> map = new Hashtable<>();
                Map<String, Object> mapq1 = new Hashtable<>();
                mapq1.put("poetry",ptry.getText().toString());

                map.put("poetry", mapq1);



                reference.setValue(mapq1);


                sendDateToFB();
                savedata();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(poetry.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void savedata(){
        kProgressHUD.show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Categories").child("Poetry");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String key = reference.push().getKey();

                Map<String, Object> map = new Hashtable<>();
                Map<String, Object> mapq1 = new Hashtable<>();
                reference.child(key).setValue(map);
                mapq1.put("poetry",ptry.getText().toString());




                reference.child(key).setValue(mapq1);
                ptry.getText().clear();

                sendDateToFB();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(poetry.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void sendDateToFB(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("settings").child("lastupdate");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                kProgressHUD.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(poetry.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initProgressDialog() {
        kProgressHUD = KProgressHUD.create(poetry.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(4)
                .setBackgroundColor(getResources().getColor(R.color.kprogresshud_default_color))
                .setDimAmount(0.5f);
    }
    }
