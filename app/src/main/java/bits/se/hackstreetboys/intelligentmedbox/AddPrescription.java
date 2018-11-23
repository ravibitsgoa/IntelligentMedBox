package bits.se.hackstreetboys.intelligentmedbox;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AddPrescription extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private Button addMedicineButton;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.add_pres);
        editText = (EditText) findViewById(R.id.med_name);
        addMedicineButton = (Button) findViewById(R.id.add_med_button);
        addMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coordinatorLayout.addView(createNewTextView());
            }
        });
    }


    private EditText createNewTextView() {
        final CoordinatorLayout.LayoutParams lparams = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
        final EditText textView = new EditText(this);
        textView.setLayoutParams(lparams);
        textView.setText("New text: " );
        return textView;
    }
}
