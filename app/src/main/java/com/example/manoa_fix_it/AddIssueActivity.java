package com.example.manoa_fix_it;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Handles adding new issues and updating old issues.
 */
public class AddIssueActivity extends AppCompatActivity {
    private EditText title;
    private EditText loc;
    private EditText desc;
    private Spinner image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // load Add form layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_issue);

        // Form View variables
        title = findViewById(R.id.title_text);
        loc = findViewById(R.id.loc_text);
        desc = findViewById(R.id.info_text);
        image = findViewById(R.id.image_picker);
        ImageView img = findViewById(R.id.image_placeholder);
        Glide.with(this).load(R.drawable.img_placeholder).into(img);

        final Bundle extras = getIntent().getExtras();
        // if activity is passed in an issue, then we're editing
        if (extras != null) {
            int id = extras.getInt("postID", -1);
            if (id != -1) {
                // Editing Toolbar
                Toolbar toolbar = findViewById(R.id.addToolbar);
                toolbar.setTitle("Editing Issue");
                setSupportActionBar(toolbar);
                ActionBar ab = getSupportActionBar();
                ab.setDisplayHomeAsUpEnabled(true);

                title.setText(extras.getString("title"));
                loc.setText(extras.getString("loc"));
                desc.setText(extras.getString("desc"));
            }
        // else, we are adding an issue
        } else {
            // Adding Toolbar
            Toolbar toolbar = findViewById(R.id.addToolbar);
            toolbar.setTitle("Adding Issue");
            setSupportActionBar(toolbar);
            ActionBar ab = getSupportActionBar();
            ab.setDisplayHomeAsUpEnabled(true);
        }

        // Initialize Submit button
        final FloatingActionButton button = findViewById(R.id.submit);
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
                } else if (image.getSelectedItem() == null) {
                    errorToast("picture");
                    setResult(RESULT_CANCELED, replyIntent);
                // updating an issue
                } else if (extras != null) {
                    // post updated form values into Intent
                    replyIntent.putExtra("postID", extras.getInt("postID"));
                    replyIntent.putExtra("userID", extras.getInt("userID"));
                    replyIntent.putExtra("title", title.getText().toString());
                    replyIntent.putExtra("loc", loc.getText().toString());
                    replyIntent.putExtra("status", extras.getString("status"));
                    replyIntent.putExtra("date", extras.getString("date"));
                    replyIntent.putExtra("desc", desc.getText().toString());
                    replyIntent.putExtra("points", extras.getInt("points"));
                    replyIntent.putExtra("image_resource", (int) image.getSelectedItemId());
                    // print result
                    Toast.makeText(
                            getApplicationContext(),
                            "Your issue has been updated",
                            Toast.LENGTH_LONG).show();
                    setResult(RESULT_OK, replyIntent);
                    finish();
                // adding a new issue
                } else {
                    // post form value into Intent
                    replyIntent.putExtra("title", title.getText().toString());
                    replyIntent.putExtra("loc", loc.getText().toString());
                    replyIntent.putExtra("desc", desc.getText().toString());
                    replyIntent.putExtra("image_resource", (int) image.getSelectedItemId());
                    // print result
                    Toast.makeText(
                            getApplicationContext(),
                            "Your post was submitted for review",
                            Toast.LENGTH_LONG).show();
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
            }
        });
    }

    private void errorToast(String input) {
        Toast.makeText(
                getApplicationContext(),
                "Please add a " + input + ".",
                Toast.LENGTH_LONG).show();
    }
}
