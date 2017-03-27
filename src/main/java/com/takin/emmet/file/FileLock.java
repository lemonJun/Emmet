package com.takin.emmet.file;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件锁
 * 基于filechannel实现的文件锁
 * 
 * @author WangYazhou
 * @date  2017年3月27日 下午3:03:21
 * @see
 */
public class FileLock {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileLock.class);
    private File file;

    private FileChannel channel = null;
    private java.nio.channels.FileLock lock = null;

    public FileLock(String filename) {
        this(new File(filename));
    }

    public FileLock(File file) {
        this.file = file;
        FileUtils.createFileIfNotExist(file);
    }

    /**
     * 获得锁
     */
    public boolean tryLock() {
        try {
            Path path = Paths.get(file.getPath());
            if (channel != null && channel.isOpen()) {
                return false;
            }
            channel = FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.READ);
            lock = channel.tryLock();
            if (lock != null) {
                return true;
            }
        } catch (IOException e) {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e1) {
                    LOGGER.error("file channel close failed.", e1);
                }
            }
            return false;
        }
        return false;
    }

    /**
     * 释放锁
     */
    public void release() {
        try {
            if (lock != null) {
                lock.release();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (channel != null) {
                try {
                    channel.close(); // also releases the lock
                } catch (IOException e) {
                    LOGGER.error("file channel close failed.", e);
                }
            }
        }
    }
}
