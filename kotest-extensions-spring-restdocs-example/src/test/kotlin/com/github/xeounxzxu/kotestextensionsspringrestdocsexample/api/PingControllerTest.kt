package com.github.xeounxzxu.kotestextensionsspringrestdocsexample.api

import com.github.xeounxzxu.SpringRestDocsExtension
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.payload.ResponseFieldsSnippet
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get


@WebMvcTest(PingController::class)
class PingControllerTest(
    @Autowired val mockMvc: MockMvc
) : FunSpec() {

    init {

        extensions(SpringExtension, SpringRestDocsExtension)

        context("GET /api/ping") {

            test("정상적으로 ping 을 보낸다") {
                mockMvc.get("/api/ping") {
                    contentType = MediaType.APPLICATION_JSON
                    accept = MediaType.APPLICATION_JSON
                }
                    .andDo { print() }
                    .andExpect {
                        status { isOk() }
                    }.andDo {
                        document(
                            "ping-controller-test",
                            getPingResponse()
                        )
                    }
            }
        }

//            test("정상적으로 ping 을 보낸다") {
//
//                mockMvc.perform(get("/api/ping"))
//                    .andExpect(status().isOk)
//                    .andDo(document("hello-response"))
//
////                mockMvc.get("/api/ping")
////                    .andDo { print() }
////                    .andExpect { status { isOk() } }
////                    .andDo(document("hello-response", getPingResponse()))
//            }
//        }
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

