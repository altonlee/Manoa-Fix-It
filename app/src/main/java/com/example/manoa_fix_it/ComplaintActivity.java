package com.example.manoa_fix_it;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

public class ComplaintActivity extends AppCompatActivity {
    private Complaint curr;

    private ImageView img;
    private TextView title;
    private TextView loc;
    private TextView date;
    private TextView pts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaint_detail);

        // Initialize toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        toolbar.setTitle("View Complaint");
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // Initialize Views
        //img = findViewById(R.id.complaintImageDetail);
        title = findViewById(R.id.complaintTitleDetail);
        loc = findViewById(R.id.complaintLocDetail);
        date = findViewById(R.id.complaintDateDetail);
        pts = findViewById(R.id.complaintPointsDetail);

        // Set data into Intent extra
        title.setText(getIntent().getStringExtra("title"));
        loc.setText(getIntent().getStringExtra("loc"));
        date.setText(getIntent().getStringExtra("date"));
        pts.setText(getIntent().getIntExtra("points", 0) + "");
        //Glide.with(this).load(getIntent().getIntExtra("image_resource", 0)).into(img);

        // create a dummy complaint
        curr = new Complaint(
                getIntent().getIntExtra("postID", -1),
                getIntent().getIntExtra("userID", -1),
                getIntent().getStringExtra("title"),
                getIntent().getStringExtra("loc"),
                getIntent().getStringExtra("date"),
                getIntent().getStringExtra("desc"),
                getIntent().getIntExtra("pts", -1));
    }
}
