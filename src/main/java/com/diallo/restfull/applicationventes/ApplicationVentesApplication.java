package com.diallo.restfull.applicationventes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ApplicationVentesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationVentesApplication.class, args);
	}
	
	/*@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/users/**").allowedOrigins("http://localhost:4200");
			}
		};
	}*/

}
