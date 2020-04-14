/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.materialme;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/***
 * Detail Activity that is launched when a list item is clicked.
 * It shows more info on the sport.
 */
public class AddActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private EditText mTitleText;
    private EditText mDescriptionText;
    private EditText mLocationText;
    private EditText mDateText;

    public static final String EXTRA_TITLE = "com.example.android.materialme.extra.TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.android.materialme.extra.DESCRIPTION";
    public static final String EXTRA_LOCATION = "com.example.android.materialme.extra.LOCATION";
    public static final String EXTRA_DATE = "com.example.android.materialme.extra.DATE";

    /**
     * Initializes the activity, filling in the data from the Intent.
     *
     * @param savedInstanceState Contains information about the saved state
     *                           of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_issue);

        mTitleText = findViewById(R.id.title_input);
        mDescriptionText = findViewById(R.id.description_input);
        mLocationText = findViewById(R.id.location_input);
        mDateText = findViewById(R.id.date_input);
    }

    public void sendIssue(View view) {
        Intent intent = new Intent(AddActivity.this, ConfirmActivity.class);

        String title = mTitleText.getText().toString();
        String description = mDescriptionText.getText().toString();
        String location = mLocationText.getText().toString();
        String date = mLocationText.getText().toString();

        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_DESCRIPTION, description);
        intent.putExtra(EXTRA_LOCATION, location);
        intent.putExtra(EXTRA_DATE, date);

        startActivity(intent);
    }

    public void backToHome(View view) {
        startActivity(new Intent(AddActivity.this, MainActivity.class));
    }

    public void addImage(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}
