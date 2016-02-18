package com.brightlight.padzmj.myfootballscores;

import com.brightlight.padzmj.myfootballscores.Fixtures.Model.Fixture;
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.TeamData;
import com.brightlight.padzmj.myfootballscores.League.LeagueData;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by PadzMJ on 08/02/2016.
 */
public interface FootballDataApi {
    String api_key1 = "00cc1325f6f745079e175af58629cafc";

    @Headers("X-Auth-Token:" + api_key1)
    @GET("fixtures")
    Observable<Fixture> getAllFixtures(
            @Query("timeFrame") String timeFrame
    );


    @Headers("X-Auth-Token:" + api_key1)
    @GET("soccerseasons")
    Observable<List<LeagueData>> getAllLeagues(
    );


    @Headers("X-Auth-Token:" + api_key1)
    @GET("soccerseasons/{id}/fixtures")
    Observable<LeagueData> getLegueFixtures(
            @Path("id") String id
    );


    @Headers("X-Auth-Token:" + api_key1)
    @GET("soccerseasons/{id}/leagueTable")
    Observable<LeagueData> getLeagueTable(
            @Path("id") String id
    );


    @Headers("X-Auth-Token:" + api_key1)
    @GET("teams/{id}")
    Observable<TeamData> getTeam(
            @Path("id") String id
    );
}
