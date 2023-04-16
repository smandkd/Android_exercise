package com.example.sampleprogress;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressBar progressBar = findViewById(R.id.progressBar); // 프로그래스바 객체 참조하여 설정하기
        progressBar.setIndeterminate(false);
        progressBar.setProgress(80);
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                dialog = new ProgressDialog(MainActivity.this); // 프로그래스 대화상자 겍체 만들고 설정하기
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("데이터를 확인하는 중입니다.");

                dialog.show();
            }
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(dialog != null) { // 프로그래스 대화상자 없애기
                    dialog.dismiss();
                }
            }
        });
    }
}