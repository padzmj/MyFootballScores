package com.brightlight.padzmj.myfootballscores;

import android.support.v4.app.FragmentManager;

import com.brightlight.padzmj.myfootballscores.Fixtures.Model.Fixture;
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.Fixtures;
import com.brightlight.padzmj.myfootballscores.Fixtures.Model.TeamData;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;

/**
 * Created by PadzMJ on 08/02/2016.
 */
public class FetchFootballData {


    //Leagues
    final String BUNDESLIGA1 = "394";
    final String BUNDESLIGA2 = "395";
    final String LIGUE1 = "396";
    final String LIGUE2 = "397";
    final String PREMIER_LEAGUE = "398";
    final String PRIMERA_DIVISION = "399";
    final String SEGUNDA_DIVISION = "400";
    final String SERIE_A = "401";
    final String PRIMERA_LIGA = "402";
    final String Bundesliga3 = "403";
    final String EREDIVISIE = "404";

    private FootballDataApi footballDataApi;
    private static final String footballBaseURL = "http://api.football-data.org/v1/";
    private static final String teamCrestBaseURL = "https://upload.wikimedia.org/wikipedia/";

    private static final String seasons= "http://api.football-data.org/v1/soccerseasons/";
    private static final String teams= "http://api.football-data.org/v1/teams/";
    //Context context;
    FragmentManager fragmentManager;

    public Retrofit retrofit;

    private Realm realm;

    public static List<Fixtures> fixturesList = new ArrayList<>();
    protected static TeamData teamData;

    public FetchFootballData() {
    }

