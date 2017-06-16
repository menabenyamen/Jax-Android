package com.criminal.menabenyamen.taskr.sqlservice;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.criminal.menabenyamen.taskr.model.Team;
import com.criminal.menabenyamen.taskr.repository.TeamRepository;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by menabenyamen on 2017-05-26.
 */

public final class SqlTeamRepository implements TeamRepository {

    private static SqlTeamRepository instance;

    public static synchronized SqlTeamRepository getInstance(Context context) {
        if(instance == null) {
            instance = new SqlTeamRepository(context);
        }

        return instance;
    }

    private final SQLiteDatabase database;

    public SqlTeamRepository(Context context) {
        database = ItemsDbHelper.getInstance(context).getWritableDatabase();
    }

    @Override
    public List<Team> getTeams() {
        TeamCursorWrapper cursor = queryTeams(null, null);

        List<Team> teams = new ArrayList<>();
        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                teams.add(cursor.getTeam());
            }
        }

        cursor.close();

        return teams;
    }

    @Override
    public List<String> getAllassigneeForTeam(String teamName) {
        TeamCursorWrapper cursor = queryTeams(ItemsDbContract.TeamsEntry.COLUMN_TEAM_NAME + " = ?", new String[] { teamName });

        List<String> assignees = new ArrayList<>();
        if(cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                assignees.add(cursor.getAssignee());
            }
        }

        cursor.close();

        return assignees;
    }



    @Override
    public Team getTeam(long id) {
        TeamCursorWrapper cursor = queryTeams(ItemsDbContract.TeamsEntry._ID + " = ?", new String[] { String.valueOf(id) });

        if(cursor.getCount() > 0) {
            Team team = cursor.getFirstTeam();
            cursor.close();

            return team;
        }

        cursor.close();

        return null;
    }

    @Override
    public long addTeam(Team item) {
        ContentValues cv = getContentValues(item);


        long result = database.insert(ItemsDbContract.TeamsEntry.TABLE_NAME, null, cv);
        return result;

        }

    @Override
    public Team removeTeam(long id) {
        Team team = getTeam(id);
        database.delete(ItemsDbContract.TeamsEntry.TABLE_NAME, ItemsDbContract.TeamsEntry._ID + " = ?", new String[] { String.valueOf(id) });

        return team;
    }


    private ContentValues getContentValues(Team team) {
        ContentValues cv = new ContentValues();

        cv.put(ItemsDbContract.TeamsEntry._ID, team.getId());
        cv.put(ItemsDbContract.TeamsEntry.COLUMN_TEAM_NAME, team.getTeamName());
        cv.put(ItemsDbContract.TeamsEntry.COLUMN_TEAM_STATUS, team.getTeamStatus());
        cv.put(ItemsDbContract.TeamsEntry.COLUMN_TEAM_ASSIGNEE,team.getAssignee());


        return cv;
    }

    private TeamCursorWrapper queryTeams(String where, String[] whereArg) {
        @SuppressLint("Recycle")
        Cursor cursor = database.query(
                ItemsDbContract.TeamsEntry.TABLE_NAME,
                null,
                where,
                whereArg,
                null,
                null,
                null
        );

        return new TeamCursorWrapper(cursor);
    }
}

