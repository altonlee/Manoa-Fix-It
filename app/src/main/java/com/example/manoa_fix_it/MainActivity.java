package com.example.manoa_fix_it;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;

import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Main Activity for Manoa Fix-It!
 */
public class MainActivity extends AppCompatActivity {
    public static final int ADD_ISSUE_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_ISSUE_ACTIVITY_REQUEST_CODE = 2;
    public static final int ADD_COMPL_ACTIVITY_REQUEST_CODE = 3;
    public static final int EDIT_COMPL_ACTIVITY_REQUEST_CODE = 4;
    public static final int LOGIN_ID = R.integer.userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        // Toolbar tabs
        final TabLayout tabLayout = findViewById(R.id.tab_layout);
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
                if (tabLayout.getSelectedTabPosition() == 0) {
                    Intent addIntent = new Intent(MainActivity.this, IssueAddActivity.class);
                    startActivityForResult(addIntent, ADD_ISSUE_ACTIVITY_REQUEST_CODE);
                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    Intent addIntent = new Intent(MainActivity.this, ComplAddActivity.class);
                    startActivityForResult(addIntent, ADD_COMPL_ACTIVITY_REQUEST_CODE);
                }
            }
        });
    }

    @Override
    // Initializes menu options
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    // Handles appbar item selections
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_sort:
                return true;
            default:
                // action not recognized
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    // Handles slider menu toggling
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Handles the newly added posts of IssueAddActivity and ComplAddActivity.
     * @param requestCode: defines what the activity should be doing
     * @param resultCode: defines if operation is successful
     * @param data: returned data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ISSUE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Issue issue = new Issue(
                    LOGIN_ID,
                    data.getStringExtra("title"),
                    data.getStringExtra("loc"),
                    "Pending Review",
                    System.currentTimeMillis(),
                    data.getStringExtra("desc"),
                    0,
                    getResources().obtainTypedArray(R.array.issue_images)
                            .getResourceId(data.getIntExtra("image_resource", 0), 0));
            IssueFragment.mIssueViewModel.insert(issue);
        } else if (requestCode == ADD_COMPL_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Complaint curr = new Complaint(
                    LOGIN_ID,
                    data.getStringExtra("title"),
                    data.getStringExtra("loc"),
                    System.currentTimeMillis(),
                    data.getStringExtra("desc"),
                    0
            );
            ComplaintFragment.mComplaintViewModel.insert(curr);
        }
    }
}
