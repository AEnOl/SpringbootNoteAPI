package com.olsen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main entry point of the Spring Application
 *
 * @author Anders Engen Olsen
 */

/* SpringBootApplication includes the following annotations
 * @Configuration: Automatically bootstrapped by Spring
 * @EnableAutoConfiguration: Automatically configure application based on dependencies in pom.xml
 * @ComponentScan: Scanning and bootstrapping other components in this package and sub-packages.
 */
@SpringBootApplication
// JPA annotation, needed for automatic population of certain DB-columns.
@EnableJpaAuditing
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
