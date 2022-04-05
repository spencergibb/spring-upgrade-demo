package com.example.demospringupgrade.todo;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.log.LogMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class TodoApplication {
	private static final Log log = LogFactory.getLog(TodoApplication.class);

	private final TodoRepository todoRepository;
	private final ObservationRegistry registry;

	public TodoApplication(TodoRepository todoRepository, ObservationRegistry registry) {
		this.todoRepository = todoRepository;
		this.registry = registry;
	}

	@PostConstruct
	void init() {
		log.info(LogMessage.format("Found %d todos", todoRepository.count()));
	}

	@GetMapping("/todos")
	Iterable<Todo> findAll() {
		longRunningWork();
		return todoRepository.findAll();
	}

	void longRunningWork() {
		Observation.createNotStarted("long-running-work", registry)
				.highCardinalityTag("work-type", "longWork")
				.observe(() -> log.info(LogMessage.format("Doing long work")));
	}

	@GetMapping("/todos/{id}")
	ResponseEntity<Todo> find(@PathVariable Long id, HttpServletRequest request) {
		log.info(LogMessage.format("Request URI %s", request.getRequestURI()));
		return todoRepository.findById(id).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}

}
