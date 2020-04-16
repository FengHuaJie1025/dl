package com.dl.mjapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@EnableCaching
@EnableAsync
@EnableJpaRepositories(basePackages = "com.dl.common.repository")
@EntityScan(basePackages = "com.dl.common.model.entity")
public class mjappApplication {

    public static void main(String[] args) {
        SpringApplication.run(mjappApplication.class, args);
    }

}
