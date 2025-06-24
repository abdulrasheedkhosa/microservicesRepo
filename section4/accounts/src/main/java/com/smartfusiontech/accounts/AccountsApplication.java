package com.smartfusiontech.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts microservice REST API Documentation",
				description = "Accounts microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "AbdulRasheed",
						email = "rasheed.khosa@gmail.com",
						url = "https://smartfusiontech.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://smartfusiontech.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description =  "Smartfusiontech Accounts microservice REST API Documentation",
				url = "https://smartfusiontech.com"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
