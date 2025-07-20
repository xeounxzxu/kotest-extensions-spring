package com.github.xeounxzxu

import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import kotlinx.coroutines.withContext
import org.springframework.restdocs.ManualRestDocumentation
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

//val SpringRestDocsExtension = RestDocTestExtension()
//
//suspend fun manualRestDocumentation(): ManualRestDocumentation {
//    return coroutineContext[RestDocsContextElement]?.manualRestDocumentation
//        ?: error("no manualRestDocumentation defined in this coroutine context")
//}
//
//class RestDocsContextElement(
//    val manualRestDocumentation: ManualRestDocumentation,
//) : AbstractCoroutineContextElement(Key) {
//    companion object Key : CoroutineContext.Key<RestDocsContextElement>
//}
//
//class RestDocTestExtension : TestCaseExtension {
//    override suspend fun intercept(
//        testCase: TestCase,
//        execute: suspend (TestCase) -> TestResult,
//    ): TestResult {
//        val manualRestDocumentation = ManualRestDocumentation()
//
//        return withContext(RestDocsContextElement(manualRestDocumentation)) {
//            testContextManager().beforeTestClass()
//            testContextManager().prepareTestInstance(testCase.spec)
//            manualRestDocumentation.beforeTest(testCase.javaClass::class.java, testCase.name.testName)
//            val result = execute(testCase)
//            manualRestDocumentation.afterTest()
//            testContextManager().afterTestClass()
//            result
//        }
//    }
//}


/**
 * Kotest 전용 RestDocs Extension
 */

object SpringRestDocsExtension : TestCaseExtension {

    override suspend fun intercept(testCase: TestCase, execute: suspend (TestCase) -> TestResult): TestResult {
        val restDocs = ManualRestDocumentation("build/generated-snippets")

        restDocs.beforeTest(testCase.spec::class.java, testCase.name.testName)

        return try {
            withContext(RestDocsContextElement(restDocs)) {
                execute(testCase)
            }
        } finally {
            restDocs.afterTest()
        }
    }
}

/**
 * 코루틴 컨텍스트로 전달하기 위한 Wrapper
 */
class RestDocsContextElement(
    val manualRestDocumentation: ManualRestDocumentation
) : AbstractCoroutineContextElement(RestDocsContextElement) {
    companion object Key : CoroutineContext.Key<RestDocsContextElement>
}

/**
 * 어디서든 REST Docs 인스턴스를 가져올 수 있는 헬퍼
 */
suspend fun manualRestDocumentation(): ManualRestDocumentation {
    return kotlin.coroutines.coroutineContext[RestDocsContextElement]?.manualRestDocumentation
        ?: error("RestDocsContextElement is not registered in coroutine context.")
}
