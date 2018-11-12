package com.sacredrelict.logreader.core.dto;

import com.sacredrelict.logreader.core.enums.LogLevel;

/**
 * Base DTO object for log file line.
 */
public class LogLine {

    private String date;
    private LogLevel logLevel;
    private String text;

    public LogLine() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    @Override
    public String toString() {
        return "LogLine{" +
                "date='" + date + '\'' +
                ", logLevel=" + logLevel +
                ", text='" + text + '\'' +
                '}';
    }

}
