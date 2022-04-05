package com.example.demospringupgrade.common;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.log.LogMessage;

@Configuration(proxyBeanMethods = false)
public class CustomAutoConfiguration {

    private static final Log log = LogFactory.getLog(CustomAutoConfiguration.class);

    @Bean
    CommandLineRunner customCommandLineRunner() {
        return args -> {
            List<String> items = Arrays.asList("A", "B", "C");
            log.info(LogMessage.format("From CustomAutoConfiguration %s", items));
        };
    }
}
