package com.sacredrelict.logreader.core.dto;

import java.util.List;

/**
 * Base DTO object for response.
 */
public class LogResponse {

    private List<LogLine> logLines;
    private int from;
    private int size;
    private boolean hasPrev;
    private boolean hasNext;
    private String exceptionMessage;
    private int infoSize;
    private int warningSize;
    private int errorSize;

    public LogResponse() {

    }

    public List<LogLine> getLogLines() {
        return logLines;
    }

    public void setLogLines(List<LogLine> logLines) {
        this.logLines = logLines;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean getHasPrev() {
        return hasPrev;
    }

    public void setHasPrev(boolean hasPrev) {
        this.hasPrev = hasPrev;
    }

    public boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public int getInfoSize() {
        return infoSize;
    }

    public void setInfoSize(int infoSize) {
        this.infoSize = infoSize;
    }

    public int getWarningSize() {
        return warningSize;
    }

    public void setWarningSize(int warningSize) {
        this.warningSize = warningSize;
    }

    public int getErrorSize() {
        return errorSize;
    }

    public void setErrorSize(int errorSize) {
        this.errorSize = errorSize;
    }

    @Override
    public String toString() {
        return "LogResponse{" +
                "logLines.size=" + logLines.size() +
                ", from=" + from +
                ", size=" + size +
                ", hasPrev=" + hasPrev +
                ", hasNext=" + hasNext +
                ", exceptionMessage='" + exceptionMessage + '\'' +
                ", infoSize=" + infoSize +
                ", warningSize=" + warningSize +
                ", errorSize=" + errorSize +
                '}';
    }
}
