package com.example.manoa_fix_it;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Activity for a selected Complaint.
 */
public class ComplaintActivity extends AppCompatActivity {
    private Complaint curr;

    private TextView title;
    private TextView user;
    private TextView loc;
    private TextView date;
    private TextView op;
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
        title = findViewById(R.id.complaintTitleDetail);
        user = findViewById(R.id.complaint_userID);
        loc = findViewById(R.id.complaintLocDetail);
        date = findViewById(R.id.complaintDateDetail);
        op = findViewById(R.id.complaint_op);
        pts = findViewById(R.id.complaintPointsDetail);

        // Set data into Intent extra
        title.setText(getIntent().getStringExtra("title"));
        user.setText(getIntent().getIntExtra("userID", 0) + "");
        loc.setText(getIntent().getStringExtra("loc"));
        // Format date
        SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy");
        Date res = new Date(getIntent().getLongExtra("date", -1));
        date.setText(df.format(res));
        op.setText(getIntent().getStringExtra("desc"));
        pts.setText(getIntent().getIntExtra("points", 0) + "");
        //Glide.with(this).load(getIntent().getIntExtra("image_resource", 0)).into(img);

        // create a dummy complaint
        curr = new Complaint(
                getIntent().getIntExtra("postID", -1),
                getIntent().getIntExtra("userID", -1),
                getIntent().getStringExtra("title"),
                getIntent().getStringExtra("loc"),
                getIntent().getLongExtra("date", -1),
                getIntent().getStringExtra("desc"),
                getIntent().getIntExtra("pts", -1));
    }

    /**
     * Inflates appbar buttons.
     * @param menu: Menu to inflate
     * @return true if successful
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (getIntent().getIntExtra("userID", -1) == MainActivity.LOGIN_ID) {
            getMenuInflater().inflate(R.menu.menu_issue_edit, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_issue_details, menu);
        }
        return true;
    }

    /**
     * Menu item select handler. Handles editing and deleting options
     * @param item: index of menu item
     * @return true if valid option
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ComplaintViewModel complaintViewModel = ViewModelProviders.of(this).get(ComplaintViewModel.class);

        switch (item.getItemId()) {
            // if deleting...
            case R.id.issue_delete:
                complaintViewModel.delete(curr);
                Toast.makeText(
                        getApplicationContext(),
                        "Deleted '" + curr.getTitle() + "' complaint.",
                        Toast.LENGTH_LONG).show();
                finish();
                return true;
            // if editing...
            case R.id.issue_edit:
                Intent editIntent = new Intent(ComplaintActivity.this, IssueAddActivity.class);
                // pass in to-be-edited issue's data to ComplAddActivity
                editIntent.putExtras(getIntent().getExtras());
                startActivityForResult(editIntent, MainActivity.EDIT_COMPL_ACTIVITY_REQUEST_CODE);
                return true;
            default:
                // action not recognized
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Processes form from ComplAddActivity.
     * Handles the updating issues aspect of ComplAddActivity.
     * @param requestCode: defines what ComplAddActivity should be doing
     * @param resultCode: defines if operation is successful
     * @param data: returned data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MainActivity.EDIT_COMPL_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // Update issue in database
            curr = new Complaint(
                    data.getIntExtra("postID", -1),
                    data.getIntExtra("userID", -1),
                    data.getStringExtra("title"),
                    data.getStringExtra("loc"),
                    data.getLongExtra("date", -1),
                    data.getStringExtra("desc"),
                    data.getIntExtra("points", -1));
            ComplaintFragment.mComplaintViewModel.update(curr);

            // Update IssueActivity Views
            title.setText(data.getStringExtra("title"));
            loc.setText(data.getStringExtra("loc"));
            date.setText(data.getStringExtra("date"));
            op.setText(data.getStringExtra("desc"));
            pts.setText(data.getIntExtra("points", 0) + "");
        }
    }
}
