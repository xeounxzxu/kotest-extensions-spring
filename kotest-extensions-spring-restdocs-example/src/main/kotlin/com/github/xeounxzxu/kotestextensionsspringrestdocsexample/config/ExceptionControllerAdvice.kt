package com.github.xeounxzxu.kotestextensionsspringrestdocsexample.config

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

private val logger = KotlinLogging.logger {}

@RestControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun error(ex: Exception): ErrorResponse {
        logger.error { ex }
        return ErrorResponse(
            "001",
            "알수없는에러입니다."
        )
    }

    @ExceptionHandler(
        value = [
            IllegalStateException::class,
            IllegalArgumentException::class
        ]
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun error(ex: RuntimeException): ErrorResponse {
        logger.error { ex }
        return ErrorResponse(
            "002",
            "잘못된요청입니다."
        )
    }
}

data class ErrorResponse(val code: String, val message: String)
