package com.criminal.menabenyamen.taskr.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.criminal.menabenyamen.taskr.R;
import com.criminal.menabenyamen.taskr.activity.ItemDetailActivity;
import com.criminal.menabenyamen.taskr.model.WorkItem;

import java.util.ArrayList;
import java.util.List;


public final class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.RecyclerViewHolder>{

   public List<WorkItem> workItems;
    private Context ctx;

    public SearchAdapter(List<WorkItem> workItems, Context ctx) {
        this.workItems = workItems;
        this.ctx = ctx;
    }



    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view, ctx, workItems);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        WorkItem workItem = workItems.get(position);

        holder.title.setText(workItems.get(position).getTitle());
        holder.description.setText(workItems.get(position).getDescription());
        holder.state.setText(workItems.get(position).getStatus());
        holder.assignee.setText(workItems.get(position).getAssignee());

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return workItems.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final private TextView title;
        final private TextView description;
        final private TextView state;
        final private TextView assignee;
        private List<WorkItem> workItems = new ArrayList<WorkItem>();
        Context ctx;

        public RecyclerViewHolder(View view, Context ctx, List<WorkItem> workItems) {
            super(view);
            this.ctx = ctx;
            this.workItems = workItems;
            view.setOnClickListener(this);

            title = (TextView) view.findViewById(R.id.workitem_title);
            description = (TextView) view.findViewById(R.id.workitem_description);
            state = (TextView) view.findViewById(R.id.workitem_statusbar);
            assignee = (TextView) view.findViewById(R.id.assigned_user);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            WorkItem workItem = this.workItems.get(position);
            Intent intent = new Intent(this.ctx, ItemDetailActivity.class);
            intent.putExtra("title", workItem.getTitle());
            intent.putExtra("description", workItem.getDescription());
            intent.putExtra("state", workItem.getStatus());
            intent.putExtra("assignee", workItem.getAssignee());
            this.ctx.startActivity(intent);

        }
    }

    public void setFilter(List<WorkItem> newWoritems){
        workItems = new ArrayList<>();
        workItems.addAll(newWoritems);
        notifyDataSetChanged();

    }
}
