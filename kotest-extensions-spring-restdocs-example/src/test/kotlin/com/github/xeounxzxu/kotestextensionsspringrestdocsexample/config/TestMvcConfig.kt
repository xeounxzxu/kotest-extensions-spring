package com.github.xeounxzxu.kotestextensionsspringrestdocsexample.config

import java.nio.charset.StandardCharsets
import java.util.logging.Filter
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder
import org.springframework.web.context.WebApplicationContext

//@TestConfiguration
//class TestMvcConfig {
//
//    @Bean
//    fun mockMvc(context: WebApplicationContext, filters: List<Filter>): MockMvc {
////        return MockMvcBuilders.webAppContextSetup(context)
////            // 설정한 Filter들 MockMvc에도 설정(빈으로 등록되지 않은 Filter라면 직접 생성해서 세팅 필요)
////            .addFilters<DefaultMockMvcBuilder>(*filters.toTypedArray())
////            // 응답 한글 깨짐 방지 설정
////            .defaultResponseCharacterEncoding<DefaultMockMvcBuilder>(StandardCharsets.UTF_8)
////            .build()
//
//        val mockMvc =
//            MockMvcBuilders.standaloneSetup(controller)
//                .apply<StandaloneMockMvcBuilder>(
//                    MockMvcRestDocumentation.documentationConfiguration(
//                        manualRestDocumentation(),
//                    ),
//                )
//                .setMessageConverters(
//                    httpMessageConverter,
//                ).alwaysDo<StandaloneMockMvcBuilder>(MockMvcResultHandlers.print())
//                .build()
//
//        return RestAssuredMockMvc.given().mockMvc(mockMvc)
//
//    }
//}
