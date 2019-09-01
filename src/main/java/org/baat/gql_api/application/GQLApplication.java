package org.baat.gql_api.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.baat")
public class GQLApplication {
    public static void main(String[] args) {
        SpringApplication.run(GQLApplication.class, args);
    }
}
