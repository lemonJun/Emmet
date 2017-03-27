package com.takin.emmet.log.log;

import java.util.Calendar;

public class DayRollLog extends RollLog {

    public DayRollLog() {
    }

    @Override
    public int getCalendarType() {
        return Calendar.DAY_OF_YEAR;
    }

}
