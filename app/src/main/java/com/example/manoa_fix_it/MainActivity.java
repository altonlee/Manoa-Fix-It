package com.example.manoa_fix_it;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;

import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Main Activity for Manoa Fix-It!
 */
public class MainActivity extends AppCompatActivity {
    public static final int ADD_ISSUE_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_ISSUE_ACTIVITY_REQUEST_CODE = 2;

    private List<Issue> issueData;
    private IssuesAdapter issuesAdapter;
    public static IssueViewModel mIssueViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Toolbar tabs
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_issues));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_complaints));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        // Tab pages
        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        // Tab onClickListeners
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        // Initialize FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, AddIssueActivity.class);
                startActivityForResult(addIntent, ADD_ISSUE_ACTIVITY_REQUEST_CODE);
            }
        });

        // Initialize adapter with data
        issueData = new ArrayList<Issue>();
        issuesAdapter = new IssuesAdapter(this, issueData);

        // Initialize IssueViewModel
        mIssueViewModel = ViewModelProviders.of(this).get(IssueViewModel.class);
        mIssueViewModel.getAllIssues().observe(this, new Observer<List<Issue>>() {
            @Override
            public void onChanged(@Nullable List<Issue> issues) {
                issuesAdapter.setIssues(issues);
            }
        });
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
     * Processes form from AddIssueActivity.
     * Handles the newly added issues aspect of AddIssueActivity.
     * @param requestCode: defines what AddIssueActivity should be doing
     * @param resultCode: defines if operation is successful
     * @param data: returned data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ISSUE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Date date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy", Locale.US);

            Issue issue = new Issue(
                    55550019, // get userID somehow
                    data.getStringExtra("title"),
                    data.getStringExtra("loc"),
                    "Pending Review",
                    df.format(date),
                    data.getStringExtra("desc"),
                    0,
                    getResources().obtainTypedArray(R.array.issue_images)
                            .getResourceId(data.getIntExtra("image_resource", 0), 0));
            mIssueViewModel.insert(issue);
        }
    }
}
