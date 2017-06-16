package com.criminal.menabenyamen.taskr.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.criminal.menabenyamen.taskr.R;
import com.criminal.menabenyamen.taskr.activity.AssigneeActivity;
import com.criminal.menabenyamen.taskr.activity.TeamDetailActivity;
import com.criminal.menabenyamen.taskr.activity.TeamViewActivity;
import com.criminal.menabenyamen.taskr.model.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by menabenyamen on 2017-06-14.
 */

public final class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.RecyclerViewHolder>{

    public List<Team> teams;
    private Context ctx;

    public TeamAdapter(List<Team> teams, Context ctx) {
        this.teams = teams;
        this.ctx = ctx;
    }



    @Override
    public TeamAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_team_all, parent, false);
        TeamAdapter.RecyclerViewHolder recyclerViewHolder = new TeamAdapter.RecyclerViewHolder(view, ctx, teams);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(TeamAdapter.RecyclerViewHolder holder, int position) {

        Team team = teams.get(position);

        holder.teamName.setText(teams.get(position).getTeamName());
        holder.teamState.setText(teams.get(position).getTeamStatus());


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
        return teams.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final private TextView teamName;
        final private TextView teamState;

        private List<Team> teams = new ArrayList<Team>();
        Context ctx;

        public RecyclerViewHolder(View view, Context ctx, List<Team> teams) {
            super(view);
            this.ctx = ctx;
            this.teams = teams;
            view.setOnClickListener(this);

            teamName = (TextView) view.findViewById(R.id.team_name);
            teamState = (TextView) view.findViewById(R.id.team_state);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            Team team = this.teams.get(position);
            Intent intent = new Intent(this.ctx, TeamDetailActivity.class);
            intent.putExtra("teamName", team.getTeamName());
            intent.putExtra("teamStatus", team.getTeamStatus());
            this.ctx.startActivity(intent);




        }
    }


}
