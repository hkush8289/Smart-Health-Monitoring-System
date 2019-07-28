package com.example.kushagra.finalshms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SleepSuggestion extends AppCompatActivity {
    private TextView hei, wei, agee,sstatus;
    private FirebaseAuth auth;
    DatabaseReference reff = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    String userKey = user.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_suggestion);
        getSupportActionBar().setTitle("Sleep Suggestion");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth = FirebaseAuth.getInstance();
        hei = findViewById(R.id.hei);
        sstatus = findViewById(R.id.sstatus);
        wei = findViewById(R.id.wei);
        agee = findViewById(R.id.agee);
        if (auth.getCurrentUser() != null) {
            reff.child("Users").child(userKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String ageStr = String.valueOf(dataSnapshot.child("age").getValue());
                    String heightStr = String.valueOf(dataSnapshot.child("height").getValue());
                    String weightStr = String.valueOf(dataSnapshot.child("weight").getValue());
                    hei.setText(heightStr+" cm");
                    agee.setText(ageStr+" yr");
                    wei.setText(weightStr+" kg");
                    float ageValue = Float.parseFloat(ageStr);
                    SLEEP(ageValue);

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
        InfoDialogSleep infoDialog = new InfoDialogSleep();
        infoDialog.show(getSupportFragmentManager(), "info dialog sleep");
    }
    private void SLEEP(float age) {
        String ageLabel = "";

        if (Float.compare(age, 13f) <= 0) {
            ageLabel = getString(R.string.Teenagers);
        } else if (Float.compare(age, 13f) > 0 && Float.compare(age, 17f) <= 0) {
            ageLabel = getString(R.string.Younger_Adults);
        } else if (Float.compare(age, 17f) > 0 && Float.compare(age, 25f) <= 0) {
            ageLabel = getString(R.string.Adults);
        } else if (Float.compare(age, 25f) > 0 && Float.compare(age, 65f) <= 0) {
            ageLabel = getString(R.string.Older_Adults);
            }
        String a= "" + age;
        sstatus.setText(ageLabel);
    }
}
