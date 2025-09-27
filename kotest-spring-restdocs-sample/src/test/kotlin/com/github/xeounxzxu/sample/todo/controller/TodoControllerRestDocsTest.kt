package com.github.xeounxzxu.sample.todo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.xeounxzxu.SpringRestDocsExtension
import com.github.xeounxzxu.restdocs.requestFields
import com.github.xeounxzxu.restdocs.responseFields
import com.github.xeounxzxu.sample.todo.dto.CreateTodoRequest
import com.github.xeounxzxu.sample.todo.service.TodoService
import com.github.xeounxzxu.withManualRestDocumentation
import io.kotest.core.spec.style.FunSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerRestDocsTest
    @Autowired
    constructor(
        private val context: WebApplicationContext,
        private val objectMapper: ObjectMapper,
        private val todoService: TodoService,
    ) : FunSpec({

            extensions(SpringRestDocsExtension)

            lateinit var mockMvc: MockMvc

            beforeTest {
                todoService.clearAll()
                mockMvc =
                    withManualRestDocumentation {
                        MockMvcBuilders
                            .webAppContextSetup(context)
                            .apply<DefaultMockMvcBuilder>(
                                MockMvcRestDocumentation.documentationConfiguration(this),
                            ).build()
                    }
            }

            test("documents fetching all todos") {
                todoService.createTodo(CreateTodoRequest("Buy milk"))
                todoService.createTodo(CreateTodoRequest("Write docs"))

                mockMvc
                    .perform(
                        RestDocumentationRequestBuilders
                            .get("/api/todos")
                            .accept(MediaType.APPLICATION_JSON),
                    ).andExpect(status().isOk)
                    .andDo(
                        MockMvcRestDocumentation.document(
                            "todos-list",
                            responseFields {
                                "[].id" type JsonFieldType.NUMBER means "Todo identifier"
                                "[].title" type JsonFieldType.STRING means "Todo title"
                                "[].completed" type JsonFieldType.BOOLEAN means "Completion flag"
                            }
                        )
                    )
            }

            test("documents creating a todo") {
                val request = CreateTodoRequest(title = "Learn Kotest")

                mockMvc
                    .perform(
                        RestDocumentationRequestBuilders
                            .post("/api/todos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)),
                    ).andExpect(status().isCreated)
                    .andExpect(jsonPath("$.title").value(request.title))
                    .andExpect(jsonPath("$.completed").value(false))
                    .andDo(
                        MockMvcRestDocumentation.document(
                            "todos-create",
                            requestFields {
                                "title" type JsonFieldType.STRING means "Todo title"
                            },
                            responseFields {
                                "id" type JsonFieldType.NUMBER means "Generated todo identifier"
                                "title" type JsonFieldType.STRING means "Todo title"
                                "completed" type JsonFieldType.BOOLEAN means "Completion flag"
                            }
                        )
                    )
            }
        })
