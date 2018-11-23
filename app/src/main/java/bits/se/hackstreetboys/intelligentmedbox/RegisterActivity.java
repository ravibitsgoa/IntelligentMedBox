package bits.se.hackstreetboys.intelligentmedbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private RadioGroup accountTypeRadioGroup;
    private RadioButton accountTypeRadioButton;
    private Button submitButton;
    private int selectedId;
    private EditText userName;
    private FirebaseAuth firebaseAuth;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            System.out.println("Error: No user logged in.");
            startActivity(new Intent(this, LoginActivity.class));
        }
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        //System.out.println(user.getUid());
        userID = MainActivity.getUserIdFromEmail(user.getEmail());

        accountTypeRadioGroup = (RadioGroup) findViewById(R.id.account_type_radio_group);
        submitButton = (Button) findViewById(R.id.submit_radio_button);

        userName = (EditText) findViewById(R.id.name_edit_text);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                selectedId = accountTypeRadioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                //accountTypeRadioButton= (RadioButton) findViewById(selectedId);

                Toast.makeText(RegisterActivity.this, userName.getText(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(RegisterActivity.this,
                //       accountTypeRadioButton.getText(), Toast.LENGTH_SHORT).show();


                if (selectedId == R.id.doctor_radio_button) {
                    Map<String, String> userData = new HashMap<>();
                    userData.put("name", String.valueOf(userName.getText()));
                    userData.put("notifications", "");
                    userData.put("prescriptions", "");

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                            .getReference("doctors/");
                    databaseReference.child(userID).setValue(userData);
                    databaseReference.getRoot().child("usertype").child(userID).child("type").setValue("doctor");
                    databaseReference.getRoot().child("usertype").child(userID).child("uid").setValue(user.getUid());
                    Intent intent = new Intent(RegisterActivity.this, DoctorActivity.class);
                    startActivity(intent);
                } else if (selectedId == R.id.patient_radio_button) {
                    Map<String, String> userData = new HashMap<>();
                    userData.put("name", String.valueOf(userName.getText()));
                    userData.put("notifications", "");
                    userData.put("prescriptions", "");

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().
                            getReference("patients/");
                    databaseReference.child(userID).setValue(userData);
                    databaseReference.getRoot().child("usertype").child(userID).child("type").setValue("patient");
                    databaseReference.getRoot().child("usertype").child(userID).child("uid").setValue(user.getUid());

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    assert false;
                }
            }

        });

    }
}
