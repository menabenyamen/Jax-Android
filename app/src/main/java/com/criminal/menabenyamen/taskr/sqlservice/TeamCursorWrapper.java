package com.criminal.menabenyamen.taskr.sqlservice;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.criminal.menabenyamen.taskr.model.Team;

public final class TeamCursorWrapper extends CursorWrapper{


    public TeamCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Team getTeam() {
        long id = getLong(getColumnIndexOrThrow(ItemsDbContract.TeamsEntry._ID));
        String title = getString(getColumnIndexOrThrow(ItemsDbContract.TeamsEntry.COLUMN_TEAM_NAME));
        String descritpion = getString(getColumnIndexOrThrow(ItemsDbContract.TeamsEntry.COLUMN_TEAM_STATUS));
        String state = getString(getColumnIndexOrThrow(ItemsDbContract.TeamsEntry.COLUMN_TEAM_ASSIGNEE));




        return new Team(id, title, descritpion, state);
    }

    public String getAssignee(){
        String assignee = getString(getColumnIndexOrThrow(ItemsDbContract.WorkItemsEntry.COLUMN_NAME_ASSIGNEE));
        return assignee;

    }

    public Team getFirstTeam() {
        moveToFirst();
        return getTeam();
    }


}
