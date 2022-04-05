package com.example.demospringupgrade.common;

import java.util.List;

import io.micrometer.observation.ObservationRegistry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.log.LogMessage;

@AutoConfiguration
public class CustomAutoConfiguration {

    private static final Log log = LogFactory.getLog(CustomAutoConfiguration.class);

    @Bean
    CommandLineRunner customCommandLineRunner() {
        return args -> {
            var items = List.of("A", "B", "C");
            log.info(LogMessage.format("From CustomAutoConfiguration %s", items));
        };
    }

    // TODO: Not needed after Spring Boot 3.0.0-M3
    @Bean
    ObservationRegistry myObservationRegistry() {
        return ObservationRegistry.NOOP;
    }
}
