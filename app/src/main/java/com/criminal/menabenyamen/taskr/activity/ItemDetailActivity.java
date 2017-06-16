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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.criminal.menabenyamen.taskr.HttpService.ItemHttpRepository;
import com.criminal.menabenyamen.taskr.R;
import com.criminal.menabenyamen.taskr.internetservice.ConnectedToInternet;
import com.criminal.menabenyamen.taskr.model.WorkItem;
import com.criminal.menabenyamen.taskr.repository.ItemsReository;
import com.criminal.menabenyamen.taskr.sqlservice.SqlWorkItemRepository;
import com.criminal.menabenyamen.taskr.swipe.HomeActivity;
import com.criminal.menabenyamen.taskr.volley.CustomVolleyRequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemDetailActivity extends AppCompatActivity {

    private TextView title, description, state, assignee;
    private Button updateButton;
    private EditText editTitle,editDescription, editState, editAssignee;
    private ItemsReository itemsReository, sqlRepository;
    private RequestQueue mQueue;
    public static final String REQUEST_TAG = "PutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        itemsReository = new ItemHttpRepository();
        sqlRepository = new SqlWorkItemRepository(this);

        title = (TextView) findViewById(R.id.update_title);
        description = (TextView) findViewById(R.id.update_description);
        state = (TextView) findViewById(R.id.update_status);
        assignee = (TextView) findViewById(R.id.update_assignee);


        editTitle = (EditText) findViewById(R.id.update_edit_title);
        editDescription = (EditText) findViewById(R.id.update_edit_description);
        editState = (EditText) findViewById(R.id.update_edit_status);
        editAssignee = (EditText) findViewById(R.id.update_edit_assignee);

        updateButton = (Button) findViewById(R.id.update_btn);

        title.setText("Title :" + getIntent().getStringExtra("title"));
        description.setText("Description :" + getIntent().getStringExtra("description"));
        state.setText("State :" + getIntent().getStringExtra("state"));
        assignee.setText("Assignee :" + getIntent().getStringExtra("assignee"));

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editTil= editTitle.getText().toString();
                String editDes = editDescription.getText().toString();
                String editSta = editState.getText().toString();
                String editAss = editAssignee.getText().toString();


                if(!ConnectedToInternet.isOnline(ItemDetailActivity.this)){
                    buildDialog(ItemDetailActivity.this).show();


                } else if(ConnectedToInternet.isOnline(ItemDetailActivity.this)) {

                    sendRequest(editTil, editDes, editSta, editAss);
                    sqlRepository.updateWorkItem(new WorkItem(editTil, editDes, editSta, editAss));

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
                Intent intent = new Intent(ItemDetailActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        return builder;
    }

    private boolean sendRequest(final String itemTitle, final String itemDescription,
                                final String itemStatus, final String itemAssignee) {

        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();

        final String url = "http://10.0.2.2:8080/items/update/"+ itemTitle +"/"+
                itemDescription +"/"+ itemStatus+ "/" + itemAssignee;

        final String inputValue = "{\"title\":\"" + itemTitle +"\","
                +"\"description\":\"" +itemDescription+"\","
                +"\"status\":\"" + itemStatus+"\","
                +"\"assignee\":\""+itemAssignee+"\"}";

        JSONObject body = null;
        try {
            body = new JSONObject(inputValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, url, body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(getApplicationContext(), "Update Ok", Toast.LENGTH_LONG).show();
                    }

                } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        jsonRequest.setTag(REQUEST_TAG);
        mQueue.add(jsonRequest);

        return true;
    }






}
