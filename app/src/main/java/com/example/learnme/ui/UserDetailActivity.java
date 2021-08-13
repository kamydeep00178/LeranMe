package com.example.learnme.ui;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.learnme.R;
import com.example.learnme.pref.UserPrefManager;
import com.google.android.material.snackbar.Snackbar;

public class UserDetailActivity extends AppCompatActivity {

    Button btn_submit;
    private UserPrefManager prefManager;
    private EditText ed_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new UserPrefManager(this);

        // make full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_user_deatil);

        btn_submit= findViewById(R.id.submit);
        ed_Name= findViewById(R.id.ed_Name);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ed_Name.getText().toString();

                // check the name is filled or not
                if(name==null||name=="Null"||name.isEmpty())
                {
                    Snackbar.make(view,"Kindly Add Your Name"  , Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                }
                else {
                    prefManager.setUserName(name);
                    Intent intent = new Intent(UserDetailActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}