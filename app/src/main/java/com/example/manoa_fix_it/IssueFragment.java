package com.example.manoa_fix_it;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class IssueFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Issue> issueData;
    private IssuesAdapter issuesAdapter;
    private IssueViewModel mIssueViewModel;

    public IssueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_issue, container, false);

        //Initialize RecyclerView
        recyclerView = rootView.findViewById(R.id.recyclerIssue);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize adapter with data
        issueData = new ArrayList<Issue>();
        issuesAdapter = new IssuesAdapter(getActivity(), issueData);
        recyclerView.setAdapter(issuesAdapter);

        // Initialize IssueViewModel
        mIssueViewModel = ViewModelProviders.of(this).get(IssueViewModel.class);
        mIssueViewModel.getAllIssues().observe(this, new Observer<List<Issue>>() {
            @Override
            public void onChanged(@Nullable List<Issue> issues) {
                issuesAdapter.setIssues(issues);
            }
        });

        return recyclerView;
    }
}
