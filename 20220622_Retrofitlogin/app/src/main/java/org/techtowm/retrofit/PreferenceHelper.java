package org.techtowm.retrofit;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {
    private final String INTRO = "intro";
    private final String ID = "id";
    private final String AGE = "age";
    private SharedPreferences app_prefs;
    private Context context;

    public PreferenceHelper(Context context) {
        app_prefs = context.getSharedPreferences("shared", 0);
        this.context = context;
    }

    public void putIslogin(boolean loginOrOut) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(INTRO, loginOrOut);
        edit.apply();
    }

    public void putId(String loginOrOut) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(ID, loginOrOut);
        edit.apply();
    }

    public String getID() {
        return app_prefs.getString(ID, "");
    }

    public void putAge(String loginOrOut) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(AGE, loginOrOut);
        edit.apply();
    }

    public String getAge() {
        return app_prefs.getString(AGE, "");
    }
}
