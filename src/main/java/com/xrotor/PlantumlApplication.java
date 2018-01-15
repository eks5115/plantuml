package com.xrotor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chensen
 */
@SpringBootApplication
public class PlantumlApplication {

	public static void main(String[] args) {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow","|{}");
		SpringApplication.run(PlantumlApplication.class, args);
	}
}
