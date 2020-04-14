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
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Member variables.
    private RecyclerView mRecyclerView;
    private ArrayList<Issue> mIssueData;
    private IssuesAdapter mAdapter;

    private ArrayList<String> titleList = new ArrayList<>();
    private ArrayList<String> descriptionList = new ArrayList<>();
    private ArrayList<String> locationList = new ArrayList<>();
    private ArrayList<String> dateList = new ArrayList<>();
    private ArrayList<String> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        if(intent != null) {
            String tTitle = intent.getStringExtra(ConfirmActivity.EXTRA_TITLE);
            String tDescription = intent.getStringExtra(ConfirmActivity.EXTRA_DESCRIPTION);
            String tLocation = intent.getStringExtra(ConfirmActivity.EXTRA_LOCATION);
            String tDate = intent.getStringExtra(ConfirmActivity.EXTRA_DATE);

            titleList.add(tTitle);
            descriptionList.add(tDescription);
            locationList.add(tLocation);
            dateList.add(tDate);
        }


        // Initialize the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerView);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the ArrayList that will contain the data.
        mIssueData = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new IssuesAdapter(this, mIssueData);
        mRecyclerView.setAdapter(mAdapter);

        // Get the data.
        initializeData();

        // Helper class for creating swipe to dismiss and drag and drop
        // functionality.
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | 
                    ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            /**
             * Defines the drag and drop functionality.
             *
             * @param recyclerView The RecyclerView that contains the list items
             * @param viewHolder The IssuesViewHolder that is being moved
             * @param target The IssuesiewHolder that you are switching the
             *               original one with.
             * @return true if the item was moved, false otherwise
             */
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                // Get the from and to positions.
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                // Swap the items and notify the adapter.
                Collections.swap(mIssueData, from, to);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            /**
             * Defines the swipe to dismiss functionality.
             *
             * @param viewHolder The viewholder being swiped.
             * @param direction The direction it is swiped in.
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                 int direction) {
                // Remove the item from the dataset.
                mIssueData.remove(viewHolder.getAdapterPosition());
                // Notify the adapter.
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        // Attach the helper to the RecyclerView.
        helper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * Initialize the issue data from resources.
     */
    private void initializeData() {
        // Get the resources from the XML file.
        String[] issueList = getResources()
                .getStringArray(R.array.issue_titles);
        String[] issueInfo = getResources()
                .getStringArray(R.array.issue_info);
        String[] issueDate = getResources()
                .getStringArray(R.array.issue_date);
        String[] issueLoc = getResources()
                .getStringArray(R.array.issue_loc);
        TypedArray issueImageResources = getResources()
                .obtainTypedArray(R.array.issue_images);

        for(String item : issueList) {
            titleList.add(item);
        }
        for(String item : issueInfo) {
            descriptionList.add(item);
        }
        for(String item : issueDate) {
            dateList.add(item);
        }
        for(String item : issueLoc) {
            locationList.add(item);
        }

        // Clear the existing data (to avoid duplication).
        mIssueData.clear();

        // Create the ArrayList of Issue objects with the titles and
        // information about each issue
        for (int i = 0; i < titleList.size(); i++) {
            if(titleList.get(i) != null) {
                mIssueData.add(new Issue(titleList.get(i), descriptionList.get(i),
                        locationList.get(i), dateList.get(i),
                        issueImageResources.getResourceId(0, 0)));
            }
        }

        // Recycle the typed array.
        issueImageResources.recycle();

        // Notify the adapter of the change.
        mAdapter.notifyDataSetChanged();
    }

    public void resetIssues(View view) {
        initializeData();
    }

    public void addIssue(View view) {
        startActivity(new Intent(MainActivity.this, AddActivity.class));
    }
}
