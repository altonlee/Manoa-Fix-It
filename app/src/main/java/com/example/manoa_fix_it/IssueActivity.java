package com.example.manoa_fix_it;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class IssueActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.issue_detail);

        // Initialize toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // Initialize views
        ImageView img = findViewById(R.id.issueImageDetail);
        TextView loc = findViewById(R.id.locDetail);
        TextView status = findViewById(R.id.statusDetail);
        TextView date = findViewById(R.id.dateDetail);
        TextView info = findViewById(R.id.infoDetail);
        TextView pts = findViewById(R.id.pointsDetail);

        // Set data into Intent extra
        loc.setText(getIntent().getStringExtra("loc"));
        status.setText(getIntent().getStringExtra("status"));
        date.setText(getIntent().getStringExtra("date"));
        info.setText(getIntent().getStringExtra("info"));
        pts.setText(getIntent().getStringExtra("points"));
        Glide.with(this).load(getIntent().getIntExtra("image_resource", 0)).into(img);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_issue_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.issue_delete:
                // get sort options
                return true;
            case R.id.issue_edit:
                // show app settings UI
                return true;
            default:
                // action not recognized
                return super.onOptionsItemSelected(item);
        }
    }
}
