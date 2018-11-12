package com.sacredrelict.logreader.core.service;

import com.sacredrelict.logreader.core.dto.LogResponse;

/**
 * Interface for LogReaderServiceImpl.
 */
public interface LogReaderService {

    LogResponse getLogs(Integer from, Integer size);

}
