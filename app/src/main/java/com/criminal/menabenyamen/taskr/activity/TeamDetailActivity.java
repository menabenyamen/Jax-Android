package com.criminal.menabenyamen.taskr.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.criminal.menabenyamen.taskr.HttpService.TeamHttpRepository;
import com.criminal.menabenyamen.taskr.R;
import com.criminal.menabenyamen.taskr.internetservice.ConnectedToInternet;
import com.criminal.menabenyamen.taskr.repository.TeamRepository;
import com.criminal.menabenyamen.taskr.sqlservice.SqlTeamRepository;


public class TeamDetailActivity extends AppCompatActivity {

    private Button addMembers, updateTeam;
    private TextView teamName, teamState;
    private EditText editTeamName, editTeamState;
    private TeamRepository teamRepository, sqlTeamRepository;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        teamRepository = new TeamHttpRepository();
        sqlTeamRepository = new SqlTeamRepository(this);

        addMembers = (Button) findViewById(R.id.member_btn);
        updateTeam = (Button) findViewById(R.id.update_btn);
        addMembers.setBackgroundColor(0xffffff);

        teamName = (TextView) findViewById(R.id.tv_team_name);
        String take = teamName.getText().toString();
        teamState = (TextView) findViewById(R.id.tv_team_status);

        teamName.setText(getIntent().getStringExtra("teamName"));
        teamState.setText(getIntent().getStringExtra("teamStatus"));

        updateTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TeamDetailActivity.this, UpdateTeamActivity.class);
                startActivity(intent);

            }
        });

        addMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TeamDetailActivity.this, AssigneeActivity.class);
                startActivity(intent);
            }
        });



    }





}
