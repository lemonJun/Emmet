package com.takin.emmet.log.factory;

import java.util.Iterator;
import java.util.Map.Entry;

import com.takin.emmet.log.LogBean;
import com.takin.emmet.log.log.RollLog;

public class RollLogFactory {

    public final static RollLog newRollLog(LogBean logBean) {
        // 通过反射创建
        RollLog rollLog = null;
        try {
            rollLog = (RollLog) Class.forName(logBean.getCls()).newInstance();
            rollLog.setLogBean(logBean);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return rollLog;
    }

    public final static void register(Iterator<Entry<String, RollLog>> iterator) {
        Thread shutdownThread = new Thread(new LogShutDown(iterator));
        Runtime.getRuntime().addShutdownHook(shutdownThread);
    }
}
