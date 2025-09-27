package com.github.xeounxzxu.sample.todo.service

import com.github.xeounxzxu.sample.todo.dto.CreateTodoRequest
import com.github.xeounxzxu.sample.todo.dto.TodoResponse
import com.github.xeounxzxu.sample.todo.model.Todo
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Service
class TodoService {
    private val idGenerator = AtomicLong(0)
    private val todos = ConcurrentHashMap<Long, Todo>()

    fun getTodos(): List<TodoResponse> =
        todos
            .values
            .sortedBy { it.id }
            .map { it.toResponse() }

    fun createTodo(request: CreateTodoRequest): TodoResponse {
        val id = idGenerator.incrementAndGet()
        val todo = Todo(id = id, title = request.title, completed = false)
        todos[id] = todo
        return todo.toResponse()
    }

    fun clearAll() {
        todos.clear()
        idGenerator.set(0)
    }

    private fun Todo.toResponse(): TodoResponse =
        TodoResponse(
            id = id,
            title = title,
            completed = completed,
        )
}
