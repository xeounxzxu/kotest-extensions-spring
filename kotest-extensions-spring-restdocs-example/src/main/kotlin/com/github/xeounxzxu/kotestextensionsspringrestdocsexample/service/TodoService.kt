package com.github.xeounxzxu.kotestextensionsspringrestdocsexample.service

import com.github.xeounxzxu.kotestextensionsspringrestdocsexample.api.dto.TodoItem
import com.github.xeounxzxu.kotestextensionsspringrestdocsexample.api.dto.TodoItemWithIndex
import org.springframework.stereotype.Service

@Service
class TodoService(
    private val mockData: MutableList<TodoItem>
) {

    fun getOne(id: Long): TodoItem {
        return checkNotNull(mockData.find {
            it.id == id
        })
    }

    fun getAll(): List<TodoItem> {
        return mockData
    }

    fun remove(id: Long) {

        val findTodo = checkNotNull(mockData.find { id == it.id })

        mockData.remove(findTodo)
    }

    fun updateTitle(id: Long, title: String): TodoItem {

        val (index, findData) = checkNotNull(mockData.withIndex().find { it.value.id == id }).let {
            (it.index to it.value) as TodoItemWithIndex
        }

        val update = findData.copy(title = title)

        mockData.add(index, update)

        return update
    }

    fun bulkUpdate(size: Int) {

        val maxId = mockData.maxOf { it.id } + 1

        val bulkUpdateList = (maxId..maxId + (size - 1)).map {
            TodoItem(
                id = it,
                title = "테스트_$it"
            )
        }

        mockData.addAll(bulkUpdateList)
    }
}
