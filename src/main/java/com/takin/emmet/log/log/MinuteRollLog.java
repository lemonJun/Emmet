package com.takin.emmet.log.log;

import java.util.Calendar;

public class MinuteRollLog extends RollLog {

    @Override
    public int getCalendarType() {
        return Calendar.MINUTE;
    }

}
