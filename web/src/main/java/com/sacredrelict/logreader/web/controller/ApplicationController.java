package com.sacredrelict.logreader.web.controller;

import com.sacredrelict.logreader.core.component.LogReaderComponent;
import com.sacredrelict.logreader.core.service.LogReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Base controller.
 */
@RestController
@RequestMapping(value = "")
public class ApplicationController {

    @Autowired
    private LogReaderService logReaderService;

    @Autowired
    private LogReaderComponent logReaderComponent;

    @RequestMapping(path = "/log", method = RequestMethod.GET)
    public ResponseEntity<?> getLogFileData(@RequestParam(required = false) Integer from,
                                            @RequestParam(required = false) Integer size) {
        return ResponseEntity.ok(logReaderService.getLogs(from, size));
    }

    @RequestMapping(path = "/log/level/info/count", method = RequestMethod.GET)
    public ResponseEntity<?> getLogFileLevelInfoCount() {
        return ResponseEntity.ok(logReaderComponent.getInfoSize());
    }

    @RequestMapping(path = "/log/level/warning/count", method = RequestMethod.GET)
    public ResponseEntity<?> getLogFileLevelWarningCount() {
        return ResponseEntity.ok(logReaderComponent.getWarningSize());
    }

    @RequestMapping(path = "/log/level/error/count", method = RequestMethod.GET)
    public ResponseEntity<?> getLogFileLevelErrorCount() {
        return ResponseEntity.ok(logReaderComponent.getErrorSize());
    }

}
