package bits.se.hackstreetboys.intelligentmedbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class ViewPrescription extends AppCompatActivity {
    private RecyclerView prescriptionsRecyclerView;
    private FirebaseRecyclerAdapter<Object, MainActivity.MyViewHolder> prescriptionsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Query query;
    private static String patientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescription);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null ){
            Toast.makeText(this, "Error: No user logged in.", Toast.LENGTH_SHORT).show();
            System.out.println("Error: No user logged in.");
            startActivity(new Intent(this, LoginActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //System.out.println(user.getUid());
        patientID= user.getUid();

        initializeScreen();
    }

    private void initializeScreen() {
        prescriptionsRecyclerView = (RecyclerView) findViewById(R.id.prescriptions);
        layoutManager = new LinearLayoutManager(this);
        prescriptionsRecyclerView.setLayoutManager(layoutManager);
        //mNotificationRef = FirebaseDatabase.getInstance().
        //        getReference("patients/"+patientName+"/prescriptions");
        query = FirebaseDatabase.getInstance()
                .getReference().child("patients/"+patientID+"/prescriptions")
                .limitToLast(50);
        setupAdapter();
        prescriptionsRecyclerView.setAdapter(prescriptionsAdapter);
        //System.out.println("Im here");
    }

    private void setupAdapter() {
        //notificationsRecyclerView.setHasFixedSize(true);
        FirebaseRecyclerOptions<Object > options =
                new FirebaseRecyclerOptions.Builder<Object >()
                        .setQuery(query, Object.class).build();
        prescriptionsAdapter = new FirebaseRecyclerAdapter<Object, MainActivity.MyViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull MainActivity.MyViewHolder holder, int position,
                                            @NonNull Object model) {
                //System.out.println(model);
                Map<String, Object> map = (Map<String, Object>) model;
                //System.out.println("hmm");
                //System.out.println(map);
                Prescription pres= new Prescription(map);
                holder.bind(pres);
            }

            @NonNull
            @Override
            public MainActivity.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.notification_list_item, viewGroup, false);

                return new MainActivity.MyViewHolder(view);
            }
        };
        prescriptionsAdapter.startListening();
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
            Intent intent = new Intent(ViewPrescription.this, MainActivity.class);
            //intent.putExtra("Name", patientID);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_view_pre) {
            return true;
        } else if (id == R.id.action_add_med) {
            Intent intent = new Intent(ViewPrescription.this, AddMedicine.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
