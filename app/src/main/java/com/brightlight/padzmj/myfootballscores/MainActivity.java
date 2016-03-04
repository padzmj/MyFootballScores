package com.brightlight.padzmj.myfootballscores;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.brightlight.padzmj.myfootballscores.Fixtures.Controller.FixturesPlaceHolderFragment;
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.Fixture;
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.Fixtures;
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.MatchResults;
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.TeamData;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final int NUMPAGES = 5;
    public static List<Fixtures> fixturesList = new ArrayList<>();

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

    private CoordinatorLayout mainCoordinatorLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar mainToolBar;
    private TabLayout mainTabLayout;
    private ViewPager mainViewPager;
    private TextView networkStatus;

    //private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkStatus = (TextView) findViewById(R.id.networkStatus);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mainSwipeView);

        loadData();

//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                loadData();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
    }

    public void loadData(){
        if(isConnected()) {
            callAllFixtures("p2");
            callAllFixtures("n2");
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutContainer, FixturesPlaceHolderFragment.newInstance(getApplicationContext())).commit();
        } else {
            setContentView(R.layout.no_network);

//            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                @Override
//                public void onRefresh() {
//                    loadData();
//                    swipeRefreshLayout.setRefreshing(false);
//                }
//            });
            //getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutContainer, FixturesPlaceHolderFragment.newInstance(getApplicationContext())).commit();
        }
    }

    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return ((networkInfo != null) && networkInfo.isConnected());
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
                        //fixturesList.addAll(fixturesList1);
                        fixturesList.addAll(fixturesList1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Fetch", "Error " + e.toString());
                    }

                    @Override
                    public void onNext(Fixture fixture) {
                        String fixtureIDURL, homeTeam, awayTeam, homeGoals, awayGoals, matchDate, matchStatus;

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

                                fixtureIDURL = fixture.getFixtures().get(i).getLinks().getSelf().getHref();
                                homeTeam = fixture.getFixtures().get(i).getHomeTeamName();
                                awayTeam = fixture.getFixtures().get(i).getAwayTeamName();
                                homeGoals = fixture.getFixtures().get(i).getResult().getGoalsHomeTeam();
                                awayGoals = fixture.getFixtures().get(i).getResult().getGoalsAwayTeam();
                                matchDate = fixture.getFixtures().get(i).getDate();
                                matchStatus = fixture.getFixtures().get(i).getStatus();

                                //Get Just Fixture ID
                                int pos1 = fixtureIDURL.lastIndexOf("/");
                                String fixtureID = fixtureIDURL.substring(pos1 + 1, fixtureIDURL.length());
                                Log.i("FixtureID", "FixtureID " + fixtureID);

                                gameFixture.setFixtureID(fixtureID);

                                String homeTeamURL = fixture.getFixtures().get(i).getLinks().getHomeTeam().getHref();
                                String awayTeamURL = fixture.getFixtures().get(i).getLinks().getAwayTeam().getHref();

                                String date = matchDate.substring(0, matchDate.indexOf("T"));
                                String time = matchDate.substring(matchDate.indexOf("T")+1, matchDate.lastIndexOf(":"));


                                Log.i("Fetch", "Fixture " + ti);
                                Log.i("Fetch", matchDate + " " + matchStatus);
                                Log.i("Fetch", homeTeam +  " v " + awayTeam);

                                final String homeTeamID = homeTeamURL.replace(teams, "");
                                final String awayTeamID = awayTeamURL.replace(teams, "");

                                Observable<TeamData> homeTeamDataObservable = fetchFootballData.fetchFootballData().getTeam(homeTeamID);
                                homeTeamDataObservable
                                        .subscribeOn(Schedulers.newThread())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Subscriber<TeamData>() {

                                            TeamData homeTeamDataOB = new TeamData();

                                            @Override
                                            public void onCompleted() {
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Log.i("NEXT TEAM DATA", "Error  " + e.toString());
                                            }

                                            @Override
                                            public void onNext(final TeamData teamData) {

                                                String teamDataIDLink = teamData.getLinks().getSelf().getHref();

                                                int pos1 = teamDataIDLink.lastIndexOf("/");
                                                final String teamDataID = teamDataIDLink.substring(pos1 + 1, teamDataIDLink.length());
                                                Log.i("TeamID", "TeamID " + teamDataID);

                                                teamData.setTeamDataID(homeTeamID);
                                                if(!teamData.getCrestUrl().contains(".png")){
                                                    teamData.setCrestUrl(updateCrestURL(teamData.getCrestUrl()));
                                                }

                                                homeTeamDataOB = teamData;

                                                Realm realm1 = Realm.getInstance(getApplicationContext());
                                                realm1.executeTransaction(new Realm.Transaction() {
                                                    @Override
                                                    public void execute(Realm realm) {
                                                        realm.copyToRealm(teamData);
                                                    }
                                                });
                                            }
                                        });

                                Observable<TeamData> awayTeamDataObservable = fetchFootballData.fetchFootballData().getTeam(awayTeamID);
                                awayTeamDataObservable
                                        .subscribeOn(Schedulers.newThread())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Subscriber<TeamData>() {

                                            TeamData awayTeamDataOB = new TeamData();
                                            @Override
                                            public void onCompleted() {
//                                                Log.i("NEXT TEAM DATA2", "Completed " + awayTeamDataOB.getCrestUrl());
//                                                Log.i("TeamID", "TeamID " + awayTeamDataOB.getTeamDataID());
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Log.i("NEXT TEAM DATA2", "Error  " + e.toString());
                                            }

                                            @Override
                                            public void onNext(final TeamData teamData) {

                                                String teamDataIDLink = teamData.getLinks().getSelf().getHref();

                                                int pos1 = teamDataIDLink.lastIndexOf("/");
                                                final String teamDataID = teamDataIDLink.substring(pos1 + 1, teamDataIDLink.length());
                                                Log.i("TeamID", "TeamID " + teamDataID);

                                                teamData.setTeamDataID(awayTeamID);


                                                if(!teamData.getCrestUrl().contains(".png")){
                                                    teamData.setCrestUrl(updateCrestURL(teamData.getCrestUrl()));
                                                }

                                                awayTeamDataOB = teamData;
                                                Realm realm1 = Realm.getInstance(getApplicationContext());
                                                realm1.executeTransaction(new Realm.Transaction() {
                                                    @Override
                                                    public void execute(Realm realm) {
                                                        realm.copyToRealmOrUpdate(teamData);
                                                    }
                                                });
                                            }
                                        });

                                results.setResultID(fixtureID);
                                results.setGoalsHomeTeam(homeGoals);
                                results.setGoalsAwayTeam(awayGoals);

                                //gameFixture.setHomeTeamData(homeTeamData);
                                //gameFixture.setAwayTeamData(awayTeamData);



                                gameFixture.setHomeTeamName(homeTeam);
                                gameFixture.setAwayTeamName(awayTeam);
                                gameFixture.setResult(results);
                                gameFixture.setStatus(matchStatus);
                                gameFixture.setDate(date);
                                gameFixture.setTime(time);
                                gameFixture.setHomeTeamID(homeTeamID);
                                gameFixture.setAwayTeamID(awayTeamID);

                                if(!fixtureID.isEmpty()) fixturesList1.add(gameFixture);
                                else Log.i("NoID", fixture.getFixtures().get(i).getFixtureID() + " No ID");

                                Realm realm = Realm.getInstance(getApplicationContext());
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        realm.copyToRealmOrUpdate(fixturesList1);
                                    }
                                });


                            }
                        }
                        //updateRealmDatabase(fixturesList1);
                        Log.i("FixtureList1", fixturesList1.size() + " SIZE of FIXTURES!!!!!!!!");
                    }
                });


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
