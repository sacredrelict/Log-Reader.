package com.sacredrelict.logreader.core.service;

import com.sacredrelict.logreader.core.component.LogReaderComponent;
import com.sacredrelict.logreader.core.dto.LogResponse;
import com.sacredrelict.logreader.core.enums.LogLevel;
import com.sacredrelict.logreader.web.LogReaderApplicationTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for LogReaderService.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LogReaderApplicationTest.class)
public class LogReaderServiceTest {

    @Autowired
    @InjectMocks
    private LogReaderService logReaderService;

    @MockBean
    private LogReaderComponent logReaderComponent;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getLogsTest_readFile_noException() throws Exception {
        LogResponse logResponse = logReaderService.getLogs(1, 4);
        assertThat(logResponse).isNotNull();
        assertThat(logResponse.getLogLines()).isNotEmpty();
        assertThat(logResponse.getLogLines().get(0).getLogLevel()).isEqualTo(LogLevel.INFO);
    }

}