    public FootballDataApi fetchFootballData() {
        if (footballDataApi == null) {

            Gson gson = new GsonBuilder()
                    .setExclusionStrategies(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            return f.getDeclaringClass().equals(RealmObject.class);
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    })
                    .create();

            //for headers
            OkHttpClient okClient = new OkHttpClient();
            okClient.interceptors().add(new Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                    return chain.proceed(chain.request());
                }
            });
            retrofit = new Retrofit.Builder()
                    .baseUrl(footballBaseURL)
                    .client(okClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            okClient.setConnectionPool(ConnectionPool.getDefault());
            okClient.getConnectionPool().evictAll();

            footballDataApi = retrofit.create(FootballDataApi.class);
        }
        return footballDataApi;
    }
    //

    public Observable<Fixture> getAllFixturesCall(String timeFrame){
        return fetchFootballData().getAllFixtures(timeFrame);
    }

    /*
    public List<Fixtures> callAllFixtures(final Context context) {

        final List<Fixtures> fixturesList1 = new ArrayList<>();
        Observable<Fixture> callAllFixturesObservable = fetchFootballData().getAllFixtures("p4");

        callAllFixturesObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Fixture>() {
                               @Override
                               public void onCompleted() {
                                   Log.i("Fetch", "Completed");
                                   FixturesFragment.fixturesList = fixturesList1;
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.i("Fetch", "Error " + e.toString());
                               }

                               @Override
                               public void onNext(Fixture fixture) {
                                   String fixtureID;
                                   String homeTeam;
                                   String awayTeam;
                                   String homeGoals;
                                   String awayGoals;

                                   int ti = 0;

                                   Log.i("Fetch", "Fixtures Size " + fixture.getFixtures().size());


                                   for (int i = 0; i < fixture.getFixtures().size(); i++) {
                                       String season = fixture.getFixtures().get(i).getLinks().getSoccerSeason().getHref();

                                       String league = season.replace(seasons, "");

                                       Log.i("Seasons", league);

                                       if (league.equals(BUNDESLIGA2) || league.equals(PRIMERA_LIGA) ||
                                               league.equals(BUNDESLIGA1) || league.equals(LIGUE1) ||
                                               league.equals(SERIE_A) || league.equals(EREDIVISIE) || league.equals(PREMIER_LEAGUE)) {

                                           final Fixtures gameFixture = new Fixtures();
                                           MatchResults results = new MatchResults();
                                           final TeamData homeTeamData = new TeamData();
                                           final TeamData awayTeamData = new TeamData();

                                           ti++;

                                           fixtureID = fixture.getFixtures().get(i).getLinks().getSelf().getHref();
                                           homeTeam = fixture.getFixtures().get(i).getHomeTeamName();
                                           awayTeam = fixture.getFixtures().get(i).getAwayTeamName();
                                           homeGoals = fixture.getFixtures().get(i).getResult().getGoalsHomeTeam();
                                           awayGoals = fixture.getFixtures().get(i).getResult().getGoalsAwayTeam();

                                           //Get Just Fixture ID
                                           int pos1 = fixtureID.lastIndexOf("/");
                                           String fixtureID1 = fixtureID.substring(pos1 + 1, fixtureID.length());
                                           Log.i("RetroLeagueSize", ti + " New Size " + fixtureID1);

                                           String homeTeamURL = fixture.getFixtures().get(i).getLinks().getHomeTeam().getHref();
                                           String awayTeamURL = fixture.getFixtures().get(i).getLinks().getAwayTeam().getHref();


                                           Log.i("Fetch", "Fixture " + ti);
                                           Log.i("Fetch", homeTeam +  " v " + awayTeam);

                                           final String homeTeamID = homeTeamURL.replace(teams, "");
                                           String awayTeamID = awayTeamURL.replace(teams, "");

                                           Observable<TeamData> homeTeamDataObservable = fetchFootballData().getTeam(homeTeamID);
                                           homeTeamDataObservable
                                                   .subscribeOn(Schedulers.newThread())
                                                   .observeOn(AndroidSchedulers.mainThread())
                                                   .subscribe(new Subscriber<TeamData>() {
                                                       @Override
                                                       public void onCompleted() {
                                                           Log.i("NEXT TEAM DATA", "Complete ");

                                                       }

                                                       @Override
                                                       public void onError(Throwable e) {
                                                           Log.i("NEXT TEAM DATA", "Error  " + e.toString());
                                                       }

                                                       @Override
                                                       public void onNext(TeamData teamData) {
                                                           Log.i("NEXT TEAM DATA", "Home Team  " + teamData.getCrestUrl());
                                                           gameFixture.setHomeTeamData(teamData);
                                                       }
                                                   });

                                           Observable<TeamData> awayTeamDataObservable = fetchFootballData().getTeam(awayTeamID);
                                           awayTeamDataObservable
                                                   .subscribeOn(Schedulers.newThread())
                                                   .observeOn(AndroidSchedulers.mainThread())
                                                   .subscribe(new Subscriber<TeamData>() {
                                                       @Override
                                                       public void onCompleted() {
                                                           Log.i("NEXT TEAM DATA2", "Completed ");

                                                       }

                                                       @Override
                                                       public void onError(Throwable e) {
                                                           Log.i("NEXT TEAM DATA2", "Error  " + e.toString());
                                                       }

                                                       @Override
                                                       public void onNext(TeamData teamData) {
                                                           Log.i("NEXT TEAM DATA2", "Away Team  " + teamData.getCrestUrl());
                                                           gameFixture.setAwayTeamData(teamData);
                                                       }
                                                   });

                                           results.setGoalsHomeTeam(homeGoals);
                                           results.setGoalsAwayTeam(awayGoals);

                                           gameFixture.setHomeTeamName(homeTeam);
                                           gameFixture.setAwayTeamName(awayTeam);
                                           gameFixture.setResult(results);
                                           gameFixture.setHomeTeamID(homeTeamID);
                                           gameFixture.setAwayTeamID(awayTeamID);
                                           gameFixture.setHomeTeamData(homeTeamData);
                                           gameFixture.setAwayTeamData(awayTeamData);


                                           fixturesList1.add(gameFixture);
                                       }
                                   }
                                   FixturesFragment.fixturesList = fixturesList1;
                                   FetchFootballData.fixturesList = fixturesList1;
                                   Log.i("FixtureList1A", FetchFootballData.fixturesList.size() + " SIZE Completed");
                               }
                           });
        //Log.i("FixtureList1B", fixturesList.size() + " SIZE");
        return FetchFootballData.fixturesList;
    }

    /*
    public void callAllFixtures(Context context) {
        Call<Fixture> callAllFixtures = fetchFootballData().getAllFixtures("p3");
        final List<Fixtures> fixturesList1 = new ArrayList<>();
        realm = Realm.getInstance(context);
        callAllFixtures.enqueue(new Callback<Fixture>() {
            @Override
            public void onResponse(Response<Fixture> response, Retrofit retrofit) {
                if (response.isSuccess()) {

                    Log.i("RetroFixtures", response.body().getCount() + " Size");
                    String fixtureID;
                    String homeTeam;
                    String awayTeam;
                    String homeGoals;
                    String awayGoals;
//                    response.body().getFixtures().size()
                    int ti = 0;

                    for (int i = 0; i < response.body().getFixtures().size(); i++) {
                        String season = response.body().getFixtures().get(i).getLinks().getSoccerSeason().getHref();

                        String league = season.replace(seasons, "");

                        Log.i("Seasons", league);

                        if(     league.equals(BUNDESLIGA2) || league.equals(PRIMERA_LIGA) ||
                                league.equals(BUNDESLIGA1)    || league.equals(LIGUE1)       ||
                                league.equals(SERIE_A)        || league.equals(EREDIVISIE)   || league.equals(PREMIER_LEAGUE)){

                            final Fixtures fixture = new Fixtures();
                            MatchResults results = new MatchResults();
                            final TeamData homeTeamData = new TeamData();
                            final TeamData awayTeamData = new TeamData();

                            ti++;

                            fixtureID = response.body().getFixtures().get(i).getLinks().getSelf().getHref();
                            homeTeam = response.body().getFixtures().get(i).getHomeTeamName();
                            awayTeam = response.body().getFixtures().get(i).getAwayTeamName();
                            homeGoals = response.body().getFixtures().get(i).getResult().getGoalsHomeTeam();
                            awayGoals = response.body().getFixtures().get(i).getResult().getGoalsAwayTeam();

                            //Get Just Fixture ID
                            int pos1 = fixtureID.lastIndexOf("/");
                            String fixtureID1 = fixtureID.substring(pos1+1, fixtureID.length());
                            Log.i("RetroLeagueSize", ti + " New Size " + fixtureID1);

                            String homeTeamURL = response.body().getFixtures().get(i).getLinks().getHomeTeam().getHref();
                            String awayTeamURL = response.body().getFixtures().get(i).getLinks().getAwayTeam().getHref();


                            Log.i("HOMEURL", "Home Team URL " + homeTeamURL);

                            final String homeTeamID = homeTeamURL.replace(teams, "");
                            String awayTeamID = awayTeamURL.replace(teams, "");

//                            TeamInfo teamInfo = new TeamInfo();
//                            teamInfo.execute(homeTeamID);
                            Response<TeamData> teamDataResponse;


                            Observable<TeamData> homeTeamDataObservable = fetchFootballData().getTeam(homeTeamID);
                            homeTeamDataObservable.subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .unsubscribeOn(Schedulers.io())
                                    .subscribe(new Subscriber<TeamData>() {
                                        @Override
                                        public void onCompleted() {
                                            Log.i("NEXT TEAM DATA", "Complete ");

                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            Log.i("NEXT TEAM DATA", "Error  " + e.toString());
                                        }

                                        @Override
                                        public void onNext(TeamData teamData) {
                                            Log.i("NEXT TEAM DATA", "Home Team  " + teamData.getCrestUrl());
                                            fixture.setHomeTeamData(teamData);
                                        }
                                    });

                            Observable<TeamData> awayTeamDataObservable = fetchFootballData().getTeam(awayTeamID);
                            awayTeamDataObservable.subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .unsubscribeOn(Schedulers.io())
                                    .subscribe(new Subscriber<TeamData>() {
                                        @Override
                                        public void onCompleted() {
                                            Log.i("NEXT TEAM DATA2", "Completed ");

                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            Log.i("NEXT TEAM DATA2", "Error  " + e.toString());
                                        }

                                        @Override
                                        public void onNext(TeamData teamData) {
                                            Log.i("NEXT TEAM DATA2", "Away Team  " + teamData.getCrestUrl());
                                            fixture.setAwayTeamData(teamData);
                                        }
                                    });

//                            Call<TeamData> homeTeamDataCall = fetchFootballData().getTeam(homeTeamID);
//                            homeTeamDataCall.enqueue(new Callback<TeamData>() {
//                                @Override
//                                public void onResponse(Response<TeamData> response, Retrofit retrofit) {
//                                    if(response.body()!=null){
//                                        fixture.setHomeTeamData(response.body());
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Throwable t) {
//                                }
//                            });
//
//                            Call<TeamData> awayTeamDataCall = fetchFootballData().getTeam(awayTeamID);
//                            awayTeamDataCall.enqueue(new Callback<TeamData>() {
//                                @Override
//                                public void onResponse(Response<TeamData> response, Retrofit retrofit) {
//                                    if(response.body()!=null){
//                                        fixture.setAwayTeamData(response.body());
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Throwable t) {
//
//                                }
//                            });

                            //Log.i("RetroFixtures", homeTeam + " " + homeTeamID + " " + homeGoals + " - " + awayGoals + " " + awayTeamID + " " + awayTeam);

                            //Log.i("HOMECRESTURL", homeTeamID + " " + i);

//                            Log.i("AWAYCRESTURL", awayTeamID + " " + i);

                            results.setGoalsHomeTeam(homeGoals);
                            results.setGoalsAwayTeam(awayGoals);

                            fixture.setHomeTeamName(homeTeam);
                            fixture.setAwayTeamName(awayTeam);
                            fixture.setResult(results);
                            fixture.setHomeTeamID(homeTeamID);
                            fixture.setAwayTeamID(awayTeamID);
                            fixture.setHomeTeamData(homeTeamData);
                            fixture.setAwayTeamData(awayTeamData);

                            final int pos = i;

                            //TRY ADD TO LIST AND TERNARY OPERATION!


                            fixturesList1.add(fixture);

                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    for(int k = 0; k < fixturesList1.size(); k++){

                                        Log.i("DB", fixturesList1.size() + " HERE");
                                    }
                                }
                            });
                        }

                        //callTeam(homeTeamID);
                    }
                    FetchFootballData.fixturesList = fixturesList1;

                    //setFixtures(fixturesList1);
                } else{
                    Log.i("RetroFIX", "All fixtures not on " + response.code());
                }


            }
            @Override
            public void onFailure(Throwable t) {
                Log.i("RetroFixtures", "Throw " + t.toString());
            }
        });
    }*/
