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
 * A simple {@link Fragment} subclass.
 */
public class ComplaintFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Complaint> complaintData;
    private ComplaintsAdapter complaintAdapter;
    private ComplaintViewModel mComplaintViewModel;

    public ComplaintFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_complaint, container, false);

        //Initialize RecyclerView
        recyclerView = rootView.findViewById(R.id.recyclerComplaint);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize adapter with data
        complaintData = new ArrayList<Complaint>();
        complaintAdapter = new ComplaintsAdapter(getActivity(), complaintData);
        recyclerView.setAdapter(complaintAdapter);

        // Initialize IssueViewModel
        mComplaintViewModel = ViewModelProviders.of(this).get(ComplaintViewModel.class);
        mComplaintViewModel.getAllComplaints().observe(this, new Observer<List<Complaint>>() {
            @Override
            public void onChanged(@Nullable List<Complaint> complaints) {
                complaintAdapter.setComplaints(complaints);
            }
        });

        return recyclerView;
    }
}
