package com.example.securityservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@OpenAPIDefinition(
		info = @Info(
				title = "Security-Service REST API",
				version = "1.0.0",
				description = "1. Регистрация нового пользователя;\n" +
						"2. Получения токена для существующего пользователя;\n" +
						"3. Валидация полученного токена;",
				contact = @Contact(
						name = "Daniil Laparevich",
						email = "danik.laparevich@bk.ru"
				)
		)
)
public class SecurityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityServiceApplication.class, args);
	}

}
