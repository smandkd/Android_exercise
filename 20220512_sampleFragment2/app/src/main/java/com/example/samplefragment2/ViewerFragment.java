package com.example.samplefragment2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class ViewerFragment extends Fragment {
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_viewer,
                container, false); // fragment_viewer.xml 을 인플레이션

        imageView = rootView.findViewById(R.id.imageView);
        return rootView;
    }

    public void setImage(int resId) {
        imageView.setImageResource(resId); // 이미지뷰에 이미지 설정
    }
}