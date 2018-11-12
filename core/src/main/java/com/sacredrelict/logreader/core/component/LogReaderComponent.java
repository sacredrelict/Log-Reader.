package com.sacredrelict.logreader.core.component;

import com.sacredrelict.logreader.core.enums.LogLevel;
import com.sacredrelict.logreader.core.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Component which read log file asynchronously.
 */
@Component
public class LogReaderComponent implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(LogReaderComponent.class);

    private volatile int infoSize = 0;
    private volatile int warningSize = 0;
    private volatile int errorSize = 0;

    private static final Pattern PATTERN = Pattern.compile(Constants.logEntryPattern);

    @Value("${log.file.path}")
    private String logFilePath;

    /**
     * Run method, that calculate levels count in log file.
     * @param applicationArguments - optional.
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        calculateLevelsCount();
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

    /**
     * Calculate levels count in log file.
     * Than thread will read all lines, it waits new lines would be added.
     */
    private void calculateLevelsCount() {
        Thread thread = new Thread(){

            @Override
            public void run(){
                try {
                    // Read lines from files.
                    Stream<String> stream = Files.lines(Paths.get(logFilePath), StandardCharsets.UTF_8);
                    Iterator<String> iterator = stream.iterator();

                    LOG.info("Start reading log file levels count.");
                    while (true) {
                        // If new lines not found, wait.
                        if (!iterator.hasNext()) {
                            sleep(5000);
                        } else {
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

                                    // Write counters for each level.
                                    if (logLevel == LogLevel.INFO) {
                                        infoSize++;
                                    }
                                    if (logLevel == LogLevel.WARNING) {
                                        warningSize++;
                                    }
                                    if (logLevel == LogLevel.ERROR) {
                                        errorSize++;
                                    }
                                }
                            }
                        }
                    }

                } catch (NoSuchFileException nsfe) {
                    LOG.error("No such file in path: " + logFilePath);
                } catch (IOException ioe) {
                    LOG.error("Error reading file. ", ioe);
                } catch (Exception e) {
                    LOG.error("Error while reading file. ", e);
                }
            }

        };

        thread.run();
    }

}
