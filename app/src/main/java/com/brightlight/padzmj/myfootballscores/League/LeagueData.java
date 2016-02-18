package com.brightlight.padzmj.myfootballscores.League;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by PadzMJ on 08/02/2016.
 */
public class LeagueData {
    private JSONObject _links;
    private String leagueCaption, matchday;
    private List<LeagueStandingData> standing;

    public JSONObject get_links() {
        return _links;
    }

    public void set_links(JSONObject _links) {
        this._links = _links;
    }

    public String getLeagueCaption() {
        return leagueCaption;
    }

    public void setLeagueCaption(String leagueCaption) {
        this.leagueCaption = leagueCaption;
    }

    public String getMatchday() {
        return matchday;
    }

    public void setMatchday(String matchday) {
        this.matchday = matchday;
    }

    public List<LeagueStandingData> getStanding() {
        return standing;
    }

    public void setStanding(List<LeagueStandingData> standing) {
        this.standing = standing;
    }
}
