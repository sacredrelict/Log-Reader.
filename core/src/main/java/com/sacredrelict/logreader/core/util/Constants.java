package com.sacredrelict.logreader.core.util;

import java.time.format.DateTimeFormatter;

/**
 * Constants file for core module.
 */
public class Constants {

    public final static String timestampRgx = "(?<timestamp>\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3})";
    public final static String levelRgx = "(?<level>INFO|WARNING|ERROR|WARN|TRACE|DEBUG|FATAL)";
    public final static String textRgx = "(?<text>.*?)(?=\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3}|\\Z)";
    public final static String logEntryPattern = timestampRgx + " " + levelRgx + " " + textRgx;

    public final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS");

}
