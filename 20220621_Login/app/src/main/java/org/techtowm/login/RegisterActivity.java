package org.techtowm.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private EditText edit_id, edit_password, edit_age, edit_name;
    private Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Log.d("Sang", "RegisterActivity");
        edit_id = findViewById(R.id.edit_id);
        edit_password = findViewById(R.id.edit_password);
        edit_age = findViewById(R.id.edit_age);
        edit_name = findViewById(R.id.edit_name);

        btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Sang", "clicked button");
                String userID = edit_id.getText().toString();
                String userPassword = edit_password.getText().toString();
                String userEmail = edit_age.getText().toString();
                String userName = edit_name.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            Log.d("Sang", "회원가입 성공");
                            if(success) {
                                Log.d("Sang", "회원가입 성공");
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Log.d("Sang", "회원가입 실패");
                                return;
                            }
                        }
                        catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userEmail, userName, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });


    }
}