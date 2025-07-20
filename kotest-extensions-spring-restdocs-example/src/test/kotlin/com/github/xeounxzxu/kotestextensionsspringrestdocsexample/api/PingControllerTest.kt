package com.github.xeounxzxu.kotestextensionsspringrestdocsexample.api

import com.github.xeounxzxu.SpringRestDocsExtension
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.payload.ResponseFieldsSnippet
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders


@WebMvcTest(PingController::class)
class PingControllerTest(
    @Autowired val mockMvc: MockMvc
) : FunSpec() {

    init {

        extensions(SpringExtension, SpringRestDocsExtension)

        val mockMvc = MockMvcBuilders.standaloneSetup(PingController())
            .build()

        context("GET /api/ping") {

            test("정상적으로 ping 을 보낸다") {
                mockMvc.get("/api/ping")
                    .andDo { print() }
                    .andExpect { status { isOk() } }
            }
        }
    }

    companion object {
        fun getPingResponse(): ResponseFieldsSnippet {
            return responseFields(
                fieldWithPath("status")
                    .type(JsonFieldType.STRING).description("상태"),
            )
        }
    }
}

