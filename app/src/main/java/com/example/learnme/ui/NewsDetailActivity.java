package com.example.learnme.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.example.learnme.R;

//Show the Detail of News
public class NewsDetailActivity extends AppCompatActivity {

    //Text View
    TextView title,desc,date,link;

    //Toolbar
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        title = findViewById(R.id.text_title);
        desc = findViewById(R.id.text_desc);
        date = findViewById(R.id.text_date);
        link = findViewById(R.id.text_link);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Add the  title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("News Feed Detail");
        }

        //Add the Menu
        toolbar.inflateMenu(R.menu.home);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.action_settings)
                {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(NewsDetailActivity.this);
                    builder1.setMessage("This Show the detail of news with date and click on link for more detail about this");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                return false;
            }
        });



        String strTitle = getIntent().getStringExtra("title");
        String strDesc = getIntent().getStringExtra("desc");
        String strDate = getIntent().getStringExtra("date");
        String strLink = getIntent().getStringExtra("link");

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(strLink));
                startActivity(browserIntent);
            }
        });


        title.setText(strTitle);
        desc.setText("Description : \n\n"+strDesc);
        date.setText("Publish Date: \n\n"+strDate);
        link.setText(strLink);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        return true;}

}