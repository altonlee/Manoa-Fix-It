package com.example.manoa_fix_it;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Handles adding new complaints and updating old complaints.
 */
public class ComplAddActivity extends AppCompatActivity {
    private EditText title;
    private EditText loc;
    private EditText desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // load Add form layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_complaint);

        // Form View variables
        title = findViewById(R.id.compl_title_text);
        loc = findViewById(R.id.compl_loc_text);
        desc = findViewById(R.id.compl_info_text);

        final Bundle extras = getIntent().getExtras();
        // if activity is passed in any complaints, then we're editing
        if (extras != null) {
            int id = extras.getInt("postID", -1);
            if (id != -1) {
                // Editing Toolbar
                Toolbar toolbar = findViewById(R.id.compl_addToolbar);
                toolbar.setTitle("Editing Complaint");
                setSupportActionBar(toolbar);
                ActionBar ab = getSupportActionBar();
                ab.setDisplayHomeAsUpEnabled(true);

                title.setText(extras.getString("title"));
                loc.setText(extras.getString("loc"));
                desc.setText(extras.getString("desc"));
            }
            // else, we are adding an complaints
        } else {
            // Adding Toolbar
            Toolbar toolbar = findViewById(R.id.compl_addToolbar);
            toolbar.setTitle("Adding Complaint");
            setSupportActionBar(toolbar);
            ActionBar ab = getSupportActionBar();
            ab.setDisplayHomeAsUpEnabled(true);
        }

        // Initialize Submit button
        final FloatingActionButton button = findViewById(R.id.compl_submit);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                // if any form inputs are empty, print error
                if (TextUtils.isEmpty(title.getText())) {
                    errorToast("title");
                    setResult(RESULT_CANCELED, replyIntent);
                } else if (TextUtils.isEmpty(loc.getText())) {
                    errorToast("location");
                    setResult(RESULT_CANCELED, replyIntent);
                } else if (TextUtils.isEmpty(desc.getText())) {
                    errorToast("description");
                    setResult(RESULT_CANCELED, replyIntent);
                // updating an complaint
                } else if (extras != null) {
                    // post updated form values into Intent
                    replyIntent.putExtra("postID", extras.getInt("postID"));
                    replyIntent.putExtra("userID", extras.getInt("userID"));
                    replyIntent.putExtra("title", title.getText().toString());
                    replyIntent.putExtra("loc", loc.getText().toString());
                    replyIntent.putExtra("date", extras.getString("date"));
                    replyIntent.putExtra("desc", desc.getText().toString());
                    replyIntent.putExtra("points", extras.getInt("points"));
                    // print result
                    Toast.makeText(
                            getApplicationContext(),
                            "Your complaint details have been updated",
                            Toast.LENGTH_LONG).show();
                    setResult(RESULT_OK, replyIntent);
                    finish();
                // adding a new complaint
                } else {
                    // post form value into Intent
                    replyIntent.putExtra("title", title.getText().toString());
                    replyIntent.putExtra("loc", loc.getText().toString());
                    replyIntent.putExtra("desc", desc.getText().toString());
                    // print result
                    Toast.makeText(
                            getApplicationContext(),
                            "Complaint '" + title.getText() + "' posted successfully",
                            Toast.LENGTH_LONG).show();
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
            }
        });
    }

    /**
     * Prints error toast.
     * @param input: specific error to print
     */
    private void errorToast(String input) {
        Toast.makeText(
                getApplicationContext(),
                "Please add a " + input + ".",
                Toast.LENGTH_LONG).show();
    }
}
