package org.techtowm.sampledatabase2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String NAME = "emplyee.db";
    public static int VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        println("onCreate 호출됨");

        String sql = "create table if not exists emp("
                + " _id integer PRIMARY KEY autoincrement, "
                + " name text, "
                + " age integer, "
                +" mobile text)";

        db.execSQL(sql);
    }

    public void onOpen(SQLiteDatabase db) {
        println("onOpen 호출됨");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        println("onUpgrade 호출됨: " + i + " -> " + i1);

        if (i > 1) {
            db.execSQL("DROP TABLE IF EXISTS emp");
        }
    }

    public void println(String s) {
        Log.d("Sang", s);
    }
}
