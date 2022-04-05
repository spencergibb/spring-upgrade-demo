package com.example.demospringupgrade.todo;

import java.util.Objects;

import jakarta.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;

public class Todo {
    @Id
    private Long id;
    @NotBlank
    private String contents;
    private boolean done;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return done == todo.done && Objects.equals(id, todo.id) && Objects.equals(contents, todo.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contents, done);
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", contents='" + contents + '\'' +
                ", done='" + done + '\'' +
                '}';
    }
}
