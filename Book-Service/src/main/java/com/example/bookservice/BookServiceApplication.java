package com.example.bookservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(
				title = "Book-Service REST API",
				version = "1.0.0",
				description = "1. Получение списка всех книг;\n" +
						"2. Получение определённой книги по её Id;\n" +
						"3. Получение книги по её ISBN;\n" +
						"4. Добавление новой книги;\n" +
						"5. Изменение информации о существующей книге;\n" +
						"6. Удаление книги;\n" +
						"7. Получение списка всех свободных книг;",
				contact = @Contact(
						name = "Daniil Laparevich",
						email = "danik.laparevich@bk.ru"
				)
		)
)
public class BookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}

}
