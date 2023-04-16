package org.techtowm.retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonDeserializationContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "LoginActivity";

    private EditText etId, etPassword;
    private Button btnlogin, btnregister;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferenceHelper = new PreferenceHelper(this);

        etId = (EditText)findViewById(R.id.edit_id);
        etPassword = (EditText) findViewById(R.id.edit_password);
        btnlogin = (Button) findViewById(R.id.btn_login);
        btnregister = (Button) findViewById(R.id.btn_signup);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        final String userId = etId.getText().toString();
        final String userPassword = etPassword.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LoginInterface.LOGIN_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        LoginInterface api = retrofit.create(LoginInterface.class);
        Call<String> call = api.getUserLogin(userId, userPassword);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Log.e("Sang", response.body());

                    String jsonResponse = response.body();
                    parseLoginData(jsonResponse);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "에러 = " + t.getMessage());
            }
        });
    }

    private void parseLoginData(String response) {
        try{
            JSONObject jsonObject = new JSONObject(response);

            if( jsonObject.getString("status").equals("true") ) {
                saveInfo(response);
                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
            }

        }
        catch(JSONException e) { e.printStackTrace(); }
    }

    private void saveInfo(String response) {
        preferenceHelper.putIslogin(true);

        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")){
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for( int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    preferenceHelper.putId(dataobj.getString("name"));
                    preferenceHelper.putAge(dataobj.getString("age"));
                }
            }
        }
        catch(JSONException e) { e.printStackTrace(); }

    }
}