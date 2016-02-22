package com.brightlight.padzmj.myfootballscores.Fixtures.Controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.brightlight.padzmj.myfootballscores.R;

/**
 * Created by PadzMJ on 09/02/2016.
 */
public class FixturesViewHolder extends RecyclerView.ViewHolder {

    ImageView homeTeamLogo, awayTeamLogo;
    TextView homeTeamName, awayTeamName, goalsHomeTeam, goalsAwayTeam, matchDate, matchStatus;

    public FixturesViewHolder(View itemView) {
        super(itemView);
        homeTeamLogo = (ImageView) itemView.findViewById(R.id.homeTeamLogo);
        awayTeamLogo = (ImageView) itemView.findViewById(R.id.awayTeamLogo);
        homeTeamName = (TextView) itemView.findViewById(R.id.homeTeamName);
        awayTeamName = (TextView) itemView.findViewById(R.id.awayTeamName);
        goalsHomeTeam = (TextView) itemView.findViewById(R.id.homeTeamScore);
        goalsAwayTeam = (TextView) itemView.findViewById(R.id.awayTeamScore);
        matchDate = (TextView) itemView.findViewById(R.id.matchDate);
        matchStatus = (TextView) itemView.findViewById(R.id.matchStatus);
    }
}
