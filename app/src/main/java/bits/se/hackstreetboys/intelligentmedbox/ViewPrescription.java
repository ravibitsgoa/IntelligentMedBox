package bits.se.hackstreetboys.intelligentmedbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ViewPrescription extends AppCompatActivity {
    private RecyclerView prescriptionsRecyclerView;
    private RecyclerView.Adapter prescriptionsAdapter;
    private RecyclerView.LayoutManager layoutManager;

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


        String[] dataSet = {"Medicine name: Crocin, Pills: 1 before breakfast, 1 after lunch",
                "Medicine name: Xanax, Pills: 2 after breakfast, 1 before dinner",
                "Medicine name: Abc, Pills: 3 before lunch, 1 before dinner",
                "Medicine name: Cdf, Pills: 2 after dinner",
        };
        prescriptionsRecyclerView = (RecyclerView) findViewById(R.id.prescriptions);
        layoutManager = new LinearLayoutManager(this);
        prescriptionsRecyclerView.setLayoutManager(layoutManager);
        prescriptionsRecyclerView.setHasFixedSize(true);
        prescriptionsAdapter = new NotificationsAdapter(dataSet);
        prescriptionsRecyclerView.setAdapter(prescriptionsAdapter);
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
