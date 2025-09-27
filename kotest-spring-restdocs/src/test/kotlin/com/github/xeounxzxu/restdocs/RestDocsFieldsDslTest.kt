package com.github.xeounxzxu.restdocs

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.SubsectionDescriptor
import org.springframework.restdocs.snippet.Snippet

class RestDocsFieldsDslTest : FunSpec({

    test("creates request field descriptors via DSL") {
        val snippet = requestFields {
            "code" type JsonFieldType.STRING means "응답 코드"
            "message" type JsonFieldType.STRING optionalMeans "응답 메시지"
            "internal" type JsonFieldType.STRING ignoredMeans "내부 값"
            "data" subsection "응답 데이터"
        }

        val descriptors = snippet.descriptors()
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
        val descriptors = relaxedResponseFields {
            "data" optionalSubsection "응답 데이터"
        }.descriptors()

        descriptors.shouldHaveSize(1)
        descriptors[0].isOptional shouldBe true
    }
})

private fun Snippet.descriptors(): List<FieldDescriptor> {
    val field = this::class.java.getDeclaredField("descriptors")
    field.isAccessible = true
    @Suppress("UNCHECKED_CAST")
    return field.get(this) as List<FieldDescriptor>
}
