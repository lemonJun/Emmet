package com.takin.emmet.util;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MavenDelLastUpdated {
    //    private static PropertyHelper propHelper = new PropertyHelper("config");
    private static final String KEY_MAVEN_REPO = "maven.repo";
    //    private static final String MAVEN_REPO_PATH = propHelper.getValue(KEY_MAVEN_REPO);
    private static final String FILE_SUFFIX = "lastUpdated";
    private static final Logger _log = LoggerFactory.getLogger(MavenDelLastUpdated.class);

    public static void main(String[] args) {
        File mavenRep = new File("E:\\Repositories\\Maven");
        if (!mavenRep.exists()) {
            _log.warn("Maven repos is not exist.");
            return;
        }
        File[] files = mavenRep.listFiles((FilenameFilter) FileFilterUtils.directoryFileFilter());
        delFileRecr(files, null);
        _log.info("Clean lastUpdated files finished.");
    }

    private static void delFileRecr(File[] dirs, File[] files) {
        if (dirs != null && dirs.length > 0) {
            for (File dir : dirs) {
                File[] childDir = dir.listFiles((FilenameFilter) FileFilterUtils.directoryFileFilter());
                File[] childFiles = dir.listFiles((FilenameFilter) FileFilterUtils.suffixFileFilter(FILE_SUFFIX));
                delFileRecr(childDir, childFiles);
            }
        }
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.delete()) {
                    System.out.println("File: [" + file.getName() + "] has been deleted.");
                }
            }
        }
    }
}
