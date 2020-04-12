package com.example.manoa_fix_it;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
        TextView status = findViewById(R.id.statusDetail);
        TextView date = findViewById(R.id.dateDetail);
        TextView info = findViewById(R.id.infoDetail);
        // Set data into Intent extra
        status.setText(getIntent().getStringExtra("status"));
        date.setText(getIntent().getStringExtra("date"));
        info.setText(getIntent().getStringExtra("info"));
        Glide.with(this).load(getIntent().getIntExtra("image_resource", 0)).into(img);
    }
}
