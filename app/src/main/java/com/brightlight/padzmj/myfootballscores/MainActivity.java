package com.brightlight.padzmj.myfootballscores;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.brightlight.padzmj.myfootballscores.Fixtures.Model.Fixtures;
import com.brightlight.padzmj.myfootballscores.Fixtures.UI.FixturesFragment;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView networkStatus;
    private List<Fixtures> fixturesList = Collections.emptyList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkStatus = (TextView) findViewById(R.id.networkStatus);

        if (isConnected()) {
            networkStatus.setVisibility(View.GONE);
            FetchFootballData fetchFootballData = new FetchFootballData();
            fetchFootballData.callAllFixtures(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayoutContainer, FixturesFragment.newInstance(this, FetchFootballData.fixturesList)).commit();

            //FixturesFragment.newInstance(fixturesList)
        } else {
            networkStatus.setVisibility(View.VISIBLE);
            networkStatus.setText("No Network, Please check your network connection");
        }
    }

    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return ((networkInfo != null) && networkInfo.isConnected());
    }
}
