package com.takin.emmet.log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.google.common.collect.Maps;
import com.takin.emmet.collection.CollectionUtil;
import com.takin.emmet.log.factory.Resource;
import com.takin.emmet.log.factory.RollLogFactory;
import com.takin.emmet.log.log.RollLog;

public class LogFactory {

    // 多少个write
    public final Map<String, RollLog> logMaps;

    private static String config = "log.properties";

    public final static void setConfig(String configPath) {
        LogFactory.config = configPath;
    }

    private LogFactory() {
        Map<String, RollLog> tmplogMaps = null;
        InputStream inputStream = null;
        try {
            File file = configFile();
            inputStream = new FileInputStream(file);
        } catch (Exception e) {
        }

        if (inputStream == null) {
            inputStream = Resource.getResource(LogFactory.config);
        }
        if (inputStream == null) {
            System.out.println("read config error");
        } else {
            Properties propertis = null;
            try {
                propertis = new Properties();
                propertis.load(inputStream);
                tmplogMaps = getPropertisMap(propertis);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (CollectionUtil.isNotEmpty(tmplogMaps)) {
            logMaps = Collections.unmodifiableMap(tmplogMaps);
            registerHandler(logMaps.entrySet());
        } else {
            logMaps = Maps.newHashMap();
        }
    }

    // 返回配置路径
    public final static File configFile() {
        File file = new File(LogFactory.config);
        if (!file.isAbsolute())
            file = new File(System.getProperty("user.dir"), LogFactory.config);
        return (file);
    }

    // 获得Logger
    public final static Log getLogger(String key) {
        return Inner.LOG_FACTORY.logMaps.get(key);
    }

    private static class Inner {
        public final static LogFactory LOG_FACTORY = new LogFactory();
    }

    private final static void registerHandler(Set<Entry<String, RollLog>> entrys) {
        RollLogFactory.register(entrys.iterator());
    }

    private final static Map<String, RollLog> getPropertisMap(Properties propertis) {
        Map<String, RollLog> tmplogMaps = new HashMap<String, RollLog>();
        Enumeration<Object> its = propertis.keys();
        while (its.hasMoreElements()) {
            String key = (String) its.nextElement();
            if (key.startsWith("appender.")) {
                String[] keys = key.split("\\.");
                if (keys.length == 3) {
                    String prefix = keys[0] + "." + keys[1];
                    LogBean logBean = new LogBean();
                    logBean.setName(keys[1]);
                    logBean.setCls(propertis.getProperty(prefix + ".class"));
                    logBean.setFormat(propertis.getProperty(prefix + ".format"));
                    logBean.setPath(propertis.getProperty(prefix + ".path"));
                    RollLog rollLog = RollLogFactory.newRollLog(logBean);
                    tmplogMaps.put(logBean.getName(), rollLog);
                }
            }
        }
        return tmplogMaps;
    }

}
