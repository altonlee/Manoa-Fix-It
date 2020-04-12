package com.example.manoa_fix_it;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // load Add form layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_issue);

        // Initialize Submit button
        FloatingActionButton fab = findViewById(R.id.submit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Your issue was submitted for review",
                        Toast.LENGTH_SHORT).show();

                processForm();
            }
        });
    }

    private void processForm() {
        // Check form inputs
        System.out.println("Pretend this works lol");

        // Push form input into local database (?)
        System.out.println("also pretend this works");

        // Return to landing screen
        startActivity(new Intent(AddActivity.this, MainActivity.class));
    }
}
