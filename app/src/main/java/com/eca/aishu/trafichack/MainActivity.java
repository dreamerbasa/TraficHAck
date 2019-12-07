package com.eca.aishu.trafichack;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference ref1,ref2;
    FirebaseUser user;
    private Context mContext;

    TextView textView;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final LinearLayout content_activities=(LinearLayout)findViewById(R.id.content_activities);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        // Get the application context
        mContext = getApplicationContext();
        textView=new TextView(mContext);

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        ref1=database.getReference().child("postFeed");
        ref2=database.getReference().child("postFeed").child("");

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items= dataSnapshot.getChildren().iterator();

                Posting posting=new Posting();

                // ViewGroup parent = (ViewGroup) content_activities.getParent();
                //parent.removeView(content_activities);
               // if(content_activities.getChildCount()>0) {
                   // content_activities.removeAllViews();
                //}
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this);//continued after while loop

                while(items.hasNext()){
                    DataSnapshot item=items.next();

                    posting=item.getValue(Posting.class);

                    if (TextUtils.isEmpty(posting.getLocation()) || TextUtils.isEmpty(posting.getDescription()) || TextUtils.isEmpty(posting.getStatus()) || TextUtils.isEmpty(posting.getTime())) ;
                    else {
                        CardView card=new CardView(content_activities.getContext());
                        //Set the CardView layoutParams
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        card.setLayoutParams(params);
                        card.setRadius(9);
                        card.setBackgroundResource(R.drawable.card_style);
                        card.setContentPadding(15, 15, 15, 15);
                        card.setMaxCardElevation(15);
                        card.getPreventCornerOverlap();
                        card.findFocus();
                        card.setPadding(10, 10, 10, 10);
                        card.setCardElevation(9);

                        LinearLayout title_layout = new LinearLayout(card.getContext());
                        title_layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        title_layout.setOrientation(LinearLayout.VERTICAL);

                        TextView tv1 = new TextView(title_layout.getContext());
                        tv1.setLayoutParams(params);
                        tv1.setText(posting.getLocation().toUpperCase() + "\n" + posting.getDescription() + "\n" + posting.getTime() + "\n");
                        tv1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        tv1.setTextColor(Color.BLACK);
                        tv1.getExtendedPaddingBottom();
                        tv1.setGravity(View.TEXT_ALIGNMENT_CENTER);
                        title_layout.addView(tv1);

                        LinearLayout status_layout = new LinearLayout(card.getContext());
                        status_layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        status_layout.setOrientation(LinearLayout.HORIZONTAL);

//                        ImageView imageView = new ImageView(mContext);
//                        imageView.setImageResource(R.drawable.red_btn);
//                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(15, 15);
//                        imageView.setLayoutParams(layoutParams);
//                        status_layout.addView(imageView);


                        TextView tv2 = new TextView(status_layout.getContext());
                        tv2.setLayoutParams(params);
                        tv2.setText(posting.getStatus());
                        tv2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        tv2.setTextColor(Color.BLACK);
                        tv2.getExtendedPaddingTop();

                        status_layout.addView(tv2);

                        card.addView(title_layout);
                        card.addView(status_layout);
                        // Finally, add the CardView in root layout
                        if (card.getParent() != null) {
                            ((ViewGroup) card.getParent()).removeView(card); // <- fix
                        }
                        content_activities.addView(card);


                    }

                }

                //notification
                mBuilder.setSmallIcon(R.drawable.add_icon);
                mBuilder.setContentTitle("ECA Session! On "+posting.getLocation());
                mBuilder.setContentText("Going to start on "+posting.getTime());

                Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(contentIntent);

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(0, mBuilder.build());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent intent = new Intent(MainActivity.this,TrafficPost.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
