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

import com.brightlight.padzmj.myfootballscores.Fixtures.Controller.FixturesAdapter;
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.Fixtures;
import com.brightlight.padzmj.myfootballscores.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by PadzMJ on 08/02/2016.
 */
public class FixturesFragment extends Fragment {

    private static List<Fixtures> fixturesList = new ArrayList<>();
    private static Context context;
    public static RecyclerView recyclerView;
    private Realm mainRealm;
    private RealmResults<Fixtures> realmResults;
    private static String matchDate;
    private String[] fragmentdate = new String[1];
    private FixturesAdapter fixturesAdapter;


    public FixturesFragment(){

    }

    public static FixturesFragment newInstance(Context context, String matchDate){
        FixturesFragment.matchDate = matchDate;
        FixturesFragment.context = context;
        return new FixturesFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        realmResults.addChangeListener(changeListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        realmResults.removeChangeListener(changeListener);
    }

    private RealmChangeListener changeListener = new RealmChangeListener() {
        @Override
        public void onChange() {
            if(!realmResults.isEmpty()) {
                fixturesAdapter.updateFixturesList(realmResults);
            }
        }
    };

    public void setFragmentDate(String date)
    {
        fragmentdate[0] = date;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainRealm = Realm.getDefaultInstance();
        getFromDatabaseByDate();
        fixturesAdapter = new FixturesAdapter(context, realmResults);
        Log.i("RealmResults", realmResults.size() + " Realm Size");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView;

        if(!realmResults.isEmpty()) {
            rootView = inflater.inflate(R.layout.fixtures_list_layout, container, false);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.fixtureListRecyclerView);
            //fixturesAdapter = new FixturesAdapter(context, realmResults);
            recyclerView.setAdapter(fixturesAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        else rootView = inflater.inflate(R.layout.no_fixtures, container, false);

        return rootView;
    }

    private void getFromDatabaseByDate() {
        mainRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmResults = realm.where(Fixtures.class).contains("date", fragmentdate[0]).findAll();
                Log.i("RealmResults", realmResults.size() + " Realm Size");
            }
        });

    }
}
