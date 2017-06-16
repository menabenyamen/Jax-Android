package com.criminal.menabenyamen.taskr.model;

/**
 * Created by menabenyamen on 2017-05-26.
 */

public final class Team {

    private long id;
    private String teamName;
    private String teamStatus;
    private String assignee;


    public Team(String teamName, String teamStatus){

        this.teamName = teamName;
        this.teamStatus = teamStatus;


    }

    public Team(long id, String teamName, String teamStatus, String assignee) {
        this.id = id;
        this.teamName = teamName;
        this.teamStatus = teamStatus;
        this.assignee = assignee;
    }


    public long getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamStatus() {
        return teamStatus;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTeamStatus(String teamStatus) {
        this.teamStatus = teamStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        if (id != team.id) return false;
        if (teamName != null ? !teamName.equals(team.teamName) : team.teamName != null)
            return false;
        if (teamStatus != null ? !teamStatus.equals(team.teamStatus) : team.teamStatus != null)
            return false;
        return assignee != null ? assignee.equals(team.assignee) : team.assignee == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (teamName != null ? teamName.hashCode() : 0);
        result = 31 * result + (teamStatus != null ? teamStatus.hashCode() : 0);
        result = 31 * result + (assignee != null ? assignee.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                ", teamStatus='" + teamStatus + '\'' +
                ", assignee='" + assignee + '\'' +
                '}';
    }
}
