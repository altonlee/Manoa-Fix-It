package com.example.android.materialme;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class IssuesAdapter extends RecyclerView.Adapter<IssuesAdapter.ViewHolder> {

    private ArrayList<Issue> mIssueData;
    private Context mContext;

    IssuesAdapter(Context context, ArrayList<Issue> issueData) {
        this.mIssueData = issueData;
        this.mContext = context;
    }

    @Override
    public IssuesAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(IssuesAdapter.ViewHolder holder,
                                 int position) {
        // Get current sport.
        Issue currentIssue = mIssueData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentIssue);
    }

    @Override
    public int getItemCount() {
        return mIssueData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Member Variables for the TextViews
        private TextView mTitleText;
        private TextView mDescriptionText;
        private TextView mLocationText;
        private TextView mDateText;
        private ImageView mIssueImage;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.title);
            mDescriptionText = itemView.findViewById(R.id.description);
            mLocationText = itemView.findViewById(R.id.location);
            mDateText = itemView.findViewById(R.id.date);
            mIssueImage = itemView.findViewById(R.id.issueImage);

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        void bindTo(Issue currentIssue) {
            // Populate the textviews with data.
            mTitleText.setText(currentIssue.getTitle());
            mDescriptionText.setText(currentIssue.getDescription());
            mLocationText.setText(currentIssue.getLocation());
            mDateText.setText(currentIssue.getDate());

            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(
                    currentIssue.getImageResource()).into(mIssueImage);
        }

        @Override
        public void onClick(View view) {
            Issue currentIssue = mIssueData.get(getAdapterPosition());
            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            detailIntent.putExtra("title", currentIssue.getTitle());
            detailIntent.putExtra("description", currentIssue.getDescription());
            detailIntent.putExtra("location", currentIssue.getLocation());
            detailIntent.putExtra("date", currentIssue.getDate());
            detailIntent.putExtra("image_resource",
                    currentIssue.getImageResource());
            mContext.startActivity(detailIntent);
        }
    }
}
