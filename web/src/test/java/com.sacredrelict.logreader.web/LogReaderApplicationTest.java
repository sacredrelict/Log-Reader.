package com.sacredrelict.logreader.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.TestPropertySource;

@SpringBootApplication
@TestPropertySource(locations="classpath:application-test.yml")
public class LogReaderApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(LogReaderApplicationTest.class, args);
    }

}
