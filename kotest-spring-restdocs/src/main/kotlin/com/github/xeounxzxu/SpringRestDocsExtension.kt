package com.github.xeounxzxu

import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import kotlinx.coroutines.withContext
import org.springframework.restdocs.ManualRestDocumentation
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

private const val DEFAULT_OUTPUT_DIRECTORY = "build/generated-snippets"

/**
 * Matches Spring REST Docs' standard output property so users can override with `-Dorg.springframework.restdocs.outputDir=...`.
 */
const val REST_DOCS_OUTPUT_DIR_PROPERTY: String = "org.springframework.restdocs.outputDir"

private val invalidSnippetChars = Regex("[^A-Za-z0-9-_]")
private val blankSequence = Regex("\\s+")

class SpringRestDocsConfig {
    var outputDirectory: String = System.getProperty(REST_DOCS_OUTPUT_DIR_PROPERTY) ?: DEFAULT_OUTPUT_DIRECTORY
    var testNameFormatter: (TestCase) -> String = { testCase ->
        testCase.name.testName
            .replace(blankSequence, "-")
            .replace(invalidSnippetChars, "-")
            .trim('-')
            .ifEmpty { "unnamed-test" }
    }
}

private data class SpringRestDocsSettings(
    val outputDirectory: String,
    val testNameFormatter: (TestCase) -> String,
)

fun springRestDocsExtension(configure: SpringRestDocsConfig.() -> Unit = {}): TestCaseExtension {
    val config = SpringRestDocsConfig().apply(configure)
    val settings = SpringRestDocsSettings(config.outputDirectory, config.testNameFormatter)
    return SpringRestDocsExtensionImpl(settings)
}

val SpringRestDocsExtension: TestCaseExtension = springRestDocsExtension()

private class SpringRestDocsExtensionImpl(
    private val settings: SpringRestDocsSettings,
) : TestCaseExtension {

    override suspend fun intercept(testCase: TestCase, execute: suspend (TestCase) -> TestResult): TestResult {
        val restDocs = ManualRestDocumentation(settings.outputDirectory)
        restDocs.beforeTest(testCase.spec::class.java, settings.testNameFormatter(testCase))

        return try {
            withContext(RestDocsContextElement(restDocs)) {
                execute(testCase)
            }
        } finally {
            restDocs.afterTest()
        }
    }
}

class RestDocsContextElement(
    val manualRestDocumentation: ManualRestDocumentation,
) : AbstractCoroutineContextElement(Key) {
    companion object Key : CoroutineContext.Key<RestDocsContextElement>
}

suspend fun manualRestDocumentation(): ManualRestDocumentation {
    return coroutineContext[RestDocsContextElement]?.manualRestDocumentation
        ?: error("RestDocsContextElement is not registered in coroutine context.")
}

suspend fun <T> withManualRestDocumentation(block: ManualRestDocumentation.() -> T): T {
    return manualRestDocumentation().run(block)
}
