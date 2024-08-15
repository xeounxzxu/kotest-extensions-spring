package com.github.xeounxzxu.kotestextensionsspringrestdocsexample.config

import com.github.xeounxzxu.kotestextensionsspringrestdocsexample.api.dto.TodoItem
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TodoMockConfig {
    @Bean
    fun mockData(): MutableList<TodoItem> {
        return (1..100).map {
            TodoItem(
                id = it.toLong(),
                title = "테스트_$it",
            )
        }.toMutableList()
    }
}
