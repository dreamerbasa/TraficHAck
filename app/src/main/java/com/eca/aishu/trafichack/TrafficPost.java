package com.eca.aishu.trafichack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TrafficPost extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText description;
    Posting posting;
    DatabaseReference mDatabase;
    Button post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_post);

        final String firebaseUser=FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        posting=new Posting();
        post = findViewById(R.id.postButton);
        // Spinner element
        final Spinner location = (Spinner) findViewById(R.id.dropLocation);
        // Spinner click listener
        location.setOnItemSelectedListener(this);
        // Spinner element
        final Spinner status = (Spinner) findViewById(R.id.dropStatus);
        // Spinner click listener
        status.setOnItemSelectedListener(this);
        description=findViewById(R.id.description);




        // Creating adapter for spinner
        ArrayAdapter<CharSequence> dataAdapterLoc = ArrayAdapter.createFromResource(this,R.array.locationS,R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> dataAdapterStat = ArrayAdapter.createFromResource(this,R.array.statusS,R.layout.support_simple_spinner_dropdown_item);

        // Drop down layout style - list view with radio button
        dataAdapterLoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterStat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        location.setAdapter(dataAdapterLoc);
        status.setAdapter(dataAdapterStat);

        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String locToDB = location.getItemAtPosition(position).toString();
                posting.setLocation(locToDB);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String statToDB = status.getItemAtPosition(position).toString();
                posting.setStatus(statToDB);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posting.setDescription(description.getText().toString());
                mDatabase.child("postFeed").child(firebaseUser).setValue(posting);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TrafficPost.this,MainActivity.class));
    }
}
