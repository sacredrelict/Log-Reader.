package com.sacredrelict.logreader.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sacredrelict.logreader.core.component.LogReaderComponent;
import com.sacredrelict.logreader.core.service.LogReaderService;
import com.sacredrelict.logreader.web.LogReaderApplicationTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for ApplicationController.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {LogReaderApplicationTest.class})
@AutoConfigureMockMvc
public class ApplicationControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ApplicationController applicationController;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private LogReaderService logReaderService;

    @MockBean
    private LogReaderComponent logReaderComponent;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(applicationController).build();
    }

    @Test
    public void getLogFileDataTest_get_noException() throws Exception {
        this.mockMvc.perform(get("/log"))
                .andExpect(status().isOk());
    }

    @Test
    public void getLogFileLevelInfoCountTest_get_noException() throws Exception {
        this.mockMvc.perform(get("/log/level/info/count"))
                .andExpect(status().isOk());
    }

    @Test
    public void getLogFileLevelWarningCountTest_get_noException() throws Exception {
        this.mockMvc.perform(get("/log/level/warning/count"))
                .andExpect(status().isOk());
    }

    @Test
    public void getLogFileLevelErrorCountTest_get_noException() throws Exception {
        this.mockMvc.perform(get("/log/level/error/count"))
                .andExpect(status().isOk());
    }

}
