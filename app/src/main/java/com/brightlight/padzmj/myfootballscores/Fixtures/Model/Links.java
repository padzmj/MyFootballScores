package com.brightlight.padzmj.myfootballscores.Fixtures.Model;

/**
 * Created by PadzMJ on 09/02/2016.
 */
public class Links {

    //@SerializedName("self")
    private Self self;

    //@SerializedName("soccerseason")
    private SoccerSeason soccerseason;

    //@SerializedName("homeTeam")
    private HomeTeam homeTeam;

    //@SerializedName("awayTeam")
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
