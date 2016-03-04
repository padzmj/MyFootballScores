package com.brightlight.padzmj.myfootballscores.Fixtures.Model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by PadzMJ on 08/02/2016.
 */
public class TeamData extends RealmObject{

    @PrimaryKey
    private String teamDataID;

    @Ignore
    @SerializedName("_links")
    private Links links;

    private String name, code, shortName, squadMarketValue, crestUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSquadMarketValue() {
        return squadMarketValue;
    }

    public void setSquadMarketValue(String squadMarketValue) {
        this.squadMarketValue = squadMarketValue;
    }

    public String getCrestUrl() {
        return crestUrl;
    }

    public void setCrestUrl(String crestUrl) {
        this.crestUrl = crestUrl;
    }

    public String getTeamDataID() {
        return teamDataID;
    }

    public void setTeamDataID(String teamDataID) {
        this.teamDataID = teamDataID;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public class Links{

        @SerializedName("self")
        private Self self;

        @SerializedName("fixtures")
        private Fixtures fixtures;

        @SerializedName("players")
        private Players players;

        public Self getSelf() {
            return self;
        }

        public void setSelf(Self self) {
            this.self = self;
        }

        public Fixtures getFixtures() {
            return fixtures;
        }

        public void setFixtures(Fixtures fixtures) {
            this.fixtures = fixtures;
        }

        public Players getPlayers() {
            return players;
        }

        public void setPlayers(Players players) {
            this.players = players;
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

        public class Fixtures{
            private String href;

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }
        }

        public class Players{
            private String href;

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }
        }
    }
}
