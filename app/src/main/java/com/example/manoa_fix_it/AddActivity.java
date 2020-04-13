package com.example.manoa_fix_it;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // load Add form layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_issue);

        final EditText name = findViewById(R.id.title_text);
        final EditText loc = findViewById(R.id.loc_text);
        final EditText date = findViewById(R.id.date_text);
        final EditText info = findViewById(R.id.info_text);
        ImageView img = findViewById(R.id.image_placeholder);
        Glide.with(this).load(R.drawable.img_placeholder).into(img);

        // Initialize Submit button
        final FloatingActionButton button = findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                // if any form inputs are empty, print error
                if (TextUtils.isEmpty(name.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else if (TextUtils.isEmpty(loc.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else if (TextUtils.isEmpty(date.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else if (TextUtils.isEmpty(info.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    // post form value into Intent
                    replyIntent.putExtra("title", name.getText().toString());
                    replyIntent.putExtra("date", date.getText().toString());
                    replyIntent.putExtra("loc", loc.getText().toString());
                    replyIntent.putExtra("info", info.getText().toString());
                    replyIntent.putExtra("image", R.id.image_placeholder);
                    // print result
                    Toast.makeText(
                            getApplicationContext(),
                            "Your post was submitted for review",
                            Toast.LENGTH_LONG).show();
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
