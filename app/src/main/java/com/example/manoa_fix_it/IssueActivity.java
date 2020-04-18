package com.example.manoa_fix_it;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

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
        toolbar.setTitle("View Issue");
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // Initialize views
        ImageView img = findViewById(R.id.issueImageDetail);
        TextView title = findViewById(R.id.titleDetail);
        TextView loc = findViewById(R.id.locDetail);
        TextView status = findViewById(R.id.statusDetail);
        TextView date = findViewById(R.id.dateDetail);
        TextView desc = findViewById(R.id.infoDetail);
        TextView pts = findViewById(R.id.pointsDetail);

        // Set data into Intent extra
        title.setText(getIntent().getStringExtra("title"));
        loc.setText(getIntent().getStringExtra("loc"));
        status.setText(getIntent().getStringExtra("status"));
        date.setText(getIntent().getStringExtra("date"));
        desc.setText(getIntent().getStringExtra("desc"));
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
        IssueViewModel issueViewModel = ViewModelProviders.of(this).get(IssueViewModel.class);

        switch (item.getItemId()) {
            case R.id.issue_delete:

                return true;
            case R.id.issue_edit:
                Intent intent = new Intent(IssueActivity.this, EditActivity.class);
                // add intent.putExtra
                startActivity(intent);
                return true;
            default:
                // action not recognized
                return super.onOptionsItemSelected(item);
        }
    }
}
