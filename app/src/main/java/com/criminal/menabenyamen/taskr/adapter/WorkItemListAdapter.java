package com.criminal.menabenyamen.taskr.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.criminal.menabenyamen.taskr.R;
import com.criminal.menabenyamen.taskr.activity.AssigneeActivity;
import com.criminal.menabenyamen.taskr.activity.ItemDetailActivity;
import com.criminal.menabenyamen.taskr.model.WorkItem;

import java.util.ArrayList;
import java.util.List;


public final class WorkItemListAdapter extends RecyclerView.Adapter<WorkItemListAdapter.RecyclerViewHolder>{

   public List<WorkItem> workItems;
    private Context ctx;
    private onCLickResultListener onCLickResultListener;
    private onLongClickListener onLongClickListener;

    public WorkItemListAdapter(List<WorkItem> workItems, Context ctx,
                               onCLickResultListener onCLickResultListener,
                               onLongClickListener onLongClickListener) {

        this.workItems = workItems;
        this.ctx = ctx;

        this.onCLickResultListener = onCLickResultListener;
        this.onLongClickListener = onLongClickListener;
    }

    public void setAdapter(List<WorkItem> workItemList) {

        this.workItems = workItemList;
        this.notifyDataSetChanged();
    }

    public interface onCLickResultListener {
        void onClickResult(WorkItem workitem);
    }

    public interface onLongClickListener {
        void onLongClickResult(WorkItem workItem);
    }

    public interface onClickDetailResult{
        void onClickDeatial(WorkItem workItem);
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
        holder.bindView(workItem, onCLickResultListener, onLongClickListener);

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

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        final private TextView title;
        final private TextView description;
        final private TextView state;
        final private TextView assignee;
        private List<WorkItem> workItems = new ArrayList<WorkItem>();
        Context ctx;

        public RecyclerViewHolder(View itemView, Context ctx, List<WorkItem> workItems) {
            super(itemView);
            this.ctx = ctx;
            this.workItems = workItems;


            title = (TextView) itemView.findViewById(R.id.workitem_title);
            description = (TextView) itemView.findViewById(R.id.workitem_description);
            state = (TextView) itemView.findViewById(R.id.workitem_statusbar);
            assignee = (TextView) itemView.findViewById(R.id.assigned_user);
        }

        void bindView(final WorkItem workItem, final onCLickResultListener onCLickResultListener, final WorkItemListAdapter.onLongClickListener onLongClickListener) {

            title.setText(workItem.getTitle());
            description.setText(workItem.getDescription());
            state.setText(workItem.getStatus());
            setupColor(state);
            assignee.setText(workItem.getAssignee());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCLickResultListener.onClickResult(workItem);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onLongClickListener.onLongClickResult(workItem);
                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    WorkItem workItem = workItems.get(position);
                    Intent intent = new Intent(ctx, ItemDetailActivity.class);
                    intent.putExtra("title", workItem.getTitle());
                    intent.putExtra("description", workItem.getDescription());
                    intent.putExtra("state", workItem.getStatus());
                    intent.putExtra("assignee", workItem.getAssignee());
                    ctx.startActivity(intent);
                }
            });



        }

        private void setupColor(TextView status){
            switch (status.getText().toString()){
                case "UNSTARTED" :{
                    status.setBackgroundColor(itemView.getResources().getColor(R.color.primary_gray));
                    break;
                }
                case "STARTED" :{
                    status.setBackgroundColor(itemView.getResources().getColor(R.color.primary_orange));
                    break;
                }
                case "DONE" :{
                    status.setBackgroundColor(itemView.getResources().getColor(R.color.colorAccent));
                    break;
                }
            }
        }
    }


}