package org.techtowm.retrofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private EditText etid, etpassword, etage, etname;
    private Button btnregister;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        preferenceHelper = new PreferenceHelper(this);

        etid = (EditText) findViewById(R.id.edit_id);
        etpassword = (EditText) findViewById(R.id.edit_password);
        etage = (EditText) findViewById(R.id.edit_age);
        etname = (EditText) findViewById(R.id.edit_name);

        btnregister = (Button) findViewById(R.id.btn_signup);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Sang", "signup button clicked");
                registMe();
            }
        });

    }

    private void registMe() {
        Log.d("Sang", "method : registMe");
        final String id = etid.getText().toString();
        final String password = etpassword.getText().toString();
        final int age = Integer.parseInt(etage.getText().toString());
        final String name = etname.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RegisterInterface.REGIST_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        Log.d("Sang", "generate retrofit");
        RegisterInterface api = retrofit.create(RegisterInterface.class);

        Call<String> call = api.getUserRegist(id, password, name, age);
        Log.d("Sang", "call");
        call.enqueue(new Callback<String>()
        {

            @Override
            public void onResponse(@NonNull Call<String> call,@NonNull Response<String> response) {
                Log.d("Sang", "response callback");
                if(response.isSuccessful() && response.body() != null ) {
                    Log.d("Sang", "response is not null");

                    String jsonResponse = response.body();
                    try{
                        parseRegData(jsonResponse);
                    }
                    catch(JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Log.d("Sang", "response is null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.d("Sang", "에러 = " + t.getMessage());
            }
        });

    }

    private void parseRegData(String response)
            throws JSONException
    {
        JSONObject jsonObject = new JSONObject(response);
        if( jsonObject.optString("status").equals("true")) {
            saveinfo(response);
            Log.d("Sang", "회원가입 성공");
            Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveinfo(String response) {
        preferenceHelper.putIslogin(true);
        try{
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.getString("status").equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    preferenceHelper.putId(dataobj.getString("id"));
                    preferenceHelper.putAge(dataobj.getString("age"));
                }
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }
}