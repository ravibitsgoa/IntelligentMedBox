package bits.se.hackstreetboys.intelligentmedbox;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Map;

public class DoctorActivity extends AppCompatActivity {

    private RecyclerView notificationsRecyclerView;
    private FirebaseRecyclerAdapter<Object, bits.se.hackstreetboys.intelligentmedbox.DoctorActivity.MyViewHolder> notificationsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static String doctorID;
    //private DatabaseReference mNotificationRef;
    private Query query;
    private static final String channelName = "notifications_channel";
    private static int notificationId = 0;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //patientName = getIntent().getStringExtra("Name");
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            System.out.println("Error: No user logged in.");
            startActivity(new Intent(this, LoginActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //System.out.println(user.getUid());
        doctorID = MainActivity.getUserIdFromEmail(user.getEmail());
        initializeScreen();
    }

    private void initializeScreen() {
        notificationsRecyclerView = (RecyclerView) findViewById(R.id.notifications);
        layoutManager = new LinearLayoutManager(this);
        notificationsRecyclerView.setLayoutManager(layoutManager);
        //mNotificationRef = FirebaseDatabase.getInstance().
        //        getReference("patients/"+patientName+"/notifications");
        query = FirebaseDatabase.getInstance()
                .getReference().child("doctors/" + doctorID + "/notifications")
                .limitToLast(50);
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                //System.out.println("hi");
                assert map != null;
                //System.out.println("hi");
                MyNotification res = new MyNotification(map);
                //dataSnapshot.getValue(MyNotification.class);
                //System.out.println(map);
                //System.out.println(res);
                sendNotification(res);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        query.addChildEventListener(childEventListener);
        setupAdapter();
        createNotificationChannel();
        notificationsRecyclerView.setAdapter(notificationsAdapter);
    }

    private void sendNotification(MyNotification myNotification) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder
                (bits.se.hackstreetboys.intelligentmedbox.DoctorActivity.this, channelName)
                .setSmallIcon(R.drawable.med_icon)
                .setContentTitle("Alert")
                .setContentText(myNotification.toString())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, mBuilder.build());
        notificationId++;
    }

    private void setupAdapter() {
        //notificationsRecyclerView.setHasFixedSize(true);
        FirebaseRecyclerOptions<Object> options =
                new FirebaseRecyclerOptions.Builder<Object>()
                        .setQuery(query, Object.class).build();
        notificationsAdapter = new FirebaseRecyclerAdapter<Object, bits.se.hackstreetboys.intelligentmedbox.DoctorActivity.MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull bits.se.hackstreetboys.intelligentmedbox.DoctorActivity.MyViewHolder holder, int position,
                                            @NonNull Object model) {
                MyNotification myNotification = new MyNotification();
                Map<String, Object> map = (Map<String, Object>) model;
                //System.out.println("hmm");
                //System.out.println(map);
                MyNotification notification = new MyNotification(map);
                holder.bind(notification);
            }

            @NonNull
            @Override
            public bits.se.hackstreetboys.intelligentmedbox.DoctorActivity.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.notification_list_item, viewGroup, false);

                return new bits.se.hackstreetboys.intelligentmedbox.DoctorActivity.MyViewHolder(view);
            }
        };
        notificationsAdapter.startListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor_menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_view_log) {
            return true;
        } else if (id == R.id.action_add_pres) {
            Intent intent = new Intent(this, AddPrescription.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_item_number);
        }

        void bind(Object obj) {
            textView.append(obj.toString());
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelName,
                    name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
    }
}
