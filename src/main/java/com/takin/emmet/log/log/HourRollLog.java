package com.takin.emmet.log.log;

import java.util.Calendar;

public class HourRollLog extends RollLog {

    public HourRollLog() {
    }

    @Override
    public int getCalendarType() {
        return Calendar.HOUR_OF_DAY;
    }

}
