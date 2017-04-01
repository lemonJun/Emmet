package com.takin.emmet.util;

import java.util.Calendar;

/**
 *
 *
 * @version 1.0
 * @date 2016年4月15日 下午3:54:21
 * @see
 * @since
 */
public class BoundTime {
    private Bound bound = new Bound();
    private int type;
    private Handler handler;

    private BoundTime(int type, Handler handler) {
        super();
        this.type = type;
        this.handler = handler;
    }

    public Bound next() {
        return next(1);
    }

    public synchronized Bound next(int step) {
        long now = System.currentTimeMillis();
        if (now >= bound.getTime()) {// 计算当天最后切换至
            bound.setTime(handler.execute(type, step));
        }
        return bound;
    }

    public Bound pre() {
        return pre(-1);
    }

    public Bound pre(int step) {
        return next(-step);
    }

    public final static BoundTime miniteBoundTime() {
        return new BoundTime(Calendar.MINUTE, new Handler() {
            @Override
            public long execute(int type, int step) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                cal.add(type, step);
                return cal.getTimeInMillis();
            }
        });
    }

    public final static BoundTime hourBoundTime() {
        return new BoundTime(Calendar.HOUR, new Handler() {
            @Override
            public long execute(int type, int step) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                cal.add(type, step);
                return cal.getTimeInMillis();
            }
        });
    }

    public final static BoundTime dayBoundTime() {
        return new BoundTime(Calendar.DAY_OF_YEAR, new Handler() {
            @Override
            public long execute(int type, int step) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                cal.add(type, step);
                return cal.getTimeInMillis();
            }
        });
    }

    public static class Bound {

        private long time;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

    }

    public interface Handler {
        public long execute(int type, int step);
    }

}
