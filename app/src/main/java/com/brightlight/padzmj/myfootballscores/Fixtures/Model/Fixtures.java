package com.brightlight.padzmj.myfootballscores.Fixtures.Model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by PadzMJ on 08/02/2016.
 */
public class Fixtures extends RealmObject{

    @PrimaryKey
    @Required
    private String fixtureID;

    private String date, status, homeTeamName, awayTeamName, homeTeamID, awayTeamID, time;

    @Ignore
    @SerializedName("_links")
    private Links links;

    @SerializedName("result")
    private MatchResults result;

    private TeamData homeTeamData;

    private TeamData awayTeamData;

    public String getFixtureID() {
        return fixtureID;
    }

    public void setFixtureID(String fixtureID) {
        this.fixtureID = fixtureID;
    }

    public MatchResults getResult() {
        return result;
    }

    public void setResult(MatchResults result) {
        this.result = result;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public TeamData getHomeTeamData() {
        return homeTeamData;
    }

    public void setHomeTeamData(TeamData homeTeamData) {
        this.homeTeamData = homeTeamData;
    }

    public TeamData getAwayTeamData() {
        return awayTeamData;
    }

    public void setAwayTeamData(TeamData awayTeamData) {
        this.awayTeamData = awayTeamData;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public class Links{


        @SerializedName("self")
        private Self self;

        @SerializedName("soccerseason")
        private SoccerSeason soccerseason;

        @SerializedName("homeTeam")
        private HomeTeam homeTeam;

        @SerializedName("awayTeam")
        private AwayTeam awayTeam;

        public Self getSelf() {
            return self;
        }

        public void setSelf(Self self) {
            this.self = self;
        }

        public SoccerSeason getSoccerSeason() {
            return soccerseason;
        }

        public void setSoccerSeason(SoccerSeason soccerSeason) {
            this.soccerseason = soccerSeason;
        }

        public HomeTeam getHomeTeam() {
            return homeTeam;
        }

        public void setHomeTeam(HomeTeam homeTeam) {
            this.homeTeam = homeTeam;
        }

        public AwayTeam getAwayTeam() {
            return awayTeam;
        }

        public void setAwayTeam(AwayTeam awayTeam) {
            this.awayTeam = awayTeam;
        }

        public class Self{
            private String href;

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }
        }

        public class SoccerSeason{
            private String href;

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }
        }

        public class HomeTeam{
            private String href;

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }
        }

        public class AwayTeam{
            private String href;

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }
        }
    }

    public String getHomeTeamID() {
        return homeTeamID;
    }

    public void setHomeTeamID(String homeTeamID) {
        this.homeTeamID = homeTeamID;
    }

    public String getAwayTeamID() {
        return awayTeamID;
    }

    public void setAwayTeamID(String awayTeamID) {
        this.awayTeamID = awayTeamID;
    }

}
