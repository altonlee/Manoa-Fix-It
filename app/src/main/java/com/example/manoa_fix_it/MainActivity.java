package com.example.manoa_fix_it;

import android.content.res.TypedArray;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    // Member variables
    private RecyclerView recyclerView;
    private List<Issue> issueData;
    private IssuesAdapter issuesAdapter;
    private IssueViewModel mIssueViewModel;

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
        //
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
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        //Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter with data
        issueData = new ArrayList<Issue>();
        issuesAdapter = new IssuesAdapter(this, issueData);
        recyclerView.setAdapter(issuesAdapter);

        // Initialize IssueViewModel
        mIssueViewModel = ViewModelProviders.of(this).get(IssueViewModel.class);
        mIssueViewModel.getAllIssues().observe(this, new Observer<List<Issue>>() {
            @Override
            public void onChanged(@Nullable final List<Issue> issues) {
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TypedArray images = getResources().obtainTypedArray(R.array.issue_images);
        int index = data.getIntExtra("image", 0);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Date date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy", Locale.US);

            Issue issue = new Issue(
                    data.getStringExtra("title"),
                    data.getStringExtra("loc"),
                    "Unresolved",
                    df.format(date),
                    data.getStringExtra("info"),
                    0,
                    images.getResourceId(index, 0));
            mIssueViewModel.insert(issue);
        }
    }
}
