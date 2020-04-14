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
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ConfirmActivity extends AppCompatActivity {

    private String title;
    private String description;
    private String location;
    private String date;

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
        setContentView(R.layout.confirm_issue);

        Intent intent = getIntent();
        title = intent.getStringExtra(AddActivity.EXTRA_TITLE);
        description = intent.getStringExtra(AddActivity.EXTRA_DESCRIPTION);
        location = intent.getStringExtra(AddActivity.EXTRA_LOCATION);
        date = intent.getStringExtra(AddActivity.EXTRA_DATE);
    }

    public void backToHome(View view) {

        Intent intent = new Intent(ConfirmActivity.this, MainActivity.class);

        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_DESCRIPTION, description);
        intent.putExtra(EXTRA_LOCATION, location);
        intent.putExtra(EXTRA_DATE, date);

        startActivity(intent);
    }
}
