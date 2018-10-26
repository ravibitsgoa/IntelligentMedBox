package bits.se.hackstreetboys.intelligentmedbox;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import static bits.se.hackstreetboys.intelligentmedbox.R.string.channel_name;

public class MainActivity extends AppCompatActivity {
    private RecyclerView notificationsRecyclerView;
    private RecyclerView.Adapter notificationsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String channelName = "notifications_channel";
    private static int notificationId=0;

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
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        String[] dataSet = {"Medicine name: Crocin, Pills: 1, Time: 09:00",
                "Medicine name: Xanax, Pills: 2, Time: 10:00",
                "Medicine name: Abc, Pills: 3, Time: 11:00",
                "Medicine name: Cdf, Pills: 2, Time: 13:00",
        };

        createNotificationChannel();
        for (String notification : dataSet) {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder
                    (MainActivity.this, channelName)
                    .setSmallIcon(R.drawable.med_icon)
                    .setContentTitle("Alert")
                    .setContentText(notification)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
            notificationManager.notify(notificationId, mBuilder.build());
            notificationId++;
        }

        notificationsRecyclerView = (RecyclerView) findViewById(R.id.notifications);
        layoutManager = new LinearLayoutManager(this);
        notificationsRecyclerView.setLayoutManager(layoutManager);
        notificationsRecyclerView.setHasFixedSize(true);
        notificationsAdapter = new NotificationsAdapter(dataSet);
        notificationsRecyclerView.setAdapter(notificationsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_view_log) {
            return true;
        } else if (id == R.id.action_view_pre) {
            Intent intent = new Intent(MainActivity.this, ViewPrescription.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_add_med) {
            Intent intent = new Intent(MainActivity.this, AddMedicine.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