/*
    public void callAllLeagues() {
        Call<List<LeagueData>> callLeagueData = fetchFootballData().getAllLeagues();
        callLeagueData.enqueue(new Callback<List<LeagueData>>() {
            @Override
            public void onResponse(Response<List<LeagueData>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    //Log.i("RetroAllLeagues", response.code() + " all leagues code");
                    //Log.i("RetroAllLeagues", "Raw all leagues " + response.message());
                } else Log.i("RetroTeam", "All Leagues not on " + response.code());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }*/
/*
    public void callLeagueData(final String league) {
        Call<LeagueData> callLeagueData = fetchFootballData().getLeagueTable(league);
        callLeagueData.enqueue(new Callback<LeagueData>() {
            @Override
            public void onResponse(Response<LeagueData> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    //Log.i("RetroLeagueTable", response.code() + " league table code");

                    String position, teamName, status;

                    for (int i = 0; i < response.body().getStanding().size(); i++) {
                        position = response.body().getStanding().get(i).getPosition();
                        teamName = response.body().getStanding().get(i).getTeamName();
                        Log.i("RetroLeagueTable", position + " " + teamName);
                    }


                    Log.i("RetroLeagueTable", "Raw league table " + response.message());
                } else Log.i("RetroTeam", "League Table not on " + response.code());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("RetroLeagueTable", "Throwable " + t);
            }
        });
    }*/

    public void callTeam(final String teamID) {
//        Observable<TeamData> callLeagueData = fetchFootballData().getTeam(teamID);
//        callLeagueData.enqueue(new Callback<TeamData>() {
//            @Override
//            public void onResponse(Response<TeamData> response, Retrofit retrofit) {
//                if (response.isSuccess()) {
//                    //Log.i("RetroTeam", response.code() + " league table code");
//
//                    setTeamDataAvailable(response);
//
//                    String teamLogo = response.body().getCrestUrl();
//                    String value = response.body().getSquadMarketValue();
//
//                    Log.i("RetroTeam", "Team Logo " + teamLogo + " " + value);
//
//                } else Log.i("RetroTeam", "Team no Logo" + response.code());
//            }
//            @Override
//            public void onFailure(Throwable t) {
//                Log.i("RetroTeam", "Throwable " + t);
//            }
//        });
    }

    private void setTeamDataAvailable(Response<TeamData> response) {
        FetchFootballData.teamData = response.body();
    }

    public TeamData getTeamDataAvailabe() {
        return FetchFootballData.teamData;
    }

    private void setFixtures(List<Fixtures> fixturesList) {
        FetchFootballData.fixturesList = fixturesList;
        //Log.i("LIST SIZE", fixturesList.size() + " List Size 1");
        //Log.i("LIST SIZE", FetchFootballData.fixturesList + " List Size 2");
    }

    public List<Fixtures> getAllFixtures() {
        return FetchFootballData.fixturesList;
    }
}


