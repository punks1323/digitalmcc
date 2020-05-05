package com.cluster.digital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DigitalClusterOperationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalClusterOperationsApplication.class, args);
    }

}
