package com.brightlight.padzmj.myfootballscores.League;

import org.json.JSONObject;

/**
 * Created by PadzMJ on 08/02/2016.
 */
public class LeagueStandingData {
    private JSONObject _links;
    private String position, teamName, playedGames, points, goals; //And More

    public JSONObject get_links() {
        return _links;
    }

    public void set_links(JSONObject _links) {
        this._links = _links;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(String playedGames) {
        this.playedGames = playedGames;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }
}
