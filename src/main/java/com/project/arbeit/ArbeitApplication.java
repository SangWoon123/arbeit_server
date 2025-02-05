package com.project.arbeit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ArbeitApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArbeitApplication.class, args);
    }

}
