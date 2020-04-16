package com.example.manoa_fix_it;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int tabs;

    public PagerAdapter(@NonNull FragmentManager fm, int tabs) {
        super(fm);
        this.tabs = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new IssueFragment();
            case 1: return new ComplaintFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabs;
    }
}
