package ru.hogwarts.school;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class UniversityApplication{
    public static void main(String[] args) {
        SpringApplication.run(UniversityApplication.class, args);
    }
}
