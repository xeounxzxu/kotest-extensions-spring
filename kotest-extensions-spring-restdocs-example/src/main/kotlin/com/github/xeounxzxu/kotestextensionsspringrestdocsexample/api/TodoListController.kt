package com.github.xeounxzxu.kotestextensionsspringrestdocsexample.api

import com.github.xeounxzxu.kotestextensionsspringrestdocsexample.api.dto.TodoListOneResponse
import com.github.xeounxzxu.kotestextensionsspringrestdocsexample.api.dto.TodoListResponse
import com.github.xeounxzxu.kotestextensionsspringrestdocsexample.api.dto.TodoListUpdateTitleRequest
import com.github.xeounxzxu.kotestextensionsspringrestdocsexample.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/todo-list")
class TodoListController(
    private val todoService: TodoService,
) {
    @GetMapping("/{todoId}")
    fun get(
        @PathVariable todoId: Long,
    ): TodoListOneResponse {
        return todoService.getOne(todoId).run {
            TodoListOneResponse.from(this)
        }
    }

    @GetMapping
    fun getList(): TodoListResponse {
        return todoService.getAll()
            .run {
                TodoListResponse.from(
                    todoList = this,
                )
            }
    }

    @DeleteMapping("/{todoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun remove(
        @PathVariable todoId: Long,
    ) {
        todoService.remove(todoId)
    }

    @PatchMapping("/{todoId}")
    fun updateTitle(
        @PathVariable todoId: Long,
        @RequestBody request: TodoListUpdateTitleRequest,
    ): TodoListOneResponse {
        return todoService.updateTitle(
            id = todoId,
            title = request.title,
        )
            .run {
                TodoListOneResponse.from(this)
            }
    }

    @PostMapping("/bulk-upload")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun bulkUpdateTodoList(size: Int) {
        todoService.bulkUpdate(size)
    }
}
