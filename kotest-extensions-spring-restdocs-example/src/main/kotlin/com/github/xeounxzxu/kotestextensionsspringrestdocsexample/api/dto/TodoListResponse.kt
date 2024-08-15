package com.github.xeounxzxu.kotestextensionsspringrestdocsexample.api.dto

data class TodoListOneResponse(
    val todoItem: TodoItem
) {
    companion object {
        fun from(
            todoItem: TodoItem
        ): TodoListOneResponse {
            return TodoListOneResponse(
                todoItem = todoItem
            )
        }
    }
}

data class TodoListResponse(
    val todoList: List<TodoItem>
) {
    companion object {
        fun from(
            todoList: List<TodoItem>
        ): TodoListResponse {
            return TodoListResponse(
                todoList = todoList
            )
        }
    }
}
