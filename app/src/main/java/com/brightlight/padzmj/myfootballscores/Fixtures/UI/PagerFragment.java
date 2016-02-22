package com.brightlight.padzmj.myfootballscores.Fixtures.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.brightlight.padzmj.myfootballscores.FetchFootballData;
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.Fixture;
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.Fixtures;
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.MatchResults;
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.TeamData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by PadzMJ on 19/02/2016.
 */
public class PagerFragment extends Fragment{

    private FetchFootballData fetchFootballData;

    private static final String seasons= "http://api.football-data.org/v1/soccerseasons/";
    private static final String teams= "http://api.football-data.org/v1/teams/";

    final String BUNDESLIGA1 = "394";
    final String BUNDESLIGA2 = "395";
    final String LIGUE1 = "396";
    final String LIGUE2 = "397";
    final String PREMIER_LEAGUE = "398";
    final String PRIMERA_DIVISION = "399";
    final String SEGUNDA_DIVISION = "400";
    final String SERIE_A = "401";
    final String PRIMERA_LIGA = "402";
    final String Bundesliga3 = "403";
    final String EREDIVISIE = "404";
    final String CHAMPIONS_LEAGUE = "405";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callAllFixtures("n3");
    }

    public void callAllFixtures(String timeFrame) {
        final List<Fixtures> fixturesList1 = new ArrayList<>();
        fetchFootballData = new FetchFootballData();
        fetchFootballData.getAllFixturesCall(timeFrame)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Fixture>() {
                    @Override
                    public void onCompleted() {
                        //getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayoutContainer, FixturesFragment.newInstance(getApplicationContext(), fixturesList1)).commit();
                        Log.i("Fetch", "Completed Main " + fixturesList1.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Fetch", "Error " + e.toString());
                    }

                    @Override
                    public void onNext(Fixture fixture) {
                        String fixtureID, homeTeam, awayTeam, homeGoals, awayGoals, matchDate, matchStatus;

                        int ti = 0;

                        Log.i("Fetch", "Fixtures Size " + fixture.getFixtures().size());


                        for (int i = 0; i < fixture.getFixtures().size(); i++) {
                            String season = fixture.getFixtures().get(i).getLinks().getSoccerSeason().getHref();

                            String league = season.replace(seasons, "");

                            Log.i("Seasons", league);

                            if (league.equals(BUNDESLIGA2) || league.equals(PRIMERA_LIGA) ||
                                    league.equals(BUNDESLIGA1) || league.equals(LIGUE1) ||
                                    league.equals(SERIE_A) || league.equals(EREDIVISIE) ||
                                    league.equals(PREMIER_LEAGUE) || league.equals(CHAMPIONS_LEAGUE)) {

                                final Fixtures gameFixture = new Fixtures();
                                MatchResults results = new MatchResults();
                                final TeamData homeTeamData = new TeamData();
                                final TeamData awayTeamData = new TeamData();


                                ti++;

                                fixtureID = fixture.getFixtures().get(i).getLinks().getSelf().getHref();
                                homeTeam = fixture.getFixtures().get(i).getHomeTeamName();
                                awayTeam = fixture.getFixtures().get(i).getAwayTeamName();
                                homeGoals = fixture.getFixtures().get(i).getResult().getGoalsHomeTeam();
                                awayGoals = fixture.getFixtures().get(i).getResult().getGoalsAwayTeam();
                                matchDate = fixture.getFixtures().get(i).getDate();
                                matchStatus = fixture.getFixtures().get(i).getStatus();



                                //Get Just Fixture ID
                                int pos1 = fixtureID.lastIndexOf("/");
                                String fixtureID1 = fixtureID.substring(pos1 + 1, fixtureID.length());
//                                Log.i("RetroLeagueSize", ti + " New Size " + fixtureID1);

                                String homeTeamURL = fixture.getFixtures().get(i).getLinks().getHomeTeam().getHref();
                                String awayTeamURL = fixture.getFixtures().get(i).getLinks().getAwayTeam().getHref();


                                Log.i("Fetch", "Fixture " + ti);
                                Log.i("Fetch", matchDate + " " + matchStatus);
                                Log.i("Fetch", homeTeam +  " v " + awayTeam);

                                final String homeTeamID = homeTeamURL.replace(teams, "");
                                String awayTeamID = awayTeamURL.replace(teams, "");

                                Observable<TeamData> homeTeamDataObservable = fetchFootballData.fetchFootballData().getTeam(homeTeamID);

                                homeTeamDataObservable
                                        .delay(100, TimeUnit.MILLISECONDS)
                                        .subscribeOn(Schedulers.newThread())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .cache()
                                        .unsubscribeOn(Schedulers.newThread())
                                        .subscribe(new Subscriber<TeamData>() {

                                            TeamData homeTeamDataOB = new TeamData();
                                            @Override
                                            public void onCompleted() {
                                                gameFixture.setHomeTeamData(homeTeamDataOB);
                                                Log.i("NEXT TEAM DATA", "Complete " + homeTeamDataOB);
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Log.i("NEXT TEAM DATA", "Error  " + e.toString());
                                            }

                                            @Override
                                            public void onNext(TeamData teamData) {

                                                if(!teamData.getCrestUrl().contains(".png")){
                                                    teamData.setCrestUrl(updateCrestURL(teamData.getCrestUrl()));
                                                }

                                                homeTeamDataOB = teamData;
                                                Log.i("NEXT TEAM DATA", "Home Team  " + teamData.getCrestUrl());
                                            }
                                        }).unsubscribe();

                                Observable<TeamData> awayTeamDataObservable = fetchFootballData.fetchFootballData().getTeam(awayTeamID);
                                awayTeamDataObservable
                                        .delay(100, TimeUnit.MILLISECONDS)
                                        .subscribeOn(Schedulers.newThread())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .cache()
                                        .unsubscribeOn(Schedulers.newThread())
                                        .subscribe(new Subscriber<TeamData>() {

                                            TeamData awayTeamDataOB = new TeamData();
                                            @Override
                                            public void onCompleted() {
                                                gameFixture.setAwayTeamData(awayTeamDataOB);
                                                Log.i("NEXT TEAM DATA2", "Completed " + awayTeamDataOB.getCrestUrl());
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Log.i("NEXT TEAM DATA2", "Error  " + e.toString());
                                            }

                                            @Override
                                            public void onNext(TeamData teamData) {

                                                if(!teamData.getCrestUrl().contains(".png")){
                                                    teamData.setCrestUrl(updateCrestURL(teamData.getCrestUrl()));
                                                }

                                                awayTeamDataOB = teamData;

                                                Log.i("NEXT TEAM DATA2", "Away Team  " + teamData.getCrestUrl());
                                            }
                                        }).unsubscribe();

                                results.setGoalsHomeTeam(homeGoals);
                                results.setGoalsAwayTeam(awayGoals);

                                gameFixture.setHomeTeamName(homeTeam);
                                gameFixture.setAwayTeamName(awayTeam);
                                gameFixture.setResult(results);
                                gameFixture.setHomeTeamID(homeTeamID);
                                gameFixture.setAwayTeamID(awayTeamID);


                                Log.i("NEXT TEAM DATA2", "HOME Team CREST " + gameFixture.getHomeTeamData().getCrestUrl());

                                //gameFixture.setHomeTeamData(homeTeamData);
                                //gameFixture.setAwayTeamData(awayTeamData);


                                fixturesList1.add(gameFixture);
                            }
                        }
                        Log.i("FixtureList1", fixturesList1.size() + " SIZE of FIXTURES!!!!!!!!");
                    }
                });
        //Log.i("FixtureList1B", fixturesList.size() + " SIZE");
    }

    public String updateCrestURL(String url){

        //I noticed on google there is the PNG file and SVG file
        //Only difference is the URL which I'm attempting to extract here

        String _de = "de/";
        String _en = "en/";
        String commons = "commons/";
        String thumb = "thumb/";
        final String baseURL = "http://upload.wikimedia.org/wikipedia/";
        final String baseURL2 = "https://upload.wikimedia.org/wikipedia/";
        String urlReturn = "";
        String imageSize = "100px-";
        String newURL="";

        if(url.contains("https://")){
            newURL = url.replace(baseURL2, "");
        }else newURL = url.replace(baseURL, "");

        //looks for the last / to concat
        int pos = newURL.lastIndexOf("/");
        String lastPartURL = newURL.substring(pos+1, newURL.length());


        if(newURL.contains(_de)){
            String newURL1 = newURL.replaceFirst(_de, baseURL+_de+thumb);
            urlReturn = newURL1+"/"+imageSize+lastPartURL+".png";
        }

        if(newURL.contains(_en)){
            String newURL1 = newURL.replaceFirst(_en, baseURL+_en+thumb);
            urlReturn = newURL1+"/"+imageSize+lastPartURL+".png";
        }

        //Paderborn contains https
        if(newURL.contains(commons)){
            if(newURL.contains(baseURL)){
                String newURL2 = newURL.replace(commons, commons+thumb);
                urlReturn = newURL2+"/"+imageSize+lastPartURL+".png";
            }else {
                String newURL2 = newURL.replace(commons, baseURL+commons+thumb);
                urlReturn = newURL2+"/"+imageSize+lastPartURL+".png";
            }
        }

        return urlReturn;
    }
}
