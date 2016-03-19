package com.brightlight.padzmj.myfootballscores.Fixtures.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.brightlight.padzmj.myfootballscores.Fixtures.Model.Fixtures;
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.MatchResults;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by PadzMJ on 08/03/2016.
 */
public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {


    Context context = null;
    Realm mainRealm;
    List<Fixtures> list = new ArrayList<>();

    public WidgetDataProvider (Context context, Intent intent){
        this.context = context;
    }

    private void initData(){

        Calendar calendar = Calendar.getInstance();
        final Date date = calendar.getTime();

        final SimpleDateFormat mformat2 = new SimpleDateFormat("yyyy-MM-dd");
        mainRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Fixtures> realmResults = realm.where(Fixtures.class).contains("date", mformat2.format(date)).findAll();
                for(Fixtures results : realmResults){
                    Fixtures fixture = new Fixtures();
                    MatchResults matchResults = new MatchResults();
                    matchResults.setGoalsHomeTeam(results.getResult().getGoalsHomeTeam());
                    matchResults.setGoalsAwayTeam(results.getResult().getGoalsAwayTeam());
                    fixture.setHomeTeamName(results.getHomeTeamName());
                    fixture.setAwayTeamName(results.getAwayTeamName());
                    fixture.setResult(matchResults);
                    list.add(fixture);
                }
                Log.i("WidgetList", list.size() + " Here List Size");
            }
        });

    }
    @Override
    public void onCreate() {
        mainRealm = Realm.getInstance(context);
        initData();
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return list.size();
    }



    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews view = new RemoteViews(context.getPackageName(), android.R.layout.simple_list_item_1);

        String homeTeam, homeGoals, awayTeam, awayGoals;
        homeTeam = list.get(i).getHomeTeamName();
        homeGoals = list.get(i).getResult().getGoalsHomeTeam();
        awayTeam = list.get(i).getAwayTeamName();
        awayGoals = list.get(i).getResult().getGoalsAwayTeam();

        if(homeGoals == null) homeGoals = "";
        if(awayGoals == null) awayGoals = "";

        view.setTextViewText(android.R.id.text1, homeTeam + " " + homeGoals + " - " + awayGoals + " " + awayTeam);
        view.setTextColor(android.R.id.text1, Color.BLACK);
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
