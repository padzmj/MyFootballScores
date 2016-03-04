package com.brightlight.padzmj.myfootballscores.Fixtures.Controller;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brightlight.padzmj.myfootballscores.Fixtures.Model.Fixtures;
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.TeamData;
import com.brightlight.padzmj.myfootballscores.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by PadzMJ on 09/02/2016.
 */
public class FixturesAdapter extends RecyclerView.Adapter<FixturesViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<Fixtures> fixturesList = new ArrayList<>();
    private TeamData homeRealmObject;
    private TeamData awayRealmObject;
    private Realm mainRealm;

    public FixturesAdapter(Context context, List<Fixtures> fixturesList){
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.fixturesList = fixturesList;
        mainRealm = Realm.getInstance(context);
        Log.i("CHECK", "Fixtures Adapter");

    }


    @Override
    public FixturesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.fixture_item_layout, parent, false);
        Log.i("CHECK", "createViewHoler");
        return new FixturesViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(final FixturesViewHolder holder, int position) {
        final String homeTeamName =  fixturesList.get(position).getHomeTeamName();
        final String awayTeamName = fixturesList.get(position).getAwayTeamName();
        final String homeTeamID =  fixturesList.get(position).getHomeTeamID();
        final String awayTeamID =  fixturesList.get(position).getAwayTeamID();
        final String homeTeamGoals = fixturesList.get(position).getResult().getGoalsHomeTeam();
        final String awayTeamGoals = fixturesList.get(position).getResult().getGoalsAwayTeam();
        final String matchDate = fixturesList.get(position).getDate();
        final String matchTime = fixturesList.get(position).getTime();
        String matchStatus = fixturesList.get(position).getStatus();
        String homeCrest = null, awayCrest = null;

        Log.i("CHECK", "Bind View Holder");

        mainRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                homeRealmObject = realm.where(TeamData.class).equalTo("teamDataID", homeTeamID).findFirst();
                awayRealmObject = realm.where(TeamData.class).equalTo("teamDataID", awayTeamID).findFirst();
            }
        });

        if (homeRealmObject != null && awayRealmObject != null) {
            homeCrest = (homeRealmObject.getCrestUrl()==null) ? "" : homeRealmObject.getCrestUrl();
            awayCrest = (awayRealmObject.getCrestUrl()==null) ? "" : awayRealmObject.getCrestUrl();
        }

        //final String homeCrest = fixturesList.get(position).getHomeTeamData().getCrestUrl();
        //final String awayCrest =fixturesList.get(position).getAwayTeamData().getCrestUrl();

        //String homeCrestURL = homeCrest;
        //String awayCrestURL = awayCrest;

        //Check image is already SVG
        //if(!homeCrestURL.contains(".png")){
        //    homeCrestURL = updateCrestURL(homeCrest);
        //}
        //if(!awayCrestURL.contains(".png")){
         //   awayCrestURL = updateCrestURL(awayCrest);
        //}

//        Realm realm = Realm.getInstance(context);
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                TeamData homeTeamData = realm.where(TeamData.class).equalTo("teamDataID", homeTeamID).findFirst();
//                TeamData awayTeamData = realm.where(TeamData.class).equalTo("teamDataID", awayTeamID).findFirst();
//            }
//        });

        holder.homeTeamName.setText(homeTeamName);
        holder.awayTeamName.setText(awayTeamName);
        holder.goalsHomeTeam.setText(homeTeamGoals);
        holder.goalsAwayTeam.setText(awayTeamGoals);
        holder.matchDate.setText(matchTime);


        if(matchStatus!=null){
            switch (matchStatus){
                case ("TIMED"):
                    holder.matchDate.setVisibility(View.VISIBLE);
                    holder.matchError.setVisibility(View.GONE);
                    holder.matchStatus.setVisibility(View.GONE);
                    break;
                case ("FINISHED"):
                    matchStatus = "FT";
                    holder.matchDate.setVisibility(View.VISIBLE);
                    holder.matchError.setVisibility(View.GONE);
                    holder.matchStatus.setVisibility(View.VISIBLE);
                    break;
                case ("POSTPONED"):
                    holder.matchDate.setVisibility(View.GONE);
                    holder.matchError.setVisibility(View.VISIBLE);
                    holder.matchStatus.setVisibility(View.GONE);
                    break;
                default:
                    matchStatus = "-";

            }
        }


        holder.matchStatus.setText(matchStatus);

        //Test just crest later!!!!
        if(homeCrest != null||awayCrest!=null){
            Glide.with(context).load(homeCrest).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.no_crest).into(holder.homeTeamLogo);
            Glide.with(context).load(awayCrest).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.no_crest).into(holder.awayTeamLogo);
        }
    }

    @Override
    public int getItemCount() {
        return fixturesList.size();
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

    private void updateTeamData(){
    }
}
