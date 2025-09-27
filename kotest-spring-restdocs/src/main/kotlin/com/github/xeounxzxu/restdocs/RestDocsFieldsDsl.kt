package com.github.xeounxzxu.restdocs

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath
import org.springframework.restdocs.payload.RequestFieldsSnippet
import org.springframework.restdocs.payload.ResponseFieldsSnippet
import org.springframework.restdocs.payload.SubsectionDescriptor

class RestDocsFieldDsl {
    private val descriptors = mutableListOf<FieldDescriptor>()

    private fun addFieldDescriptor(
        path: String,
        type: JsonFieldType,
        description: String,
        optional: Boolean,
        ignored: Boolean,
    ): FieldDescriptor {
        val descriptor =
            fieldWithPath(path)
                .type(type)
                .description(description)

        if (optional) {
            descriptor.optional()
        }
        if (ignored) {
            descriptor.ignored()
        }

        descriptors += descriptor
        return descriptor
    }

    private fun addSubsectionDescriptor(
        path: String,
        description: String,
        optional: Boolean,
    ): SubsectionDescriptor {
        val descriptor = subsectionWithPath(path)
        require(descriptor is SubsectionDescriptor) {
            "subsectionWithPath should produce a SubsectionDescriptor"
        }

        descriptor.description(description)

        if (optional) {
            descriptor.optional()
        }

        descriptors += descriptor
        return descriptor
    }

    inner class FieldSpec internal constructor(
        private val path: String,
        private val type: JsonFieldType,
        private val optional: Boolean = false,
        private val ignored: Boolean = false,
    ) {
        infix fun means(description: String): FieldDescriptor = means(description) {}

        fun means(
            description: String,
            block: FieldDescriptor.() -> Unit,
        ): FieldDescriptor {
            return addFieldDescriptor(path, type, description, optional, ignored).apply(block)
        }

        fun optional(): FieldSpec = FieldSpec(path, type, optional = true, ignored = ignored)

        fun ignored(): FieldSpec = FieldSpec(path, type, optional = optional, ignored = true)

        infix fun optionalMeans(description: String): FieldDescriptor = optional().means(description)

        fun optionalMeans(
            description: String,
            block: FieldDescriptor.() -> Unit,
        ): FieldDescriptor = optional().means(description, block)

        infix fun ignoredMeans(description: String): FieldDescriptor = ignored().means(description)

        fun ignoredMeans(
            description: String,
            block: FieldDescriptor.() -> Unit,
        ): FieldDescriptor = ignored().means(description, block)
    }

    infix fun String.type(jsonType: JsonFieldType): FieldSpec = FieldSpec(this, jsonType)

    fun field(
        path: String,
        type: JsonFieldType,
        description: String,
        optional: Boolean = false,
        block: FieldDescriptor.() -> Unit = {},
    ) {
        FieldSpec(path, type, optional = optional).means(description, block)
    }

    fun optionalField(
        path: String,
        type: JsonFieldType,
        description: String,
        block: FieldDescriptor.() -> Unit = {},
    ) {
        FieldSpec(path, type, optional = true).means(description, block)
    }

    fun ignoredField(
        path: String,
        type: JsonFieldType,
        description: String,
        block: FieldDescriptor.() -> Unit = {},
    ) {
        FieldSpec(path, type, ignored = true).means(description, block)
    }

    fun subsection(
        path: String,
        description: String,
        optional: Boolean = false,
        block: SubsectionDescriptor.() -> Unit = {},
    ) {
        addSubsectionDescriptor(path, description, optional).apply(block)
    }

    fun optionalSubsection(
        path: String,
        description: String,
        block: SubsectionDescriptor.() -> Unit = {},
    ) {
        subsection(path, description, optional = true, block = block)
    }

    infix fun String.subsection(description: String): SubsectionDescriptor = addSubsectionDescriptor(this, description, optional = false)

    infix fun String.optionalSubsection(description: String): SubsectionDescriptor =
        addSubsectionDescriptor(this, description, optional = true)

    fun build(): List<FieldDescriptor> = descriptors
}

internal fun restDocsFieldDescriptors(block: RestDocsFieldDsl.() -> Unit): List<FieldDescriptor> {
    val builder = RestDocsFieldDsl()
    builder.block()
    return builder.build()
}

fun requestFields(block: RestDocsFieldDsl.() -> Unit): RequestFieldsSnippet {
    val descriptors = restDocsFieldDescriptors(block)
    return PayloadDocumentation.requestFields(*descriptors.toTypedArray())
}

fun relaxedRequestFields(block: RestDocsFieldDsl.() -> Unit): RequestFieldsSnippet {
    val descriptors = restDocsFieldDescriptors(block)
    return PayloadDocumentation.relaxedRequestFields(*descriptors.toTypedArray())
}

fun responseFields(block: RestDocsFieldDsl.() -> Unit): ResponseFieldsSnippet {
    val descriptors = restDocsFieldDescriptors(block)
    return PayloadDocumentation.responseFields(*descriptors.toTypedArray())
}

fun relaxedResponseFields(block: RestDocsFieldDsl.() -> Unit): ResponseFieldsSnippet {
    val descriptors = restDocsFieldDescriptors(block)
    return PayloadDocumentation.relaxedResponseFields(*descriptors.toTypedArray())
}
