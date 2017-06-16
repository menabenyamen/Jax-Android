package com.criminal.menabenyamen.taskr.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.criminal.menabenyamen.taskr.HttpService.ItemHttpRepository;
import com.criminal.menabenyamen.taskr.R;
import com.criminal.menabenyamen.taskr.adapter.WorkItemListAdapter;
import com.criminal.menabenyamen.taskr.internetservice.ConnectedToInternet;
import com.criminal.menabenyamen.taskr.model.WorkItem;
import com.criminal.menabenyamen.taskr.repository.ItemsReository;
import com.criminal.menabenyamen.taskr.sqlservice.SqlWorkItemRepository;
import com.criminal.menabenyamen.taskr.swipe.HomeActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AddWorkItem extends AppCompatActivity {

    private EditText title, description, state, assignee;
    private Button saveButton;

    private String titleEdit;
    private String descriptionEdit;
    private String stateEdit;
    private String assigneeEdit;
    private Button viewButton;
    private ItemsReository itemsReository, sqlRepository;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_item);

        itemsReository = new ItemHttpRepository();
        sqlRepository = new SqlWorkItemRepository(this);


        title = (EditText) findViewById(R.id.edit_title);
        description = (EditText) findViewById(R.id.edit_description);
        state = (EditText) findViewById(R.id.edit_status);
        assignee = (EditText) findViewById(R.id.edit_assignee);

        saveButton = (Button) findViewById(R.id.btn_save);



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleEdit = title.getText().toString();
                descriptionEdit = description.getText().toString();
                stateEdit = state.getText().toString();
                assigneeEdit = assignee.getText().toString();


                if(!ConnectedToInternet.isOnline(AddWorkItem.this)){
                    buildDialog(AddWorkItem.this).show();


                } else if(ConnectedToInternet.isOnline(AddWorkItem.this)) {

                    itemsReository.addWorkItem(new WorkItem(titleEdit, descriptionEdit, stateEdit, assigneeEdit));
                    sqlRepository.addWorkItem(new WorkItem(titleEdit, descriptionEdit, stateEdit, assigneeEdit));

                }

            }

        });


    }

    public AlertDialog.Builder buildDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to start WIFI to add items");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(AddWorkItem.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        return builder;
    }

}
