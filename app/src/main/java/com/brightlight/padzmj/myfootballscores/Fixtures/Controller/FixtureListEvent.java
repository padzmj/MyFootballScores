package com.brightlight.padzmj.myfootballscores.Fixtures.Controller;

import com.brightlight.padzmj.myfootballscores.Fixtures.Model.Fixtures;

import java.util.List;

/**
 * Created by PadzMJ on 17/02/2016.
 */
public class FixtureListEvent {
    public final List<Fixtures> fixturesList;

    public FixtureListEvent(List<Fixtures> fixturesList) {
        this.fixturesList = fixturesList;
    }
}
