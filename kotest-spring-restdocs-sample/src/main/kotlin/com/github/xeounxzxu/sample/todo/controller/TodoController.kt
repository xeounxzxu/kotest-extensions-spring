package com.github.xeounxzxu.sample.todo.controller

import com.github.xeounxzxu.sample.todo.dto.CreateTodoRequest
import com.github.xeounxzxu.sample.todo.dto.TodoResponse
import com.github.xeounxzxu.sample.todo.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/todos")
class TodoController(
    private val todoService: TodoService,
) {
    @GetMapping
    fun getTodos(): List<TodoResponse> = todoService.getTodos()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTodo(
        @RequestBody request: CreateTodoRequest,
    ): TodoResponse = todoService.createTodo(request)
}
