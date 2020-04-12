package com.example.manoa_fix_it;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.res.TypedArray;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    // Member variables
    private RecyclerView recyclerView;
    private ArrayList<Issue> issueData;
    private IssuesAdapter issuesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Initialize FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });
        //Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Initialize adapter with data
        issueData = new ArrayList<>();
        issuesAdapter = new IssuesAdapter(this, issueData);
        recyclerView.setAdapter(issuesAdapter);
        // Get data
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
             * @param viewHolder The SportsViewHolder that is being moved
             * @param target The SportsViewHolder that you are switching the
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
                Collections.swap(issueData, from, to);
                issuesAdapter.notifyItemMoved(from, to);
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
                issueData.remove(viewHolder.getAdapterPosition());
                // Notify the adapter.
                issuesAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        // Attach the helper to the RecyclerView.
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_sort:
                // get sort options
                return true;
            case R.id.action_settings:
                // show app settings UI
                return true;
            default:
                // action not recognized
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Initialize the issue data from XML resources
     */
    private void initializeData() {
        // Get resources from XML file
        String[] issuesList = getResources().getStringArray(R.array.issue_titles);
        String[] statusList = getResources().getStringArray(R.array.issue_statuses);
        String[] datesList = getResources().getStringArray(R.array.issue_dates);
        String[] issuesInfo = getResources().getStringArray(R.array.issue_info);
        TypedArray issuesImage = getResources().obtainTypedArray(R.array.issue_images);
        // Clear existing data to avoid duplication
        issueData.clear();
        // Create ArrayList of Issue objs with its respective details
        for (int i = 0; i < issuesList.length; i++)
            issueData.add(new Issue(issuesList[i], statusList[i], datesList[i], issuesInfo[i], issuesImage.getResourceId(i, 0)));
        // Recycle the typed array
        issuesImage.recycle();
        issuesAdapter.notifyDataSetChanged();
    }
}
