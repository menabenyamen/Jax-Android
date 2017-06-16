package com.criminal.menabenyamen.taskr.HttpService;


import com.criminal.menabenyamen.taskr.http.ApiCommand;
import com.criminal.menabenyamen.taskr.http.AsyncTaskItem;
import com.criminal.menabenyamen.taskr.http.HttpClient;
import com.criminal.menabenyamen.taskr.http.HttpResponse;
import com.criminal.menabenyamen.taskr.model.Team;

import com.criminal.menabenyamen.taskr.model.WorkItem;
import com.criminal.menabenyamen.taskr.repository.TeamRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class TeamHttpRepository extends HttpClient implements TeamRepository {

    private final String BASE_URL = "http://10.0.2.2:8080/";

    @Override
    public List<Team> getTeams() {
        try {
            HttpResponse httpResponse = new AsyncTaskItem(new ApiCommand() {
                @Override
                public HttpResponse execute() {
                    return get(BASE_URL + "teams/all");
                }
            }).execute().get();
            return parserTeams(httpResponse.getResponseAsString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Team getTeam(long id) {
        return null;
    }

    @Override
    public long addTeam(Team team) {
        return 0;
    }

    @Override
    public Team removeTeam(long id) {
        return null;
    }


    @Override
    public List<String> getAllassigneeForTeam(final String teamName) {
        try {
            HttpResponse httpResponse = new AsyncTaskItem(new ApiCommand() {
                @Override
                public HttpResponse execute() {
                    return get(BASE_URL + "teams/all/" + teamName);
                }
            }).execute().get();
            return parserAssignee(httpResponse.getResponseAsString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Team parserTeam(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            long id = jsonObject.getLong("id");
            String teamName = jsonObject.getString("teamName");
            String teamState = jsonObject.getString("teamStatus");
            String assignee = jsonObject.getString("assignee");

            return new Team(id, teamName, teamState, assignee);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Team> parserTeams(String jsonString) {
        try {
            List<Team> teamItems = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                long id = jsonObject.getLong("id");
                String teamName = jsonObject.getString("teamName");
                String teamState = jsonObject.getString("teamStatus");
                String assignee = jsonObject.getString("assignee");
                teamItems.add(new Team(id, teamName, teamState, assignee));
            }

            return teamItems;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> parserAssignee(String jsonString) {
        try {

            List<String> teamItems = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String assignee = jsonObject.getString("assignee");
                teamItems.add(assignee);
            }

            return teamItems;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}