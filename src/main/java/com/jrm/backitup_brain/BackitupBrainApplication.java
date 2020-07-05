package com.jrm.backitup_brain;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class BackitupBrainApplication { //extends SpringBootServletInitializer {

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder app) {
//		return	app.sources(BackitupBrainApplication.class);
//	}

	public static void main(String[] args) {
		SpringApplication.run(BackitupBrainApplication.class, args);
	}

}
