package com.example.sampleframelayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    ImageView imageView2;

    int imageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButton1Clicked(View v) {
        changeImage();
    }

    private void changeImage() {
        if( imageIndex == 0 ) {
            imageView.setVisibility(View.VISIBLE);
            imageView2.setVisibility((View.INVISIBLE));

            imageIndex = 1;
        }else if ( imageIndex == 1 ) {
            imageView.setVisibility(View.VISIBLE);
            imageView2.setVisibility((View.INVISIBLE));

            imageIndex = 1;
        }
    }
}