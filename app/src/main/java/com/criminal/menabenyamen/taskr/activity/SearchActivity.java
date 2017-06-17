package com.criminal.menabenyamen.taskr.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import com.criminal.menabenyamen.taskr.R;
import com.criminal.menabenyamen.taskr.adapter.SearchAdapter;
import com.criminal.menabenyamen.taskr.model.WorkItem;
import com.criminal.menabenyamen.taskr.repository.ItemsReository;
import com.criminal.menabenyamen.taskr.sqlservice.SqlWorkItemRepository;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private ItemsReository sqlRepository;
    private String title;
    private List<WorkItem> newList;
    private RecyclerView recyclerView;
    private SearchAdapter workItemListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private MenuItem menuItem;
  


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        sqlRepository = new SqlWorkItemRepository(this);
        menuItem = (MenuItem) findViewById(R.id.action_serach_search);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_search);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        workItemListAdapter = new SearchAdapter(sqlRepository.getAllWorkItems(), this);
        workItemListAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(workItemListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_search, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_serach_search).getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        newList = new ArrayList<>();
        for (WorkItem workItem : sqlRepository.getAllWorkItems()){
            title = workItem.getTitle().toLowerCase();
            if (title.contains(newText)){
                newList.add(workItem);
            }

            workItemListAdapter.setFilter(newList);
        }
        return true;
    }

    }
