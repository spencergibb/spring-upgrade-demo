package com.example.demospringupgrade.todo;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
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
	private final Tracer tracer;

	public TodoApplication(TodoRepository todoRepository, Tracer tracer) {
		this.todoRepository = todoRepository;
		this.tracer = tracer;
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
		Span newSpan = this.tracer.nextSpan().name("longRunningWork");
		try (Tracer.SpanInScope ws = this.tracer.withSpan(newSpan.start())) {
			log.info(LogMessage.format("Doing long work"));
			newSpan.tag("workType", "longWork");
			newSpan.event("longRunningWorkDone");
		}
		finally {
			newSpan.end();
		}
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
