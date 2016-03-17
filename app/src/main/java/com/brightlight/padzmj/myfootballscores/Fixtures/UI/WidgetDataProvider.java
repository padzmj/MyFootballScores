package com.brightlight.padzmj.myfootballscores.Fixtures.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.brightlight.padzmj.myfootballscores.Fixtures.Model.Fixtures;

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
    RealmResults realmResults;
    List<Fixtures> list = new ArrayList<>();

    public WidgetDataProvider (Context context, Intent intent){
        this.context = context;
    }

    private void initData(){

        Calendar calendar = Calendar.getInstance();
        final Date date = calendar.getTime();

        final SimpleDateFormat mformat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        mainRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmResults = realm.where(Fixtures.class).contains("date", mformat2.format(date)).findAll();
                //list.addAll(realmResults);
                //collections.add(realmResults);
                list = realmResults;
                Log.i("RealmResults", realmResults.size() + " Here List Size -");
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
        view.setTextViewText(android.R.id.text1, list.get(i).getHomeTeamName());
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
