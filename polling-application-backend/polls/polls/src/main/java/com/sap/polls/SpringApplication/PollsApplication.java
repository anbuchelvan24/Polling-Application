package com.sap.polls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class PollsApplication {

	public static void main(String[] args) {

		FirebaseConfig.firebaseInit();

		SpringApplication.run(PollsApplication.class, args);
		System.out.println("main");
	}

	@Configuration
	@EnableWebMvc
	public class WebConfig implements WebMvcConfigurer {

		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
					.allowedOrigins("http://localhost:5173") // Add the origins you want to allow
					.allowedMethods("GET", "POST", "PUT", "DELETE")
					.allowCredentials(true);
		}
	}
}
