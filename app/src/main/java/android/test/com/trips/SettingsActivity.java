package android.test.com.trips;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.test.com.trips.model.User;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {


    Button saveBtn;
    DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        db = new DBHelper(SettingsActivity.this);
        saveBtn = findViewById(R.id.saveBtn);


        final AppCompatEditText name_et = findViewById(R.id.name_et);
        final AppCompatEditText id_et = findViewById(R.id.id_et);
        final AppCompatEditText email_et = findViewById(R.id.email_et);
        final AppCompatEditText comment_et = findViewById(R.id.comment_et);
        final AppCompatEditText gender_et = findViewById(R.id.gender_et);

        if (db.getUser()!=null) {
            name_et.setText(db.getUser().getName());
            id_et.setText(db.getUser().getUser_id());
            email_et.setText(db.getUser().getEmail());
            comment_et.setText(db.getUser().getComment());
            gender_et.setText(db.getUser().getGender());
        }


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = name_et.getText().toString();
                String id = id_et.getText().toString();
                String email = email_et.getText().toString();
                String comment = comment_et.getText().toString();
                String gender = gender_et.getText().toString();
                Toast.makeText(SettingsActivity.this, "Details saved", Toast.LENGTH_SHORT).show();

                db.insertUser(new User(name, id, email, gender, comment));

                finish();

            }
        });


    }


}


