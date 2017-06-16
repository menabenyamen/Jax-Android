package com.criminal.menabenyamen.taskr.repository;


import com.criminal.menabenyamen.taskr.model.Team;

import java.util.List;

/**
 * Created by menabenyamen on 2017-06-08.
 */

public interface TeamRepository {

    List<Team> getTeams();
    Team getTeam(long id);
    long addTeam(Team team);
    Team removeTeam(long id);
    List<String> getAllassigneeForTeam(final String teamName);
}
