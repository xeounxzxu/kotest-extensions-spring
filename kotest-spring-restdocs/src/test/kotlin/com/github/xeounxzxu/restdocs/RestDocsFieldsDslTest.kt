package com.github.xeounxzxu.restdocs

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.SubsectionDescriptor

class RestDocsFieldsDslTest :
    FunSpec({

        test("creates request field descriptors via DSL") {
            val descriptors =
                restDocsFieldDescriptors {
                    "code" type JsonFieldType.STRING means "응답 코드"
                    "message" type JsonFieldType.STRING optionalMeans "응답 메시지"
                    "internal" type JsonFieldType.STRING ignoredMeans "내부 값"
                    "data" subsection "응답 데이터"
                }
            descriptors.shouldHaveSize(4)

            descriptors[0].path shouldBe "code"
            descriptors[0].type shouldBe JsonFieldType.STRING
            descriptors[0].description shouldBe "응답 코드"
            descriptors[0].isOptional shouldBe false

            descriptors[1].path shouldBe "message"
            descriptors[1].isOptional shouldBe true

            descriptors[2].isIgnored shouldBe true

            val subsection = descriptors[3] as SubsectionDescriptor
            subsection.path shouldBe "data"
        }

        test("creates response descriptors with relaxed variant") {
            val descriptors =
                restDocsFieldDescriptors {
                    "data" optionalSubsection "응답 데이터"
                }

            descriptors.shouldHaveSize(1)
            val subsection = descriptors[0] as SubsectionDescriptor
            subsection.isOptional shouldBe true
            subsection.path shouldBe "data"
        }
    })
