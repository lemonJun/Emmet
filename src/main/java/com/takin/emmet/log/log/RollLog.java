package com.takin.emmet.log.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.locks.ReentrantLock;

import com.takin.emmet.log.Log;
import com.takin.emmet.log.LogBean;

/**
 * @author minds
 * @version 1.0
 */
public abstract class RollLog implements Log {

    final static String LINE = "\r\n";
    // 同步�?
    private String filename;
    // 日志路径
    private String path;
    // 同步锁 
    private final ReentrantLock lock = new ReentrantLock();

    volatile OutputStream writer = null;

    public SimpleDateFormat format;

    private long bound = 0l;

    private String name;

    public RollLog() {
    }

    public boolean compareAndSetGate() {
        long now = System.currentTimeMillis();
        if (now >= this.getBound()) {// 计算当天�?后切换至
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.add(getCalendarType(), 1);
            setBound(cal.getTimeInMillis());
            return false;
        }
        return true;
    }

    public abstract int getCalendarType();

    public long getBound() {
        return bound;
    }

    public void setBound(long bound) {
        this.bound = bound;
    }

    private void updateLocalFile() {
        try {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        filename = updateFile();
        try {
            writer = new FileOutputStream(filename, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加日志
     */
    @Override
    public void add(String... logs) {
        StringBuffer buffer = new StringBuffer(300);
        for (String log : logs) {
            buffer.append(log).append("\001");
        }
        add(buffer.toString());
    }

    @Override
    public void add(String log) {
        lock.lock();
        try {
            if (!compareAndSetGate()) {
                updateLocalFile();
            }
            flush(log);
        } finally {
            lock.unlock();
        }
    }

    public void flush(String log) {
        StringBuilder buffer = new StringBuilder(log);
        buffer.append("\r\n");
        try {
            byte[] content = buffer.toString().getBytes();
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 当服务关闭的话，把内存中日志先入�?
     */
    public void release() {
        try {
            if (writer != null) {
                writer.flush();
                writer.close();
                writer = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得文件
     */
    public String updateFile() {
        return path + name + format.format(System.currentTimeMillis()) + ".log";
    }

    public void setLogBean(LogBean logBean) {
        path = logBean.getPath();
        if (!path.endsWith("/")) {
            path = path + "/";
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (logBean.getFormat() == null) {
            logBean.setFormat("yyyy-MM-dd-HH-mm-ss");
        }
        name = logBean.getName();
        format = new SimpleDateFormat(logBean.getFormat());
    }

}
