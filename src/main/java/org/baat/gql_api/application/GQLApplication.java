package org.baat.gql_api.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("org.baat.gql_api.repository")
@EntityScan("org.baat.gql_api.repository.entity")
@ComponentScan("org.baat")
public class GQLApplication {
    public static void main(String[] args) {
        SpringApplication.run(GQLApplication.class, args);
    }
}
