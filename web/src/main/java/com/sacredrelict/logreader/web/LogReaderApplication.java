package com.sacredrelict.logreader.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.yml")
@ComponentScan(basePackages = {
		"com.sacredrelict.logreader.web",
		"com.sacredrelict.logreader.core",
		"com.sacredrelict.logreader.core.component"})
public class LogReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogReaderApplication.class, args);
	}

}
