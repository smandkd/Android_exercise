package com.example.sampleintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { // MenuActivity 로 부터 받은 응답을 처리
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == REQUEST_CODE_MENU) {
            Toast.makeText(getApplicationContext(),
                    "onActivityResult 메서드 호출됨. 요청 코드 : " + requestCode +
                    ", 결과 코드 : " + resultCode, Toast.LENGTH_LONG).show();

            if( resultCode == RESULT_OK ) {
                String name = data.getStringExtra("name");
                Toast.makeText(getApplicationContext(), "응답으로 전달된 name : " + name, Toast.LENGTH_LONG ).show();
            }
        }
    }

    public static final int REQUEST_CODE_MENU = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) { // activity_menu 띄우기
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivityForResult(intent, REQUEST_CODE_MENU);
                // 새 액티비티를 띄우면서 응답을 받을 수 있다.
                // REQUEST_CODE_MENU 는 새 엑티비티를 띄울 떄 보낼 요청코드, 새 액티비티들을 구분할 때 사용
            }
        });
    }
}