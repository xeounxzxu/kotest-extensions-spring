package com.github.xeounxzxu.restdocs.kotest

import io.kotest.assertions.fail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.restdocs.ManualRestDocumentation
import java.io.File

class SpringRestDocsExtensionTest :
    FunSpec({

        extensions(SpringRestDocsExtension)

        test("makes ManualRestDocumentation available during test execution") {
            val manual = manualRestDocumentation()
            manual.outputDirectoryFile() shouldBe File("build/generated-snippets")
        }

        test("withManualRestDocumentation runs block with the same ManualRestDocumentation instance") {
            val manualFromBlock = withManualRestDocumentation { this }
            val manualDirect = manualRestDocumentation()
            manualFromBlock shouldBe manualDirect
        }
    })

class SpringRestDocsExtensionSystemPropertyTest :
    FunSpec({

        val customOutputDir = "build/custom-snippets-from-property"
        System.setProperty(REST_DOCS_OUTPUT_DIR_PROPERTY, customOutputDir)

        extensions(springRestDocsExtension())

        afterSpec {
            System.clearProperty(REST_DOCS_OUTPUT_DIR_PROPERTY)
        }

        test("uses system property as default output directory") {
            withManualRestDocumentation {
                outputDirectoryFile() shouldBe File(customOutputDir)
            }
        }
    })

class ManualRestDocumentationHelperTest :
    FunSpec({

        test("manualRestDocumentation throws when context element is missing") {
            try {
                manualRestDocumentation()
                fail("Expected manualRestDocumentation to throw when context is missing")
            } catch (e: IllegalStateException) {
                e.message shouldBe "RestDocsContextElement is not registered in coroutine context."
            }
        }
    })

private fun ManualRestDocumentation.outputDirectoryFile(): File {
    val field = ManualRestDocumentation::class.java.getDeclaredField("outputDirectory")
    field.isAccessible = true
    return field.get(this) as File
}
