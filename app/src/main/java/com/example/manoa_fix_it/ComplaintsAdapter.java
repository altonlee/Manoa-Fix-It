package com.example.manoa_fix_it;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ComplaintsAdapter extends RecyclerView.Adapter<ComplaintsAdapter.ViewHolder> {
    private List<Complaint> complData;
    private Context context;

    ComplaintsAdapter(Context context, List<Complaint> complData) {
        this.complData = complData;
        this.context = context;
    }

    @Override
    public ComplaintsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_complaint, parent, false));
    }

    @Override
    public void onBindViewHolder(ComplaintsAdapter.ViewHolder holder, int pos) {
        // Get current complaint
        Complaint curr = complData.get(pos);
        // populate view with data
        holder.bindTo(curr);
    }

    void setComplaints(List<Complaint> complaints) {
        complData = complaints;
        notifyDataSetChanged();
    }

    /**
     * Returns size of data set.
     * @return size of data set.
     */
    @Override
    public int getItemCount() {
        return complData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Member variables
        private TextView title;
        private TextView loc;
        private TextView date;
        private TextView pts;
        private ImageView image;

        /**
         * Constructor for ViewHolder
         * @param itemView: rootview of list_issue.xmll layout file
         */
        ViewHolder(View itemView) {
            super(itemView);
            // Initialize member variables
            title = itemView.findViewById(R.id.complaintTitle);
            loc = itemView.findViewById(R.id.complaintLoc);
            date = itemView.findViewById(R.id.complaintDate);
            pts = itemView.findViewById(R.id.complaintPoints);
            image = itemView.findViewById(R.id.complaintImage);
            // Set OnClickListener to entire view
            itemView.setOnClickListener(this);
        }
        /**
         * Method binds passed in issue data with View
         * @param curr: current Issue
         */
        void bindTo(Complaint curr) {
            // Format date
            SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy");
            Date res = new Date(curr.getDate());

            // Populate View with issue details
            title.setText(curr.getTitle());
            loc.setText(curr.getLoc());
            date.setText(df.format(res));
            pts.setText(curr.getPoints() + "");
            Glide.with(context).load(R.drawable.img_complaint).into(image);
        }

        /**
         * Method handles onClick activity
         * @param view: it does stuff
         */
        @Override
        public void onClick(View view) {
            Complaint curr = complData.get(getAdapterPosition());
            Intent detailIntent = new Intent(context, ComplaintActivity.class);
            detailIntent.putExtra("postID", curr.getPostId());
            detailIntent.putExtra("userID", curr.getUserId());
            detailIntent.putExtra("title", curr.getTitle());
            detailIntent.putExtra("loc", curr.getLoc());
            detailIntent.putExtra("date", curr.getDate());
            detailIntent.putExtra("desc", curr.getDesc());
            detailIntent.putExtra("points", curr.getPoints());
            context.startActivity(detailIntent);
        }
    }
}
