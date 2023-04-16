package org.techtowm.multicheckbox;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.checkbox.MaterialCheckBox;

public class MainActivity extends AppCompatActivity {
    CheckBoxNode checkBox_parent;
    CheckBoxNode checkBox_child;
    TableLayout tableLayout;
    TableRow tableRow;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBox_parent = findViewById(R.id.parent);
        tableLayout = findViewById(R.id.tablelayout);

        createTableRow();

        checkBox_parent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if( b ) {
                    for( Integer t : CheckBoxNode.getCheckedId() ) {
                        Log.d("Sang", "Checked CheckBox ids : " + t.toString() );
                    }

                }
            }
        });

        checkBox_child.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("Sang", "Checked childcheckbox id" + checkBox_child.getId());
            }
        });
    }

    private void createTableRow() {
        for(int i = 0; i < 5; i++ )
        {
            tableRow = new TableRow(getApplicationContext());
            tableRow.setId(100 + count);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            checkBox_child = new CheckBoxNode(this);
            checkBox_child.setId(count+200);
            tableRow.addView(checkBox_child);

            TextView table_column_1 = new TextView(getApplicationContext());
            TextView table_column_2 = new TextView(getApplicationContext());
            TextView table_column_3 = new TextView(getApplicationContext());

            table_column_1.setText("열1");
            tableRow.addView(table_column_1);

            table_column_2.setText("열2");
            tableRow.addView(table_column_2);

            table_column_3.setText("열3");
            tableRow.addView(table_column_3);

            tableLayout.addView(tableRow, new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
            );
            count++;
        }
    }
}