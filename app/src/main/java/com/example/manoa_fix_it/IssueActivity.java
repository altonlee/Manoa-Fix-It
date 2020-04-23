package com.example.manoa_fix_it;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class IssueActivity extends AppCompatActivity {
    private Issue curr;

    private ImageView img;
    private TextView title;
    private TextView loc;
    private TextView status;
    private TextView date;
    private TextView desc;
    private TextView pts;

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

        // Initialize Views
        img = findViewById(R.id.issueImageDetail);
        title = findViewById(R.id.titleDetail);
        loc = findViewById(R.id.locDetail);
        status = findViewById(R.id.statusDetail);
        date = findViewById(R.id.dateDetail);
        desc = findViewById(R.id.descDetail);
        pts = findViewById(R.id.pointsDetail);

        // Set data into Intent extra
        title.setText(getIntent().getStringExtra("title"));
        loc.setText(getIntent().getStringExtra("loc"));
        status.setText(getIntent().getStringExtra("status"));
        date.setText(getIntent().getStringExtra("date"));
        desc.setText(getIntent().getStringExtra("desc"));
        pts.setText(getIntent().getIntExtra("points", 0) + "");
        Glide.with(this).load(getIntent().getIntExtra("image_resource", 0)).into(img);

        // create a dummy issue
        curr = new Issue(
                getIntent().getIntExtra("postID", -1),
                getIntent().getIntExtra("userID", -1),
                getIntent().getStringExtra("title"),
                getIntent().getStringExtra("loc"),
                getIntent().getStringExtra("status"),
                getIntent().getStringExtra("date"),
                getIntent().getStringExtra("desc"),
                getIntent().getIntExtra("points", -1),
                getIntent().getIntExtra("image_resource", 0));
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
            // if deleting...
            case R.id.issue_delete:
                issueViewModel.delete(curr);
                Toast.makeText(
                        getApplicationContext(),
                        "Deleted '" + curr.getTitle() + "' issue.",
                        Toast.LENGTH_LONG).show();
                finish();
                return true;
            // if editing...
            case R.id.issue_edit:
                Intent editIntent = new Intent(IssueActivity.this, AddIssueActivity.class);
                // pass in to-be-edited issue's data to AddIssueActivity
                editIntent.putExtras(getIntent().getExtras());
                startActivityForResult(editIntent, MainActivity.EDIT_ISSUE_ACTIVITY_REQUEST_CODE);
                return true;
            default:
                // action not recognized
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Processes form from AddIssueActivity.
     * Handles the updating issues aspect of AddIssueActivity.
     * @param requestCode: defines what AddIssueActivity should be doing
     * @param resultCode: defines if operation is successful
     * @param data: returned data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

         if (requestCode == MainActivity.EDIT_ISSUE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
             // Update issue in database
            curr = new Issue(
                    data.getIntExtra("postID", -1),
                    data.getIntExtra("userID", -1),
                    data.getStringExtra("title"),
                    data.getStringExtra("loc"),
                    data.getStringExtra("status"),
                    data.getStringExtra("date"),
                    data.getStringExtra("desc"),
                    data.getIntExtra("points", -1),
                    getResources().obtainTypedArray(R.array.issue_images)
                            .getResourceId(data.getIntExtra("image_resource", 0), 0));
            MainActivity.mIssueViewModel.update(curr);

            // Update IssueActivity Views
             title.setText(data.getStringExtra("title"));
             loc.setText(data.getStringExtra("loc"));
             status.setText(data.getStringExtra("status"));
             date.setText(data.getStringExtra("date"));
             desc.setText(data.getStringExtra("desc"));
             pts.setText(data.getIntExtra("points", 0) + "");
             Glide.with(this).load(getResources().obtainTypedArray(R.array.issue_images)
                     .getResourceId(data.getIntExtra("image_resource", 0), 0))
                     .into(img);
        }
    }
}
