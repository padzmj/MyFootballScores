package com.brightlight.padzmj.myfootballscores.Fixtures.Controller;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brightlight.padzmj.myfootballscores.FetchFootballData;
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.Fixture;
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.Fixtures;
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.MatchResults;
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.TeamData;
import com.brightlight.padzmj.myfootballscores.Fixtures.UI.FixturesFragment;
import com.brightlight.padzmj.myfootballscores.MainActivity;
import com.brightlight.padzmj.myfootballscores.R;
import com.brightlight.padzmj.myfootballscores.RealmDatabase.DBFixtures;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by PadzMJ on 17/02/2016.
 */
public class FixturesPlaceHolderFragment extends Fragment {


    private static final int NUMPAGES = 5;
    private TextView networkStatus;
    private static List<Fixtures> fixturesList = Collections.emptyList();
    private static List<Fixtures> datedFixtures = Collections.emptyList();

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
    private Toolbar mainToolBar;
    private TabLayout mainTabLayout;
    private ViewPager mainViewPager;
    private SwipeRefreshLayout swipeRefreshLayout;

    View rootView;

    private static Context context;

    public static FixturesPlaceHolderFragment newInstance(Context context, List<Fixtures> fixturesList){
        FixturesPlaceHolderFragment.context = context;
        FixturesPlaceHolderFragment.fixturesList = fixturesList;
        return new FixturesPlaceHolderFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fixtures_placeholder_fragment, container, false);

        mainCoordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.mainCoordinatorLayout);
        mainTabLayout = (TabLayout) rootView.findViewById(R.id.mainTabLayout);
        mainToolBar = (Toolbar) rootView.findViewById(R.id.mainToolBarLayout);
        mainViewPager = (ViewPager) rootView.findViewById(R.id.mainContainerViewPager);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);

        ((MainActivity)getActivity()).setSupportActionBar(mainToolBar);

        initViewPager(mainViewPager);

        for(int i = 0; i<fixturesList.size(); i++){
            Log.i("Date", fixturesList.get(i).getDate() + " Time");
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViewPager(mainViewPager);
            }
        });

        return rootView;
    }

    private void initViewPager(ViewPager viewPager){
        PagerAdapter pagerAdapter = new PagerAdapter((getActivity()).getSupportFragmentManager());

        //updateRealmDatabase(fixturesList);

        for(int i = 0; i<NUMPAGES; i++){

            Date date = new Date(System.currentTimeMillis()+((i-2)*86400000));
            SimpleDateFormat mformat = new SimpleDateFormat("EEEE", Locale.getDefault());

            //sortFixturesByDate(fixturesList, date);

            //SimpleDateFormat mformat2 = new SimpleDateFormat("yyyy-MM-dd");
            //pagerAdapter.addFragment(FixturesPlaceHolderFragment.newInstance(this), mformat.format(date));

            pagerAdapter.addFragment(FixturesFragment.newInstance(FixturesPlaceHolderFragment.context, fixturesList), mformat.format(date));

//            for(Fixtures fixtures1: fixturesList){
//                String date1 = fixtures1.getDate().substring(0, fixtures1.getDate().lastIndexOf("T"));
//
//                Log.i("DateFormat", date1 + " " + mformat2.format(date));
//
//                    if(fixtures1.getDate()==mformat2.format(date)){
//                    }
//
//            }
            //Toast.makeText(this, fixturesList.size() + " Size", Toast.LENGTH_LONG).show();
        }
        swipeRefreshLayout.setRefreshing(false);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(2);
        mainTabLayout.setupWithViewPager(mainViewPager);
    }

    private List<Fixtures> sortFixturesByDate(List<Fixtures> fList, Date date) {

        SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
        List<Fixtures> myFix = new ArrayList<>();

        for(int i = 0; i<fList.size(); i++){
            String fixDate = fList.get(i).getDate().substring(0, fList.get(i).getDate().lastIndexOf("T"));
            if(fixDate.equals(mformat.format(date))){
                Log.i("FixtureDate", fList.get(i).getHomeTeamName() + " " + mformat.format(date));
                myFix.add(fList.get(i));
            }
        }

        return myFix;

            //pagerAdapter.addFragment(FixturesPlaceHolderFragment.newInstance(this), mformat.format(date));

//            for(Fixtures fixtures1: fixturesList){
//                String date1 = fixtures1.getDate().substring(0, fixtures1.getDate().lastIndexOf("T"));
//
//                Log.i("DateFormat", date1 + " " + mformat2.format(date));
//
//                    if(fixtures1.getDate()==mformat2.format(date)){
//                    }
//
//            }
            //Toast.makeText(this, fixturesList.size() + " Size", Toast.LENGTH_LONG).show();


    }

    private void updateRealmDatabase(final List<Fixtures> fixturesList){
        Realm realm = Realm.getInstance(context);
        List<Fixtures> dbFixtureList = new ArrayList<>();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                String fixtureID, date, homeCrestURL, homeTeamName, homeTeamGoals;
                String awayCrestURL, awayTeamName, awayTeamGoals;

                for(Fixtures fixtures : fixturesList){
                    RealmResults<DBFixtures> realmResults = realm.where(DBFixtures.class).equalTo("fixtureID", fixtures.getFixtureID()).findAll();

                    if(realmResults.size()==0){
                        DBFixtures dbFixtures = realm.createObject(DBFixtures.class);

                        fixtureID = fixtures.getFixtureID();
                        homeTeamName = fixtures.getHomeTeamName();
                        homeCrestURL = fixtures.getHomeTeamData().getCrestUrl();
                        homeTeamGoals = fixtures.getResult().getGoalsHomeTeam();
                        awayTeamName = fixtures.getAwayTeamName();
                        awayCrestURL = fixtures.getAwayTeamData().getCrestUrl();
                        awayTeamGoals = fixtures.getResult().getGoalsAwayTeam();

                        dbFixtures.setFixtureID(fixtureID.toString());
                        dbFixtures.setHomeTeamName(homeTeamName.toString());
                        dbFixtures.setHomeTeamCrest(homeCrestURL.toString());
                        dbFixtures.setGoalsHomeTeam(homeTeamGoals.toString());
                        dbFixtures.setAwayTeamName(awayTeamName.toString());
                        dbFixtures.setAwayTeamCrest(awayCrestURL.toString());
                        dbFixtures.setGoalsAwayTeam(awayTeamGoals.toString());
                    }
                }
            }
        });
    }


    public void callAllFixtures() {
        final List<Fixtures> fixturesList1 = new ArrayList<>();
        fetchFootballData = new FetchFootballData();
        fetchFootballData.getAllFixturesCall("p7")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Fixture>() {
                    @Override
                    public void onCompleted() {
                        //((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayoutContainer, FixturesFragment.newInstance(context, fixturesList1)).commit();
                        Log.i("Fetch", "Completed Main " + fixturesList1.size());
                        fixturesList = fixturesList1;
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
