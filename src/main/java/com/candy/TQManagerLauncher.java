package com.candy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@PropertySource(value = { "file:conf/bdp.properties" }, encoding = "utf-8")
@EnableScheduling
public class TQManagerLauncher {

	public static void main(String[] args) {
		SpringApplication.run(TQManagerLauncher.class, args);
	}
}
