package org.techtowm.loginsession;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    //key for storing email
    public static final String EMAIL_KEY = "email_key";

    //key for storing password
    public static final String PASSWORD_KEY = "password_key";

    //variable for shared preferences
    SharedPreferences sharedpreferences;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing EditTexts and our Button
        EditText emailEdt = findViewById(R.id.etemail);
        EditText passwordEdt = findViewById(R.id.etpassword);
        Button loginbtn = findViewById(R.id.btnlogin);

        //getting the data which is stored in shared preferences
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        //in shared prefs inside het string method
        //we are passing key value as EMAIL_KEY and
        //default value is
        //set to null if not present
        email = sharedpreferences.getString(EMAIL_KEY, null);
        password = sharedpreferences.getString(PASSWORD_KEY, null);

        //calling on click listener for login button
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // to check if the user fields are empty or not
                if( TextUtils.isEmpty(emailEdt.getText().toString()) && TextUtils.isEmpty(passwordEdt.getText().toString())) {
                    //this method will call when email and password fields are empty
                    Toast.makeText(MainActivity.this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    //below two lines will put values for
                    //email and password in shared preferences
                    editor.putString(EMAIL_KEY, emailEdt.getText().toString());
                    editor.putString(PASSWORD_KEY, passwordEdt.getText().toString());

                    //to save our data with key and value
                    editor.apply();

                    //starting new activity
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(email != null && password != null) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }
}