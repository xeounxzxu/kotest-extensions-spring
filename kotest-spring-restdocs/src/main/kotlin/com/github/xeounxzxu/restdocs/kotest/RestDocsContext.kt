package com.github.xeounxzxu.restdocs.kotest

import org.springframework.restdocs.ManualRestDocumentation
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class RestDocsContextElement(
    val manualRestDocumentation: ManualRestDocumentation,
) : AbstractCoroutineContextElement(Key) {
    companion object Key : CoroutineContext.Key<RestDocsContextElement>
}

suspend fun manualRestDocumentation(): ManualRestDocumentation =
    coroutineContext[RestDocsContextElement]?.manualRestDocumentation
        ?: error("RestDocsContextElement is not registered in coroutine context.")

suspend fun <T> withManualRestDocumentation(block: ManualRestDocumentation.() -> T): T = manualRestDocumentation().run(block)
