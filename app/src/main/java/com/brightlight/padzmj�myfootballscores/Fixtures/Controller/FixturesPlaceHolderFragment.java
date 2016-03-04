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
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.Fixtures;
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.TeamData;
import com.brightlight.padzmj.myfootballscores.Fixtures.UI.FixturesFragment;
import com.brightlight.padzmj.myfootballscores.MainActivity;
import com.brightlight.padzmj.myfootballscores.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
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
    private SwipeRefreshLayout swipeRefreshLayout ;
    private Toolbar mainToolBar;
    private TabLayout mainTabLayout;
    private ViewPager mainViewPager;

    private static Context context;

    private Realm mainRealm;

    public FixturesFragment[] fixturesFragments = new FixturesFragment[5];

    public static FixturesPlaceHolderFragment newInstance(Context context){
        FixturesPlaceHolderFragment.context = context;
        return new FixturesPlaceHolderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //updateTeamData(realmResults);
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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViewPager(mainViewPager);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return rootView;
    }

    private void initViewPager(ViewPager viewPager){
        PagerAdapter pagerAdapter = new PagerAdapter((getActivity()).getSupportFragmentManager());

        for(int i = 0; i<NUMPAGES; i++){

            Date date = new Date(System.currentTimeMillis()+((i-2)*86400000));
            SimpleDateFormat mformat = new SimpleDateFormat("EEEE", Locale.getDefault());
            SimpleDateFormat mformat2 = new SimpleDateFormat("yyyy-MM-dd");

            fixturesFragments[i] = FixturesFragment.newInstance(context, mformat2.format(date));
            fixturesFragments[i].setFragmentDate(mformat2.format(date));
            pagerAdapter.addFragment(fixturesFragments[i], mformat.format(date));
        }

        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(3);
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

    private void updateTeamData(final List<Fixtures> listOfFixtures){
                for (int i = 0; i < listOfFixtures.size(); i++) {
                    String homeTeamID = listOfFixtures.get(i).getHomeTeamID();
                    String awayTeamID = listOfFixtures.get(i).getAwayTeamID();
                    //updateHomeTeam(homeTeamID);
                    //updateAwayTeam(awayTeamID);
                }

    }

    private void updateHomeTeam(final String homeTeamID){
        Observable<TeamData> homeTeamDataObservable = fetchFootballData.fetchFootballData().getTeam(homeTeamID);
        homeTeamDataObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TeamData>() {

                    TeamData homeTeamDataOB = new TeamData();

                    @Override
                    public void onCompleted() {
                        Realm realm1 = Realm.getInstance(context);
                        realm1.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealm(homeTeamDataOB);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("NEXT TEAM DATA", "Error  " + e.toString());
                    }

                    @Override
                    public void onNext(TeamData teamData) {

                        String teamDataIDLink = teamData.getLinks().getSelf().getHref();

                        int pos1 = teamDataIDLink.lastIndexOf("/");
                        final String teamDataID = teamDataIDLink.substring(pos1 + 1, teamDataIDLink.length());
                        Log.i("TeamID", "TeamID " + teamDataID);

                        teamData.setTeamDataID(homeTeamID);
                        if(!teamData.getCrestUrl().contains(".png")){
                            teamData.setCrestUrl(updateCrestURL(teamData.getCrestUrl()));
                        }

                        homeTeamDataOB = teamData;
                    }
                });

    }

    private void updateAwayTeam(final String awayTeamID){
        Observable<TeamData> awayTeamDataObservable = fetchFootballData.fetchFootballData().getTeam(awayTeamID);
        awayTeamDataObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TeamData>() {

                    TeamData awayTeamDataOB = new TeamData();
                    @Override
                    public void onCompleted() {
                        Realm realm1 = Realm.getInstance(context);
                        realm1.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(awayTeamDataOB);
                            }
                        });
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
