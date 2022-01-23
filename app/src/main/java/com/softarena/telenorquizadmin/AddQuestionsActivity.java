package com.softarena.telenorquizadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

public class AddQuestionsActivity extends AppCompatActivity {
    Button btn_submit;
    KProgressHUD kProgressHUD;
    EditText et_q1,et_q2,et_q3,et_q4,et_q5,et_a1,et_a2,et_a3,et_a4,et_a5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);
        initProgressDialog();
        btn_submit=findViewById(R.id.btn_submit);
        et_q1=findViewById(R.id.et_q1);
        et_q2=findViewById(R.id.et_q2);
        et_q3=findViewById(R.id.et_q3);
        et_q4=findViewById(R.id.et_q4);
        et_q5=findViewById(R.id.et_q5);
        et_a1=findViewById(R.id.et_a1);
        et_a2=findViewById(R.id.et_a2);
        et_a3=findViewById(R.id.et_a3);
        et_a4=findViewById(R.id.et_a4);
        et_a5=findViewById(R.id.et_a5);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AddQuestionsActivity.this)
                        .setTitle("Alert")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendDataToFB();

                            }
                        })
                        .setMessage("Are you sure to submit Questions")
                       .setNegativeButton("No", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                           }
                       })
                        .show();
            }
        });
    }

    public void sendDataToFB(){
        kProgressHUD.show();
       DatabaseReference reference = FirebaseDatabase.getInstance().getReference("questions");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> map = new Hashtable<>();
                Map<String, Object> mapq1 = new Hashtable<>();
                mapq1.put("question",et_q1.getText().toString());
                mapq1.put("correctans",et_a1.getText().toString());

                Map<String, Object> mapq2 = new Hashtable<>();
                mapq2.put("question",et_q2.getText().toString());
                mapq2.put("correctans",et_a2.getText().toString());

                Map<String, Object> mapq3 = new Hashtable<>();
                mapq3.put("question",et_q3.getText().toString());
                mapq3.put("correctans",et_a3.getText().toString());

                Map<String, Object> mapq4 = new Hashtable<>();
                mapq4.put("question",et_q4.getText().toString());
                mapq4.put("correctans",et_a4.getText().toString());

                Map<String, Object> mapq5 = new Hashtable<>();
                mapq5.put("question",et_q5.getText().toString());
                mapq5.put("correctans",et_a5.getText().toString());

                map.put("question1", mapq1);
                map.put("question2", mapq2);
                map.put("question3", mapq3);
                map.put("question4", mapq4);
                map.put("question5", mapq5);



                reference.setValue(map);

                sendDateToFB();
                savedata();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(AddQuestionsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void savedata(){
        kProgressHUD.show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Categories").child("General Questions");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String key = reference.push().getKey();

                Map<String, Object> map = new Hashtable<>();
                Map<String, Object> mapq1 = new Hashtable<>();
                reference.child(key).setValue(map);
                mapq1.put("question",et_q1.getText().toString());
                mapq1.put("correctans",et_a1.getText().toString());

                Map<String, Object> mapq2 = new Hashtable<>();
                mapq2.put("question",et_q2.getText().toString());
                mapq2.put("correctans",et_a2.getText().toString());

                Map<String, Object> mapq3 = new Hashtable<>();
                mapq3.put("question",et_q3.getText().toString());
                mapq3.put("correctans",et_a3.getText().toString());

                Map<String, Object> mapq4 = new Hashtable<>();
                mapq4.put("question",et_q4.getText().toString());
                mapq4.put("correctans",et_a4.getText().toString());

                Map<String, Object> mapq5 = new Hashtable<>();
                mapq5.put("question",et_q5.getText().toString());
                mapq5.put("correctans",et_a5.getText().toString());

                map.put("question1", mapq1);
                map.put("question2", mapq2);
                map.put("question3", mapq3);
                map.put("question4", mapq4);
                map.put("question5", mapq5);



                reference.child(key).setValue(map);

                sendDateToFB();
                finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(AddQuestionsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void sendDateToFB(){
        kProgressHUD.show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("settings").child("lastupdate");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                reference.setValue(currentDate());

                kProgressHUD.dismiss();
                finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(AddQuestionsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public String currentDate(){
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public void initProgressDialog() {
        kProgressHUD = KProgressHUD.create(AddQuestionsActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(4)
                .setBackgroundColor(getResources().getColor(R.color.kprogresshud_default_color))
                .setDimAmount(0.5f);
    }
}