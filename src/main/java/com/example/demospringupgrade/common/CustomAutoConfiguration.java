package com.example.demospringupgrade.common;

import java.util.List;

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
}
