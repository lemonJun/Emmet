package com.takin.emmet.log.log;

import java.util.Calendar;

public class DayRollLog extends RollLog {

    @Override
    public int getCalendarType() {
        return Calendar.DAY_OF_YEAR;
    }

}
