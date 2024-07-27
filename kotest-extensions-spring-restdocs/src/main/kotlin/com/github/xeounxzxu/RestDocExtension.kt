package com.github.xeounxzxu

import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.extensions.spring.testContextManager
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.withContext
import org.springframework.restdocs.ManualRestDocumentation

val RestDocExtension = RestDocTestExtension()

suspend fun manualRestDocumentation(): ManualRestDocumentation {
    return coroutineContext[RestDocTestContextCoroutineContextElement]?.manualRestDocumentation
        ?: error("No ManualRestDocumentation defined in this coroutine context")
}

class RestDocTestContextCoroutineContextElement(
    val manualRestDocumentation: ManualRestDocumentation,
) : AbstractCoroutineContextElement(Key) {
    companion object Key : CoroutineContext.Key<RestDocTestContextCoroutineContextElement>
}

class RestDocTestExtension : TestCaseExtension {
    override suspend fun intercept(
        testCase: TestCase,
        execute: suspend (TestCase) -> TestResult,
    ): TestResult {
        val manualRestDocumentation = ManualRestDocumentation()

        return withContext(RestDocTestContextCoroutineContextElement(manualRestDocumentation)) {
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

