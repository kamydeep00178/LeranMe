package com.example.learnme;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    Button btn_submit;
    private UserPrefManager prefManager;
    private EditText ed_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new UserPrefManager(this);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_home);

        btn_submit=(Button)findViewById(R.id.submit);
        ed_Name=(EditText)findViewById(R.id.ed_Name);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ed_Name.getText().toString();
                if(name==null||name=="Null"||name.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Kindly Add Your Name",Toast.LENGTH_SHORT).show();
                }
                else {
                    prefManager.setUserName(name);
                    Intent intent = new Intent(DetailActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}