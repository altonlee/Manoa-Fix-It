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

public class IssuesAdapter extends RecyclerView.Adapter<IssuesAdapter.ViewHolder> {
    // Member variables
    private List<Issue> issueData;
    private Context context;

    /**
     * Constructor that passes in issue data and context.
     * @param issueData: an ArrayList containing the issues.
     * @param context: Context of the application.
     */
    IssuesAdapter(Context context, List<Issue> issueData) {
        this.issueData = issueData;
        this.context = context;
    }

    /**
     * Required method used to create viewholder objects.
     * @param parent: ViewGroup the View will be added to
     * @param viewType: view type of new View
     * @return the newly created ViewHolder.
     */
    @Override
    public IssuesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_issue, parent, false));
    }

    /**
     * Required method used to bind data to viewholder.
     * @param holder: viewholder the data should be put into
     * @param pos: position of adapter
     */
    @Override
    public void onBindViewHolder(IssuesAdapter.ViewHolder holder, int pos) {
        // Get current issue
        Issue curr = issueData.get(pos);
        // populate textview with issue data
        holder.bindTo(curr);
    }

    void setIssues(List<Issue> issues){
        issueData = issues;
        notifyDataSetChanged();
    }

    /**
     * Returns size of data set.
     * @return size of data set.
     */
    @Override
    public int getItemCount() {
        return issueData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Member variables
        private TextView title;
        private TextView loc;
        private TextView status;
        private TextView date;
        private TextView info;
        private TextView pts;
        private ImageView image;

        /**
         * Constructor for ViewHolder
         * @param itemView: rootview of list_issue.xmll layout file
         */
        ViewHolder(View itemView) {
            super(itemView);
            // Initialize member variables
            title = itemView.findViewById(R.id.issueTitle);
            loc = itemView.findViewById(R.id.issueLoc);
            status = itemView.findViewById(R.id.issueStatus);
            date = itemView.findViewById(R.id.issueDate);
            info = itemView.findViewById(R.id.issueDesc);
            pts = itemView.findViewById(R.id.issuePoints);
            image = itemView.findViewById(R.id.issueImage);
            // Set OnClickListener to entire view
            itemView.setOnClickListener(this);
        }
        /**
         * Method binds passed in issue data with View
         * @param curr: current Issue
         */
        void bindTo(Issue curr) {
            // Format date
            SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy");
            Date res = new Date(curr.getDate());

            // Populate View with issue details
            title.setText(curr.getTitle());
            loc.setText(curr.getLoc());
            status.setText(curr.getStatus());
            date.setText(df.format(res));
            info.setText(curr.getDesc());
            pts.setText(curr.getPoints() + "");
            Glide.with(context).load(curr.getImageResource()).into(image);
        }

        /**
         * Method handles onClick activity
         * @param view: it does stuff
         */
        @Override
        public void onClick(View view) {
            Issue curr = issueData.get(getAdapterPosition());
            Intent detailIntent = new Intent(context, IssueActivity.class);
            detailIntent.putExtra("postID", curr.getPostId());
            detailIntent.putExtra("userID", curr.getUserId());
            detailIntent.putExtra("title", curr.getTitle());
            detailIntent.putExtra("loc", curr.getLoc());
            detailIntent.putExtra("status", curr.getStatus());
            detailIntent.putExtra("date", curr.getDate());
            detailIntent.putExtra("desc", curr.getDesc());
            detailIntent.putExtra("points", curr.getPoints());
            detailIntent.putExtra("image_resource", curr.getImageResource());
            context.startActivity(detailIntent);
        }
    }
}
