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
        final String homeTeamName =  fixturesList.get(position).getHomeTeamData().getShortName();
        final String awayTeamName = fixturesList.get(position).getAwayTeamData().getShortName();
        final String homeTeamGoals = fixturesList.get(position).getResult().getGoalsHomeTeam();
        final String awayTeamGoals = ((fixturesList.get(position).getResult()==null)? "0" : fixturesList.get(position).getResult().getGoalsAwayTeam());
        final String matchDate = fixturesList.get(position).getDate();
        String matchStatus = fixturesList.get(position).getStatus();


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

        holder.homeTeamName.setText(homeTeamName);
        holder.awayTeamName.setText(awayTeamName);
        holder.goalsHomeTeam.setText(homeTeamGoals);
        holder.goalsAwayTeam.setText(awayTeamGoals);
        holder.matchDate.setText(matchDate.substring(matchDate.indexOf("T")+1, matchDate.lastIndexOf(":")));
        //holder.matchStatus.setVisibility(View.VISIBLE);

        if(matchStatus!=null){
            switch (matchStatus){
                case ("TIMED"):
                    holder.matchStatus.setVisibility(View.GONE);
                    break;
                case ("FINISHED"):
                    matchStatus = "FT";
                    break;
                default:
                    matchStatus = "-";

            }
        }


        holder.matchStatus.setText(matchStatus);

        //Test just crest later!!!!
        Glide.with(context).load(homeCrestURL).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.no_crest).crossFade().into(holder.homeTeamLogo);
        Glide.with(context).load(awayCrestURL).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.no_crest).crossFade().into(holder.awayTeamLogo);
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
}
