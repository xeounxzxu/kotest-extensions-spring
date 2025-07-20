package com.github.xeounxzxu

import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.extensions.spring.testContextManager
import kotlinx.coroutines.withContext
import org.springframework.restdocs.ManualRestDocumentation
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

val SpringRestDocsExtension = RestDocTestExtension()

suspend fun manualRestDocumentation(): ManualRestDocumentation {
    return coroutineContext[RestDocsContextElement]?.manualRestDocumentation
        ?: error("no manualRestDocumentation defined in this coroutine context")
}

class RestDocsContextElement(
    val manualRestDocumentation: ManualRestDocumentation,
) : AbstractCoroutineContextElement(Key) {
    companion object Key : CoroutineContext.Key<RestDocsContextElement>
}

class RestDocTestExtension : TestCaseExtension {
    override suspend fun intercept(
        testCase: TestCase,
        execute: suspend (TestCase) -> TestResult,
    ): TestResult {
        val manualRestDocumentation = ManualRestDocumentation()

        return withContext(RestDocsContextElement(manualRestDocumentation)) {
            testContextManager().beforeTestClass()
            testContextManager().prepareTestInstance(testCase.spec)
            manualRestDocumentation.beforeTest(testCase.javaClass::class.java, testCase.name.testName)
            val result = execute(testCase)
            manualRestDocumentation.afterTest()
            testContextManager().afterTestClass()
            result
        }
    }
}
