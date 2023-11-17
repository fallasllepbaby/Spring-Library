package com.example.libraryservice;

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
                title = "Library-Service REST API",
                version = "1.0.0",
                description = "1. Добавление новой записи;\n" +
                        "2. Редактирование существующей записи;\n" +
                        "3. Удаление существующей записи о книге;",
                contact = @Contact(
                        name = "Daniil Laparevich",
                        email = "danik.laparevich@bk.ru"
                )
        )
)
public class LibraryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryServiceApplication.class, args);
    }

}
