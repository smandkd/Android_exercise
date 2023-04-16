package org.techtowm.loginsession;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    // creating constant keys for shared preferences
    public static final String SHARED_PREFS = "shared_prefs";

    //key for storing email
    public static final String EMAIL_KEY = "email_key";

    //key for storing password
    public static final String PASSWORD_KEY = "password_key";

    //variable for shared preferences;
    SharedPreferences sharedpreferences;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // initializing our shared preferences
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        //getting data from shared prefs and
        //storing it in our string variable
        email = sharedpreferences.getString(EMAIL_KEY, null);

        //initializing our textview and button
        TextView tvwelcome = findViewById(R.id.tvid_welcome);
        tvwelcome.setText("Welcome \n" + email);
        Button btnlogout = findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // calling method to edit values in shared prefs
                SharedPreferences.Editor editor = sharedpreferences.edit();

                //below line will clear
                //the data in shared prefs
                editor.clear();

                //below line will apply empty
                //data to shared prefs
                editor.apply();

                //starting mainactivity after
                //clearing values in shared preferences
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}