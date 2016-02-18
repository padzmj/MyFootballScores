package com.brightlight.padzmj.myfootballscores.Fixtures.UI;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brightlight.padzmj.myfootballscores.FetchFootballData;
import com.brightlight.padzmj.myfootballscores.Fixtures.Controller.FixturesAdapter;
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.Fixtures;
import com.brightlight.padzmj.myfootballscores.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PadzMJ on 08/02/2016.
 */
public class FixturesFragment extends Fragment {

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
    public static List<Fixtures> fixturesList = new ArrayList<>();
    private static Context context;
    private RecyclerView recyclerView;

    public static FixturesFragment newInstance(Context context, List<Fixtures> fixturesList){
        FixturesFragment.fixturesList = fixturesList;
        FixturesFragment.context = context;
        return new FixturesFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fixtures_list_layout, container, false);

        //EventBus.getDefault().register(this);

        //fetchData();

        //FixturesFragment.fixturesList = FetchFootballData.fixturesList;
        Log.i("Fetch", "Create View FIX FETCH " + FixturesFragment.fixturesList);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.fixtureListRecyclerView);
        FixturesAdapter fixturesAdapter = new FixturesAdapter(context, FetchFootballData.fixturesList);
        recyclerView.setAdapter(fixturesAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

    private void fetchData() {
        //FetchFootballData fetchFootballData = new FetchFootballData();
        //fetchFootballData.callAllFixtures(context);
    }

//    @Subscribe
//    public void onEvent(FixtureListEvent fixtureListEvent){
//        //fixturesList = fixtureListEvent.fixturesList;
//        //Log.i("EVENT", fixturesList.size()+" EVENT LIST");
////    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        fetchData();
//        Log.i("Fetch", "Start FIX FETCH " + fixturesList);
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//    }
}
