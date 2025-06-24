package com.smartfusiontech.cards;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication

@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Cards microservice REST API Documentation",
				description = "https://smartfusiontech.com/ Cards microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "AbdulRasheed",
						email = "smartfusiontech.com",
						url = "https://smartfusiontech.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://smartfusiontech.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "smartfusiontech Cards microservice REST API Documentation",
				url = "https://smartfusiontech.com/"
		)
)
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
		List<String> names = List.of("ABDUL","Rasheed");
		List<String> output = names.stream().map(String::toUpperCase).filter(name->name.startsWith("A")).collect(Collectors.toList());
   System.out.println(output);
	}
}
