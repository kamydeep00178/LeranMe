package com.example.learnme;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPrefManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "UserName";

    private static final String Name = "Name";

    public UserPrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setUserName(String isFirstTime) {
        editor.putString(Name, isFirstTime);
        editor.commit();
    }

    public String getUserName() {
        return pref.getString(Name, "News");
    }
}
