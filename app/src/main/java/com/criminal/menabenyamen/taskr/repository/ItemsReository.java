package com.criminal.menabenyamen.taskr.repository;

import com.criminal.menabenyamen.taskr.model.WorkItem;

import java.util.List;

/**
 * Created by menabenyamen on 2017-06-08.
 */

public interface ItemsReository {

    //WorkItem getItem(long id);
    long addWorkItem(WorkItem workItem);
    void removeWorkItem(long id);
    List<WorkItem> getAllWorkItems();
    List<WorkItem> getWorkItemsWhihUnstarted();
    List<WorkItem> getWorkItemsWhihStarted();
    List<WorkItem> getWorkItemsByAssignee(String assignee);
    List<WorkItem> getWorkItemsWhihDone();
    boolean updateWorkItem(WorkItem workItem);

}
