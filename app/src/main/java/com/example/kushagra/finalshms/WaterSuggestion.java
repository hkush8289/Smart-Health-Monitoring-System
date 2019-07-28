package com.example.kushagra.finalshms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WaterSuggestion extends AppCompatActivity {
    private TextView wat, litre;
    private FirebaseAuth auth;
    DatabaseReference reff = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    String userKey = user.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_suggestion);
        getSupportActionBar().setTitle("Water Intake Suggestion");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth = FirebaseAuth.getInstance();
        wat = findViewById(R.id.wat);
        litre = findViewById(R.id.litre);
        if (auth.getCurrentUser() != null) {
            reff.child("Users").child(userKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String weightStr = String.valueOf(dataSnapshot.child("weight").getValue());
                    wat.setText(weightStr + " Kg");
                    float weightValue = Float.parseFloat(weightStr);
                    float ans = weightValue/30;
                    String a= "" + String.format("%.2f",ans);
                    litre.setText(a);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        CreateMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        return MenuChoice(item);
    }

    public void CreateMenu(Menu menu) {
        MenuItem menu1 = menu.add(0, 0, 0, "info");
        menu1.setIcon(R.drawable.ic_action_info);
        menu1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

    }

    public boolean MenuChoice(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
            case 0:
                openDialog();
                return true;

        }
        return false;
    }

    public void openDialog() {
        WaterInfo waterInfo = new WaterInfo();
        waterInfo.show(getSupportFragmentManager(), "info dialog sleep");
    }
}
