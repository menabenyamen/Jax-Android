package com.criminal.menabenyamen.taskr.HttpService;



import com.criminal.menabenyamen.taskr.http.ApiCommand;
import com.criminal.menabenyamen.taskr.http.AsyncTaskItem;
import com.criminal.menabenyamen.taskr.http.HttpClient;
import com.criminal.menabenyamen.taskr.http.HttpResponse;
import com.criminal.menabenyamen.taskr.model.WorkItem;
import com.criminal.menabenyamen.taskr.repository.ItemsReository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by menabenyamen on 2017-06-08.
 */

public class ItemHttpRepository extends HttpClient implements ItemsReository {

    private final String URL = "http://10.0.2.2:8080/";

  // @Override
    //public WorkItem getItem(final long id) {

      //  try {
        //    HttpResponse httpResponse = new AsyncTaskItem(new ApiCommand() {
          //      @Override
            //    public HttpResponse execute() {
              //      return get(URL + "workitems/" + id);
               // }
            //}).execute().get();
          //  return parserWorkItem(httpResponse.getResponseAsString());
        //} catch (InterruptedException | ExecutionException e) {
          //  e.printStackTrace();
        //}
        //return null;
    //}

    @Override
    public long addWorkItem(WorkItem workItem) {

        final String body =
                "{" +
                        "\"id\": " + workItem.getId() + "," +
                        "\"title\": \"" + workItem.getTitle() + "\"," +
                        "\"description\": \"" + workItem.getDescription() + "\"," +
                        "\"status\": \"" + workItem.getStatus() + "\"," +
                        "\"assignee\": \"" + workItem.getAssignee() + "\"" +
                        "}";

        try {
            HttpResponse httpResponse = new AsyncTaskItem(new ApiCommand() {
                @Override
                public HttpResponse execute() {
                    return post(URL + "items/", body);
                }
            }).execute().get();

            if (httpResponse.getStatusCode() == 201) {
                String[] splitArray = httpResponse.getHeaders().get("Location").get(0).split("/")
                        ;
                String returnValue = splitArray[splitArray.length - 1];
                return Long.valueOf(returnValue);
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    @Override
    public void removeWorkItem(long id) {

    }

    @Override
    public List<WorkItem> getAllWorkItems() {

        try {
            HttpResponse httpResponse = new AsyncTaskItem(new ApiCommand() {
                @Override
                public HttpResponse execute() {
                    return get(URL + "items/all/items");
                }
            }).execute().get();
            return parserWorkItems(httpResponse.getResponseAsString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<WorkItem> getWorkItemsWhihUnstarted() {
        try {
            HttpResponse httpResponse = new AsyncTaskItem(new ApiCommand() {
                @Override
                public HttpResponse execute() {
                    return get(URL + "items/UNSTARTED");
                }
            }).execute().get();
            return parserWorkItems(httpResponse.getResponseAsString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<WorkItem> getWorkItemsWhihStarted() {
        try {
            HttpResponse httpResponse = new AsyncTaskItem(new ApiCommand() {
                @Override
                public HttpResponse execute() {
                    return get(URL + "items/STARTED");
                }
            }).execute().get();
            return parserWorkItems(httpResponse.getResponseAsString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<WorkItem> getWorkItemsByAssignee(final String assignee) {

        try {
            HttpResponse httpResponse = new AsyncTaskItem(new ApiCommand() {
                @Override
                public HttpResponse execute() {
                    return get(URL + "items/view/" + assignee);
                }
            }).execute().get();
            return parserWorkItems(httpResponse.getResponseAsString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<WorkItem> getWorkItemsWhihDone() {

        try {
            HttpResponse httpResponse = new AsyncTaskItem(new ApiCommand() {
                @Override
                public HttpResponse execute() {
                    return get(URL + "items/DONE");
                }
            }).execute().get();
            return parserWorkItems(httpResponse.getResponseAsString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean updateWorkItem(final WorkItem workItem) {

        final String body =
                "{" +

                        "\"title\": \"" + workItem.getTitle() + "\"," +
                        "\"description\": \"" + workItem.getDescription() + "\"," +
                        "\"status\": \"" + workItem.getStatus() + "\"," +
                        "\"assignee\": \"" + workItem.getAssignee() + "\"" +
                        "}";

        try {
            HttpResponse httpResponse = new AsyncTaskItem(new ApiCommand() {
                @Override
                public HttpResponse execute() {
                    return put(URL + "items/update/title/description/status/assignee", body);
                }
            }).execute().get();

            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    private WorkItem parserWorkItem(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            long id = jsonObject.getLong("id");
            String title = jsonObject.getString("title");
            String description = jsonObject.getString("description");
            String status = jsonObject.getString("status");
            String assignee = jsonObject.getString("assignee");

            WorkItem workItem = new WorkItem(id, title, description, status,assignee);
            workItem.setStatus(status);

            return workItem;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<WorkItem> parserWorkItems(String jsonString) {
        try {
            List<WorkItem> workItems = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                long id = jsonObject.getLong("id");
                String title = jsonObject.getString("title");
                String description = jsonObject.getString("description");
                String status = jsonObject.getString("status");
                String assignee = jsonObject.getString("assignee");

                WorkItem workItem = new WorkItem(id, title, description, status,assignee);
                workItem.setStatus(status);

                workItems.add(workItem);
            }

            return workItems;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
