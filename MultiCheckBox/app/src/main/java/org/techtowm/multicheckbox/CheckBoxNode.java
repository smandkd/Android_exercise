package org.techtowm.multicheckbox;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CheckBoxNode extends androidx.appcompat.widget.AppCompatCheckBox {

    private static ArrayList<Integer> list;

    public CheckBoxNode(Context context) {
        super(context);

        list = new ArrayList<Integer>();
    }

    public CheckBoxNode(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCheckedId(int id) {
        list.add(id);
    }

    public static ArrayList<Integer> getCheckedId() {
        return list;
    }
}
