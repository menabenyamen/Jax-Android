package com.criminal.menabenyamen.taskr.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.criminal.menabenyamen.taskr.HttpService.TeamHttpRepository;
import com.criminal.menabenyamen.taskr.R;
import com.criminal.menabenyamen.taskr.adapter.TeamAdapter;
import com.criminal.menabenyamen.taskr.internetservice.ConnectedToInternet;
import com.criminal.menabenyamen.taskr.model.Team;
import com.criminal.menabenyamen.taskr.repository.TeamRepository;
import com.criminal.menabenyamen.taskr.sqlservice.SqlTeamRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TeamViewActivity extends AppCompatActivity {

    private TeamRepository teamRepository, sqlRepository;
    private RecyclerView recyclerView;
    private TeamAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private int position;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_team);

        teamRepository = new TeamHttpRepository();
        sqlRepository = new SqlTeamRepository(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_all_team);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        HashSet<Team> hashSet = new HashSet<>(teamRepository.getTeams());
        List<Team> list = new ArrayList<>(hashSet);

        for (Team team : list) {
            if (sqlRepository.getTeams().equals(null) &&
                    !sqlRepository.getTeams().contains(team.getTeamName()) ) {

                sqlRepository.addTeam(new Team(team.getTeamName(), team.getTeamStatus()));

            }
        }

        if(ConnectedToInternet.isOnline(this)){


        adapter = new TeamAdapter(list, this);
        adapter.notifyDataSetChanged();
        recyclerView.invalidate();
        recyclerView.setAdapter(adapter);


    } else {

        adapter = new TeamAdapter(sqlRepository.getTeams(), this);
        adapter.notifyDataSetChanged();
        recyclerView.invalidate();
        recyclerView.setAdapter(adapter);
    }
    }
}









