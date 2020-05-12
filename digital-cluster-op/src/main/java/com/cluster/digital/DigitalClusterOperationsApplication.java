package com.cluster.digital;

import com.cluster.digital.database.repo.DairyRepository;
import com.cluster.digital.database.repo.RouteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@Slf4j
public class DigitalClusterOperationsApplication implements ApplicationRunner {

    @Autowired
    DairyRepository dairyRepository;

    @Autowired
    RouteRepository routeRepository;

    public static void main(String[] args) {
        SpringApplication.run(DigitalClusterOperationsApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info(dairyRepository.findDairyIdByClusterId("CL-001").toString());
        log.info(routeRepository.findRouteIdByDairyId("DA-001").toString());
    }
}
