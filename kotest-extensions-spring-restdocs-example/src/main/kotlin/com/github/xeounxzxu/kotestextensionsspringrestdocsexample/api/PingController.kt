package com.github.xeounxzxu.kotestextensionsspringrestdocsexample.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PingController {
    @GetMapping("/api/ping")
    fun getPing(): Map<String, String> {
        return mapOf(
            "status" to "pong",
        )
    }
}
