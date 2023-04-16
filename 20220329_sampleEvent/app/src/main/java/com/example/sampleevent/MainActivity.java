package com.example.sampleevent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        View view = findViewById(R.id.view);
        view.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) { // MotionEvent : 액션정보와 터치한 곳의 좌표 정보를 담고 있다.
                int action = motionEvent.getAction(); // 액션정보를 담고 있다. ( 손가락이 눌렸는지 눌린 상태로 움직이는지 손가릭이 떼어졌는지 등등 )

                float curX = motionEvent.getX();
                float curY = motionEvent.getY();

                if( action == MotionEvent.ACTION_DOWN) {
                    println("손가락 눌림 : " + curX + ", " + curY);
                } else if(action == MotionEvent.ACTION_MOVE) {
                    println("손가락 움직임 : " + curX + ", " + curY);
                } else if(action == MotionEvent.ACTION_UP) {
                    println("손가락 움직임 : " + curX + ", " + curY);
                }
                return true;
            }
        });
    }

    public void println(String data) {
        textView.append(data + "\n");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if( keyCode == KeyEvent.KEYCODE_BACK ){
            Toast.makeText(this, "시스템 [BACK] 버튼이 눌렸습니다.",
                    Toast.LENGTH_LONG).show();

            return true;
        }
        return false;
    }
}