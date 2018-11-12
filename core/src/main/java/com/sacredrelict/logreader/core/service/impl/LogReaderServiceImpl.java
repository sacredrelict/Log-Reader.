package com.sacredrelict.logreader.core.service.impl;

import com.sacredrelict.logreader.core.dto.LogLine;
import com.sacredrelict.logreader.core.dto.LogResponse;
import com.sacredrelict.logreader.core.enums.LogLevel;
import com.sacredrelict.logreader.core.service.LogReaderService;
import com.sacredrelict.logreader.core.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Service to work with log files.
 */
@Service
public class LogReaderServiceImpl implements LogReaderService {

    private static final Logger LOG = LoggerFactory.getLogger(LogReaderServiceImpl.class);

    private static final int DEFAULT_LOGS_FROM = 1;
    private static final int DEFAULT_LOGS_SIZE = 4;
    private static final Pattern PATTERN = Pattern.compile(Constants.logEntryPattern);

    @Value("${log.file.path}")
    private String logFilePath;

    /**
     * Read log file line be line and write data to LogResponse object.
     * @param from - from what line to start reading.
     * @param size - lines, that should be written.
     * @return - LogResponse object.
     */
    @Override
    public LogResponse getLogs(Integer from, Integer size) {
        if (from == null) {
            from = DEFAULT_LOGS_FROM;
        }
        if (size == null) {
            size = DEFAULT_LOGS_SIZE;
        }

        LogResponse logResponse = new LogResponse();
        logResponse.setFrom(from);
        logResponse.setSize(size);
        logResponse.setHasPrev(from >= size + 1);

        Thread thread = new Thread(){

            @Override
            public void run(){

                try {
                    // Read lines from files.
                    Stream<String> stream = Files.lines(Paths.get(logFilePath), StandardCharsets.UTF_8);
                    Iterator<String> iterator = stream.iterator();
                    List<LogLine> logs = new LinkedList<>();
                    int counter = 1;

                    while (iterator.hasNext()) {
                        String line = iterator.next();
                        Matcher matcher = PATTERN.matcher(line);
                        boolean isNewLogLine = true;

                        // If line not matches with regex, skip it.
                        if (!matcher.matches()) {
                            isNewLogLine = false;
                        }
                        if (isNewLogLine) {

                            LogLevel logLevel = LogLevel.UNKNOWN;
                            try {
                                logLevel = LogLevel.valueOf(matcher.group(2));
                            } catch (Exception e) {
                                LOG.debug("Log level is unknown: " + matcher.group(2));
                            }

                            if (logLevel == LogLevel.INFO) {
                                logResponse.setInfoSize(logResponse.getInfoSize() + 1);
                            }

                            if (logLevel == LogLevel.WARNING) {
                                logResponse.setWarningSize(logResponse.getWarningSize() + 1);
                            }

                            if (logLevel == LogLevel.ERROR) {
                                logResponse.setErrorSize(logResponse.getErrorSize() + 1);
                            }

                            // If iterator in right position, start write data to dto.
                            if (counter >= logResponse.getFrom() && counter <= (logResponse.getFrom() + logResponse.getSize() - 1)) {
                                LogLine logLine = new LogLine();
                                logLine.setDate(matcher.group(1));
                                logLine.setLogLevel(logLevel);
                                logLine.setText(matcher.group(3));
                                logs.add(logLine);
                            }

                            logResponse.setHasNext(iterator.hasNext());

                            // Do not read more than needed. Break foreach.
                            if (counter > (logResponse.getFrom() + logResponse.getSize() - 1)) {
                                break;
                            }

                            counter = counter + 1;
                        }
                    }

                    logResponse.setLogLines(logs);

                } catch (NoSuchFileException nsfe) {
                    LOG.error("No such file in path: " + logFilePath);
                    logResponse.setExceptionMessage("No such file in path: " + logFilePath);
                } catch (IOException ioe) {
                    LOG.error("Error reading file. ", ioe);
                    logResponse.setExceptionMessage("Error reading file. Look at logs.");
                } catch (Exception e) {
                    LOG.error("Error while reading file. ", e);
                    logResponse.setExceptionMessage("Error while reading file. Look at logs.");
                }

            }

        };

        thread.run();

        return logResponse;
    }

}
