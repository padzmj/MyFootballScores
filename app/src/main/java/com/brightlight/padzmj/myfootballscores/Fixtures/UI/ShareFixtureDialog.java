package com.brightlight.padzmj.myfootballscores.Fixtures.UI;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.brightlight.padzmj.myfootballscores.R;

/**
 * Created by PadzMJ on 19/03/2016.
 */
public class ShareFixtureDialog extends DialogFragment {
    private static final String FOOTBALL_SCORES_HASHTAG = "#";
    private static Context mContext;
    private String mHomeTeam, mHomeGoals, mAwayTeam, mAwayGoals, mHomeShortName, mAwayShortName;
    private final static String HOME_TEAM = "homeTeam";
    private final static String HOME_SHORT_NAME = "homeShortName";
    private final static String AWAY_TEAM = "awayTeam";
    private final static String AWAY_SHORT_NAME = "awayShortName";
    private final static String HOME_GOALS = "homeGoals";
    private final static String AWAY_GOALS = "awayGoals";


    public static ShareFixtureDialog newInstance(Context context, String homeTeam, String homeGoals, String awayTeam, String awayGoals){
        mContext = context;
        ShareFixtureDialog shareFixtureDialog = new ShareFixtureDialog();

        Bundle shareBundle = new Bundle();
        shareBundle.putString(HOME_TEAM, homeTeam);
        shareBundle.putString(AWAY_TEAM, awayTeam);
        shareBundle.putString(HOME_GOALS, homeGoals);
        shareBundle.putString(AWAY_GOALS, awayGoals);
        shareFixtureDialog.setArguments(shareBundle);

        return shareFixtureDialog;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.share_fixture_fragment, container, false);
        final EditText shareEditText = (EditText) rootView.findViewById(R.id.shareText);
        Button shareButton = (Button) rootView.findViewById(R.id.shareButton);

        Bundle shared = getArguments();
        if(shared!=null){
            mHomeTeam = shared.getString(HOME_TEAM);
            mHomeGoals = shared.getString(HOME_GOALS);
            mAwayTeam = shared.getString(AWAY_TEAM);
            mAwayGoals = shared.getString(AWAY_GOALS);

            mHomeGoals = (mHomeGoals == null)? " " : mHomeGoals;
            mAwayGoals = (mAwayGoals == null)? " " : mAwayGoals;

            shareEditText.setText(mHomeTeam + " " + mHomeGoals + " v " + mAwayGoals + " " + mAwayTeam);
        }

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(createShareForecastIntent(shareEditText.getText().toString()));
                dismiss();
            }
        });

        return rootView;
    }

    public Intent createShareForecastIntent(String shareText) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText + FOOTBALL_SCORES_HASHTAG);
        return shareIntent;
    }
}
