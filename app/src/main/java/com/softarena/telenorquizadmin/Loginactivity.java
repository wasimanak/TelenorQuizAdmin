package com.softarena.telenorquizadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Loginactivity extends AppCompatActivity {
EditText username,password;
Button btn_login;
KProgressHUD kProgressHUD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        initProgressDialog();
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        btn_login=findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromDB();
            }
        });


    }
    public void initProgressDialog() {
        kProgressHUD = KProgressHUD.create(Loginactivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(4)
                .setBackgroundColor(getResources().getColor(R.color.kprogresshud_default_color))
                .setDimAmount(0.5f);
    }
    public void getDataFromDB(){
        kProgressHUD.show();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("admin");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.child("username").getValue(String.class).equals(username.getText().toString())&&snapshot.child("password").getValue(String.class).equals(password.getText().toString()))
                    {
                        kProgressHUD.dismiss();
                        startActivity(new Intent(Loginactivity.this,MainActivity.class));
                    }else
                    {
                        kProgressHUD.dismiss();
                        Toast.makeText(Loginactivity.this, "invalid id or password", Toast.LENGTH_SHORT).show();

                    }
                }else {
                    kProgressHUD.dismiss();
                    Toast.makeText(Loginactivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
//                kProgressHUD.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                kProgressHUD.dismiss();

            }
        });

    }

}