package org.techtowm.recyclerview2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PersonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter  = new PersonAdapter();

        adapter.addItem(new Person("김민수", "010-0000-0000"));
        adapter.addItem(new Person("이하늘", "010-2000-2000"));
        adapter.addItem(new Person("홍길동", "010-3000-3000"));
        adapter.addItem(new Person("내이름1", "010-4000-4000"));
        adapter.addItem(new Person("내이름2", "010-5000-5000"));
        adapter.addItem(new Person("내이름3", "010-6000-6000"));
        adapter.addItem(new Person("내이름4", "010-7000-7000"));
        adapter.addItem(new Person("내이름5", "010-8000-8000"));
        adapter.addItem(new Person("내이름6", "010-9000-9000"));

        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnPersonItemClickListener() {
            @Override
            public void onItemClick(PersonAdapter.ViewHolder holder, View view, int position) {
                Person item = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "아이템 선택됨 : " + item.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}