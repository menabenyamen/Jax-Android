package com.criminal.menabenyamen.taskr.swipe;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.criminal.menabenyamen.taskr.R;
import com.criminal.menabenyamen.taskr.activity.AddWorkItem;
import com.criminal.menabenyamen.taskr.activity.SearchActivity;
import com.criminal.menabenyamen.taskr.activity.TeamViewActivity;
import com.criminal.menabenyamen.taskr.internetservice.ConnectedToInternet;
import com.criminal.menabenyamen.taskr.repository.ItemsReository;
import com.criminal.menabenyamen.taskr.sqlservice.SqlWorkItemRepository;

public class HomeActivity extends AppCompatActivity{

    private ProgressBar progressBarUnstarted;
    private ProgressBar progressBarStarted;
    private ProgressBar progressBarDone;
    private ProgressBar progressBarMyTask;
    private ItemsReository itemsReository;
    private ItemsReository sqlRep;
    private TextView tvUnstarted, tvStarted, tvDone, tvMyTask;
    private MenuItem search;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_bar_cycles);
        sqlRep = new SqlWorkItemRepository(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddWorkItem.class);
                startActivity(intent);
            }
        });



        progressBarUnstarted = (ProgressBar) findViewById(R.id.UNSTARTED);
        progressBarStarted = (ProgressBar) findViewById(R.id.STARTED);
        progressBarDone = (ProgressBar) findViewById(R.id.DONE);
        progressBarMyTask = (ProgressBar) findViewById(R.id.MyTask);
        tvUnstarted = (TextView) findViewById(R.id.tv_unstarted_number);
        tvStarted = (TextView) findViewById(R.id.tv_started_number);
        tvDone = (TextView) findViewById(R.id.tv_done_number);
        tvMyTask = (TextView) findViewById(R.id.tv_my_items_number);
        search = (MenuItem) findViewById(R.id.action_serach);

        tvUnstarted.setText(Integer.toString(sqlRep.getWorkItemsWhihUnstarted().size()));
        tvStarted.setText(Integer.toString(sqlRep.getWorkItemsWhihStarted().size()));
        tvDone.setText(Integer.toString(sqlRep.getWorkItemsWhihDone().size()));
        tvMyTask.setText(Integer.toString(sqlRep.getWorkItemsByAssignee("As").size()));

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        StartedFragment startedFragment = new StartedFragment();
        transaction.add(R.id.fragment_placeholder, startedFragment);



    }

    public void updateCycles(){
        if (ConnectedToInternet.isOnline(this)){

            tvUnstarted.setText(Integer.toString(itemsReository.getWorkItemsWhihUnstarted().size()));
            tvStarted.setText(Integer.toString(itemsReository.getWorkItemsWhihStarted().size()));
            tvDone.setText(Integer.toString(itemsReository.getWorkItemsWhihDone().size()));
            tvMyTask.setText(Integer.toString(itemsReository.getWorkItemsByAssignee("As").size()));

        }else if (ConnectedToInternet.isOnline(this)){
            tvUnstarted.setText(Integer.toString(sqlRep.getWorkItemsWhihUnstarted().size()));
            tvStarted.setText(Integer.toString(sqlRep.getWorkItemsWhihStarted().size()));
            tvDone.setText(Integer.toString(sqlRep.getWorkItemsWhihDone().size()));
            tvMyTask.setText(Integer.toString(sqlRep.getWorkItemsByAssignee("As").size()));
        }
    }

    public void onSelectedFragment(View view){
        Fragment newFragment;

        if(view == findViewById(R.id.UNSTARTED)){
            newFragment = new UnstartedFragment();

        }else if (view == findViewById(R.id.STARTED)){
            newFragment = new StartedFragment();

        }else if (view == findViewById(R.id.DONE)){
            newFragment = new DoneFragment();

        }else if (view == findViewById(R.id.MyTask)){
            newFragment = new MyTaskFragment();

        }else {
            newFragment = new UnstartedFragment();
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        getMenuInflater().inflate(R.menu.menu_option,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.action_serach:

                startActivity(new Intent(HomeActivity.this,SearchActivity.class));
                break;

            case R.id.team_menu :
                startActivity(new Intent(HomeActivity.this, TeamViewActivity.class));
                break;

            case R.id.log_out_menu :
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }



}

