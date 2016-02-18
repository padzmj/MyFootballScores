package com.brightlight.padzmj.myfootballscores.Fixtures.Model;

import java.util.List;

/**
 * Created by PadzMJ on 08/02/2016.
 */
public class Fixture {
    private String timeFrameStart, timeFrameEnd, count;
    private List<Fixtures> fixtures;

    public String getTimeFrameStart() {
        return timeFrameStart;
    }

    public void setTimeFrameStart(String timeFrameStart) {
        this.timeFrameStart = timeFrameStart;
    }

    public String getTimeFrameEnd() {
        return timeFrameEnd;
    }

    public void setTimeFrameEnd(String timeFrameEnd) {
        this.timeFrameEnd = timeFrameEnd;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<Fixtures> getFixtures() {
        return fixtures;
    }

    public void setFixtures(List<Fixtures> fixtures) {
        this.fixtures = fixtures;
    }
}
