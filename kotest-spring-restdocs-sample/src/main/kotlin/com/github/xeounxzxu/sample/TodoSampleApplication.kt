package com.github.xeounxzxu.sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TodoSampleApplication

fun main(args: Array<String>) {
    runApplication<TodoSampleApplication>(*args)
}
