package com.brightlight.padzmj.myfootballscores.Fixtures.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brightlight.padzmj.myfootballscores.FetchFootballData;
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.Fixtures;
import com.brightlight.padzmj.myfootballscores.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PadzMJ on 09/02/2016.
 */
public class FixturesAdapter extends RecyclerView.Adapter<FixturesViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<Fixtures> fixturesList = new ArrayList<>();
    FetchFootballData fetchFootballData;

    public FixturesAdapter(Context context, List<Fixtures> fixturesList){
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.fixturesList = fixturesList;
        Log.i("CHECK", "Fixtures Adapter");


        //Toast.makeText(context, "ADAPTER", Toast.LENGTH_LONG).show();

//        String n = ((fixturesList.get(0).getResult().getGoalsAwayTeam()==null)? "0" : fixturesList.get(0).getResult().getGoalsHomeTeam());


        //Toast.makeText(context, "ADAPTER " + n, Toast.LENGTH_LONG).show();

//        for(int i = 0; i<100; i++){
//            Fixtures fixtures = new Fixtures();
//            MatchResults matchResults = new MatchResults();
//            String homeTeam = "HomeTeam ";
//            String awayTeam = "AwayTeam ";
//
//            fixtures.setHomeTeamName(homeTeam + i);
//            fixtures.setAwayTeamName(awayTeam + i);
//
//            Random randomScore1 = new Random();
//
//            int score = randomScore1.nextInt(10 - 5) + 5;
//            int score2 = randomScore1.nextInt(10 - 5) + 5;
//
//            matchResults.setGoalsHomeTeam(score + "");
//            matchResults.setGoalsAwayTeam(score2 + "");
//
//            fixtures.setResult(matchResults);
//
//            //this.fixturesList.add(fixtures);
//        }
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

    @Override
    public void onBindViewHolder(final FixturesViewHolder holder, int position) {
        final String homeTeamName =  fixturesList.get(position).getHomeTeamName();
        final String awayTeamName = fixturesList.get(position).getAwayTeamName();
        final String homeTeamGoals = fixturesList.get(position).getResult().getGoalsHomeTeam();
        final String awayTeamGoals = ((fixturesList.get(position).getResult()==null)? "0" : fixturesList.get(position).getResult().getGoalsAwayTeam());

        Log.i("CHECK", "Bind View Holder");

        final String homeCrest = fixturesList.get(position).getHomeTeamData().getCrestUrl();
        final String awayCrest =fixturesList.get(position).getAwayTeamData().getCrestUrl();

        String homeCrestURL = homeCrest;
        String awayCrestURL = awayCrest;

        //Check image is already SVG
        if(!homeCrestURL.contains(".png")){
            homeCrestURL = updateCrestURL(homeCrest);
        }
        if(!awayCrestURL.contains(".png")){
            awayCrestURL = updateCrestURL(awayCrest);
        }

        Log.i("UTILITY", fixturesList.size() + " SIZE");

        Log.i("NULLEDHOME", homeCrestURL + " " + position);
        Log.i("NULLEDAWAY", awayCrestURL + " " + position);

        holder.homeTeamName.setText(homeTeamName);
        holder.awayTeamName.setText(awayTeamName);
        holder.goalsHomeTeam.setText(homeTeamGoals);
        holder.goalsAwayTeam.setText(awayTeamGoals);

        Glide.with(context).load(homeCrestURL).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.homeTeamLogo);
        Glide.with(context).load(awayCrestURL).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.awayTeamLogo);
    }

    @Override
    public int getItemCount() {
        return fixturesList.size();
    }

    public String updateCrestURL(String url){
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


        Log.i("URLUPDATE", url + " NEW URL: " + newURL + " " + pos);
//        Log.i("URLUPDATE", url + " NEW URL: " + newURL + " " + x);

        if(newURL.contains(_de)){
            String newURL1 = newURL.replaceFirst(_de, baseURL+_de+thumb);
            urlReturn = newURL1+"/"+imageSize+lastPartURL+".png";
            Log.i("URLUPDATE", url + " NEW URL1: " + urlReturn);
        }

        if(newURL.contains(_en)){
            String newURL1 = newURL.replaceFirst(_en, baseURL+_en+thumb);
            urlReturn = newURL1+"/"+imageSize+lastPartURL+".png";
            Log.i("URLUPDATE", url + " NEW URL1: " + urlReturn);
        }

        //Paderborn contains https
        if(newURL.contains(commons)){
            if(newURL.contains(baseURL)){
                String newURL2 = newURL.replace(commons, commons+thumb);
                urlReturn = newURL2+"/"+imageSize+lastPartURL+".png";
                Log.i("URLUPDATE", url + " NEW URL2: " + urlReturn);
            }else {
                String newURL2 = newURL.replace(commons, baseURL+commons+thumb);
                urlReturn = newURL2+"/"+imageSize+lastPartURL+".png";
                Log.i("URLUPDATE", url + " NEW URL3: " + urlReturn);
            }
        }

        return urlReturn;
    }
}
