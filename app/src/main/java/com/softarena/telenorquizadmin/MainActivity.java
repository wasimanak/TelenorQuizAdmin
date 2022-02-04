package com.softarena.telenorquizadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RelativeLayout btn_addques, btn_settings, btn_addpoetry;
    TextView tv_contacts, tv_today, tv_month, tv_total, tv_kcontacts,tv_ktoday, tv_kmonth, tv_ktotal;
    String showad;
    KProgressHUD kProgressHUD;
    int thismonthcounter = 0;
    int todayscounter = 0;
    int totalcounter = 0;

    int kthismonthcounter = 0;
    int ktodayscounter = 0;
    int ktotalcounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initProgressDialog();
        kProgressHUD.show();
        btn_addques = findViewById(R.id.btn_addques);
        tv_contacts = findViewById(R.id.tv_contacts);
        tv_kcontacts = findViewById(R.id.tv_kcontacts);
        tv_today = findViewById(R.id.tv_today);
        tv_ktoday = findViewById(R.id.tv_ktoday);
        tv_ktotal = findViewById(R.id.tv_ktotal);
        tv_total = findViewById(R.id.tv_total);
        tv_kmonth = findViewById(R.id.tv_kmonth);
        tv_month = findViewById(R.id.tv_month);
        btn_settings = findViewById(R.id.btn_settings);
        btn_addpoetry = findViewById(R.id.btn_addpoetry);
        getAnalytic();
        getDataFromDB();
        btn_addques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddQuestionsActivity.class));
            }
        });
        btn_addpoetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, poetry.class));
            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                intent.putExtra("showadd", showad);
                startActivity(intent);
            }
        });
    }

    public void getDataFromDB() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("settings");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.child("showadds").getValue(String.class).equals("true")) {
                        showad = "true";
                    } else {
                        showad = "false";

                    }
                } else {
                    Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
                kProgressHUD.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DatabaseReference contactsreferance = FirebaseDatabase.getInstance().getReference("ContactsUsers");
        contactsreferance.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int countcontact = 0;
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        JsonArray jsonArray = new JsonParser().parse(dataSnapshot.child("Contactsjson").getValue().toString()).getAsJsonArray();
                        countcontact = countcontact + jsonArray.size();


                    }
                    tv_contacts.setText(countcontact + "");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        contactsreferance = FirebaseDatabase.getInstance().getReference("Kalma_Contacts");
        contactsreferance.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int countcontact = 0;
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        JsonArray jsonArray = new JsonParser().parse(dataSnapshot.child("Contactsjson").getValue().toString()).getAsJsonArray();
                        countcontact = countcontact + jsonArray.size();


                    }
                    tv_kcontacts.setText(countcontact + "");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void initProgressDialog() {
        kProgressHUD = KProgressHUD.create(MainActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(4)
                .setBackgroundColor(getResources().getColor(R.color.kprogresshud_default_color))
                .setDimAmount(0.5f);
    }

    public void getAnalytic() {
//         getting user by date
        String currentDate = new MyHelperClass().getCurrentDateYYYYMMDDhhmmsss();
        String splictCurrentDate[] = currentDate.split("-");

        List<Integer> thismonthuser;
        List<Integer> todays;
        List<Integer> totalusers;
        thismonthuser = new ArrayList<>();
        totalusers = new ArrayList<>();
        todays = new ArrayList<>();

        DatabaseReference dataabaseReference = FirebaseDatabase.getInstance().getReference("ContactsUsers");
        dataabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String[] date = dataSnapshot.child("date").getValue(String.class).split("-");
                        if (date[1].equals(splictCurrentDate[1])) {
                            thismonthcounter = thismonthcounter + 1;


                        }
                        if (dataSnapshot.child("date").getValue(String.class).equals(new MyHelperClass().getCurrentDateYYYYMMDDhhmmsss())) {
                            todayscounter = todayscounter + 1;


                        }
                        totalcounter = totalcounter + 1;

                    }
                    tv_today.setText(todayscounter + "");
                    tv_month.setText(thismonthcounter + "");
                    tv_total.setText(totalcounter + "");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        dataabaseReference = FirebaseDatabase.getInstance().getReference("Kalma_Contacts");
        dataabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String[] date = dataSnapshot.child("date").getValue(String.class).split("-");
                        if (date[1].equals(splictCurrentDate[1])) {
                            kthismonthcounter = kthismonthcounter + 1;


                        }
                        if (dataSnapshot.child("date").getValue(String.class).equals(new MyHelperClass().getCurrentDateYYYYMMDDhhmmsss())) {
                            ktodayscounter = ktodayscounter + 1;


                        }
                        ktotalcounter = ktotalcounter + 1;

                    }
                    tv_ktoday.setText(ktodayscounter + "");
                    tv_kmonth.setText(kthismonthcounter + "");
                    tv_ktotal.setText(ktotalcounter + "");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

}