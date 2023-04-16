package com.example.sampleparcelable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {
    TextView textView;

    public static final String KEY_SIMPLE_DATA = "data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("name", "mike");
                setResult(RESULT_OK, intent);

                finish();
            }
        });

        Intent intent = getIntent(); // 메인 액티비티로부터 전달 받은 인텐트 객체를 참조하기 위해 호출
        processIntent(intent);
    }

    public void processIntent(Intent intent) {
        if( intent != null ) {
            Bundle bundle = intent.getExtras(); // Bundle 자료형 객체가 반환된다.
            SimpleData data = bundle.getParcelable(KEY_SIMPLE_DATA); // KEY_SIMPLE_DATA : 데이터 저장을 위한 키
            if ( intent != null ) {
                textView.setText("전달 받은 데이터\nNumber : " + data.number
                + "\nMessage : " + data.message);
            }
        }
    }

}





























