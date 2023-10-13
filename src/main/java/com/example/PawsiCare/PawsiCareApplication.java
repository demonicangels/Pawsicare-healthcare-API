package com.example.PawsiCare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PawsiCareApplication {

	public static void main(String[] args) {
		SpringApplication.run(PawsiCareApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsconfigurer(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry corsRegistry){
				corsRegistry.addMapping("/doctors").allowedOrigins("http://localhost:5173");
				corsRegistry.addMapping("/login").allowedOrigins("http://localhost:5173");
			}
		};

	}

}
