package com.github.xeounxzxu.kotestextensionsspringrestdocsexample.api.dto

data class TodoItem(
    val id: Long,
    val title: String,
)

typealias TodoItemWithIndex = Pair<Int, TodoItem>
