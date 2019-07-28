package com.example.kushagra.finalshms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GetInfoActivity extends AppCompatActivity {
    EditText age,heightt, weightt, calorie, stepp,namee;
    Button save;
    private DatabaseReference reff;
    private FirebaseAuth auth;
    User user;
    public static float mSeries = 0f;
    public static float mSeries1 = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info);
        heightt = findViewById(R.id.heightt);
        weightt = findViewById(R.id.weightt);
        calorie = findViewById(R.id.calorie);
        stepp = findViewById(R.id.stepp);
        namee = findViewById(R.id.namee);
        age = findViewById(R.id.age);
        reff = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();


    }

    public void a(View view) {

        FirebaseUser user = auth.getCurrentUser();
        String ax = user.getUid();
        reff.child("Users").child(ax).child("Name").setValue(namee.getText().toString());
        reff.child("Users").child(ax).child("age").setValue(age.getText().toString());
        reff.child("Users").child(ax).child("height").setValue(heightt.getText().toString());
        reff.child("Users").child(ax).child("weight").setValue(weightt.getText().toString());
        reff.child("Users").child(ax).child("caloriegoal").setValue(calorie.getText().toString());
        reff.child("Users").child(ax).child("stepgoal").setValue(stepp.getText().toString());
        reff.child("Users").child(ax).child("stepgoal").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mSeries = Float.parseFloat(String.valueOf(dataSnapshot.getValue()));
                Log.d("mSeries", (String.valueOf(mSeries)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        reff.child("Users").child(ax).child("caloriegoal").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mSeries = Float.parseFloat(String.valueOf(dataSnapshot.getValue()));
                Log.d("mSeries", (String.valueOf(mSeries)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        reff.child("Users").child(ax).child("weight").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mSeries = Float.parseFloat(String.valueOf(dataSnapshot.getValue()));
                Log.d("mSeries", (String.valueOf(mSeries)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        reff.child("Users").child(ax).child("height").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mSeries = Float.parseFloat(String.valueOf(dataSnapshot.getValue()));
                Log.d("mSeries", (String.valueOf(mSeries)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        reff.child("Users").child(ax).child("age").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mSeries = Float.parseFloat(String.valueOf(dataSnapshot.getValue()));
                Log.d("mSeries", (String.valueOf(mSeries)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        reff.child("Users").child(ax).child("Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mSeries = Float.parseFloat(String.valueOf(dataSnapshot.getValue()));
                Log.d("mSeries", (String.valueOf(mSeries)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        Toast.makeText(GetInfoActivity.this, "data inserted sucessfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
