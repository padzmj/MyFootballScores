package com.brightlight.padzmj.myfootballscores.RealmDatabase;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by PadzMJ on 15/02/2016.
 */
public class DBFixtures extends RealmObject {

    @PrimaryKey
    private String fixtureID;

    private String  date, status;
    private String  homeTeamCrest, homeTeamCode, homeTeamName, homeShortName, homeSquadMarketValue, goalsHomeTeam;
    private String  awayTeamCrest, awayTeamCode, awayTeamName, awayShortName, awaySquadMarketValue, goalsAwayTeam;

    public String getFixtureID() {
        return fixtureID;
    }

    public void setFixtureID(String fixtureID) {
        this.fixtureID = fixtureID;
    }

    public String getHomeTeamCrest() {
        return homeTeamCrest;
    }

    public void setHomeTeamCrest(String homeTeamCrest) {
        this.homeTeamCrest = homeTeamCrest;
    }

    public String getAwayTeamCrest() {
        return awayTeamCrest;
    }

    public void setAwayTeamCrest(String awayTeamCrest) {
        this.awayTeamCrest = awayTeamCrest;
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

    public String getHomeTeamCode() {
        return homeTeamCode;
    }

    public void setHomeTeamCode(String homeTeamCode) {
        this.homeTeamCode = homeTeamCode;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getHomeShortName() {
        return homeShortName;
    }

    public void setHomeShortName(String homeShortName) {
        this.homeShortName = homeShortName;
    }

    public String getHomeSquadMarketValue() {
        return homeSquadMarketValue;
    }

    public void setHomeSquadMarketValue(String homeSquadMarketValue) {
        this.homeSquadMarketValue = homeSquadMarketValue;
    }

    public String getGoalsHomeTeam() {
        return goalsHomeTeam;
    }

    public void setGoalsHomeTeam(String goalsHomeTeam) {
        this.goalsHomeTeam = goalsHomeTeam;
    }

    public String getAwayTeamCode() {
        return awayTeamCode;
    }

    public void setAwayTeamCode(String awayTeamCode) {
        this.awayTeamCode = awayTeamCode;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public String getAwayShortName() {
        return awayShortName;
    }

    public void setAwayShortName(String awayShortName) {
        this.awayShortName = awayShortName;
    }

    public String getAwaySquadMarketValue() {
        return awaySquadMarketValue;
    }

    public void setAwaySquadMarketValue(String awaySquadMarketValue) {
        this.awaySquadMarketValue = awaySquadMarketValue;
    }

    public String getGoalsAwayTeam() {
        return goalsAwayTeam;
    }

    public void setGoalsAwayTeam(String goalsAwayTeam) {
        this.goalsAwayTeam = goalsAwayTeam;
    }
}
