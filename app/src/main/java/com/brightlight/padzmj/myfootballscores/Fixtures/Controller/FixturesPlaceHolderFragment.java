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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brightlight.padzmj.myfootballscores.FetchFootballData;
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.Fixtures;
import com.brightlight.padzmj.myfootballscores.Fixtures.UI.FixturesFragment;
import com.brightlight.padzmj.myfootballscores.MainActivity;
import com.brightlight.padzmj.myfootballscores.R;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;

/**
 * Created by PadzMJ on 17/02/2016.
 */
public class FixturesPlaceHolderFragment extends Fragment {

    public static final String ACTION_DATA_UPDATED = "com.brightlight.padzmj.myfootballscores.app.ACTION_DATA_UPDATED";

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
        viewPager.setCurrentItem(2);
        mainTabLayout.setupWithViewPager(mainViewPager);
    }
}
