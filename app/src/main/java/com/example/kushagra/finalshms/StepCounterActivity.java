package com.example.kushagra.finalshms;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StepCounterActivity extends AppCompatActivity implements SensorEventListener, StepListener {
    private TextView textView, sstep;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "";
    private int numSteps;
    TextView TvSteps;
    Button BtnStart, BtnStop;
    private FirebaseAuth auth;
    DatabaseReference reff = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    String userKey = user.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);
        getSupportActionBar().setTitle("Step Counter");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        TvSteps = findViewById(R.id.tv_steps);
        sstep = findViewById(R.id.sstep);
        BtnStart = findViewById(R.id.btn_start);
        BtnStop = findViewById(R.id.btn_stop);
        BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                numSteps = 0;
                sensorManager.registerListener(StepCounterActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
                Toast.makeText(StepCounterActivity.this, "Pedometer Started", Toast.LENGTH_LONG).show();

            }
        });

        BtnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                sensorManager.unregisterListener(StepCounterActivity.this);
                Toast.makeText(StepCounterActivity.this, "Pedometer Stopped", Toast.LENGTH_LONG).show();

            }
        });
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            reff.child("Users").child(userKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String goalStr = String.valueOf(dataSnapshot.child("stepgoal").getValue());
                    sstep.setText(goalStr);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }
    @Override
    public void onAccuracyChanged (Sensor sensor,int i){

    }

    @Override
    public void onSensorChanged (SensorEvent event){
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step ( long timeNs){
        numSteps++;
        TvSteps.setText(TEXT_NUM_STEPS + numSteps);
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
       Stepinfo stepinfo = new Stepinfo();
       stepinfo.show(getSupportFragmentManager(), "info dialog");
    }
}
