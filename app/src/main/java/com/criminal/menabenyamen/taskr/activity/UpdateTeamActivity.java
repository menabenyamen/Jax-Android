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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.criminal.menabenyamen.taskr.R;
import com.criminal.menabenyamen.taskr.internetservice.ConnectedToInternet;
import com.criminal.menabenyamen.taskr.model.Team;
import com.criminal.menabenyamen.taskr.repository.TeamRepository;
import com.criminal.menabenyamen.taskr.volley.CustomVolleyRequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateTeamActivity extends AppCompatActivity {

    private EditText oldTeamName, newTeamName;
    private Button updateButton;
    private RequestQueue mQueue;
    public static final String REQUEST_TAG = "PutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_team);

        oldTeamName = (EditText) findViewById(R.id.update_team_old);
        newTeamName = (EditText) findViewById(R.id.update_team_new);

        updateButton = (Button) findViewById(R.id.update_btn_new);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String assignee = oldTeamName.getText().toString();
                String newEditTeamName = newTeamName.getText().toString();

                if(ConnectedToInternet.isOnline(getApplicationContext())){

                    sendRequest(newEditTeamName ,assignee);


                }else if(!ConnectedToInternet.isOnline(getApplicationContext())) {

                    buildDialog(UpdateTeamActivity.this).show();
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
                Intent intent = new Intent(UpdateTeamActivity.this, TeamViewActivity.class);
                startActivity(intent);
                finish();
            }
        });
        return builder;
    }

    private boolean sendRequest(String newTeamName , String assignee) {

        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();

        final String url = "http://10.0.2.2:8080/teams/update/"+ newTeamName + "/" + assignee;

        final String inputValue = "{\"name\":\"" + newTeamName +"\","
                                   +"\"assignee\":\"" + assignee +"\"}";

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
