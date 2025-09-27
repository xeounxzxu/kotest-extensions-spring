# kotest-spring-restdocs

## English

`kotest-spring-restdocs` is a Kotest extension that makes it effortless to work with [Spring REST Docs](https://docs.spring.io/spring-restdocs/docs/current/reference/htmlsingle/). Once the `SpringRestDocsExtension` is registered, every test automatically receives a fresh `ManualRestDocumentation` instance that is started before the test body runs and cleaned up afterwards so your snippets are always generated safely.

### Features
- Provides a Kotest `TestCaseExtension` that manages the lifecycle of `ManualRestDocumentation` for each test run.
- Uses `build/generated-snippets` as the default output directory.
- Offers helpers such as `manualRestDocumentation()` to access the active `ManualRestDocumentation` instance inside suspending code.

### Installation
#### Gradle (Kotlin DSL)
You can consume the JitPack artifact or publish it locally if needed.

```kotlin
repositories {
    mavenCentral()
    maven("https://jitpack.io")
    // add mavenLocal() if you publish locally
}

dependencies {
    testImplementation("com.github.xeounxzxu:kotest-spring-restdocs:1.0.0")
}
```

#### Publish to Maven Local

```bash
./gradlew clean :kotest-spring-restdocs:publishToMavenLocal
```

### Usage Example
Below is a Spring MVC slice test that produces REST Docs snippets. For a full setup, check the example module.

```kotlin
@WebMvcTest(PingController::class)
class PingControllerTest(
    @Autowired private val mockMvc: MockMvc,
) : FunSpec({

    extensions(SpringExtension, SpringRestDocsExtension)

    context("GET /api/ping") {
        test("responds with 200") {
            mockMvc.get("/api/ping") {
                contentType = MediaType.APPLICATION_JSON
                accept = MediaType.APPLICATION_JSON
            }
                .andExpect { status { isOk() } }
                .andDo {
                    document(
                        "ping-controller-test",
                        responseFields {
                            "status" type JsonFieldType.STRING means "Status value"
                        },
                    )
                }
        }
    }
})
```

### Customising the Extension
Use the `springRestDocsExtension { ... }` factory to override the output directory or snippet name formatter. The `SpringRestDocsExtension` constant is simply the default configuration produced by this factory.

```kotlin
extensions(
    SpringExtension,
    springRestDocsExtension {
        outputDirectory = "build/custom-snippets"
        testNameFormatter = { case -> "${case.spec::class.simpleName}-${case.name.testName}" }
    }
)
```

### Manual Access Helpers
Whenever you need direct access to `ManualRestDocumentation`, call `manualRestDocumentation()` inside a suspend scope
or use `withManualRestDocumentation { ... }` for a scoped block.

```kotlin
suspend fun beforeEachTest() {
    manualRestDocumentation().apply {
        snippetConfigurer
            .withTemplateFormat(TemplateFormats.asciidoctor())
    }
}

suspend fun beforeEachTest() = withManualRestDocumentation {
    snippetConfigurer.withTemplateFormat(TemplateFormats.asciidoctor())
}
```

### Build & Test

```bash
./gradlew check
```

Generated snippets live under `build/generated-snippets`. Combine them with Asciidoctor or any other documentation tooling as needed.

### Kotlin DSL for Field Descriptors
The module also ships a concise DSL to describe payload fields without repetitive builder chains:

```kotlin
document(
    "todos-create",
    requestFields {
        "title" type JsonFieldType.STRING means "Todo title"
    },
    responseFields {
        "id" type JsonFieldType.NUMBER means "Generated todo identifier"
        "message" type JsonFieldType.STRING optionalMeans "Optional message"
    }
)
```

Relaxed variants (`relaxedRequestFields { ... }`, `relaxedResponseFields { ... }`) and subsection helpers (`subsection(...)`, `optionalSubsection(...)`) are available when documenting nested structures.

## 한국어

`kotest-spring-restdocs`는 [Spring REST Docs](https://docs.spring.io/spring-restdocs/docs/current/reference/htmlsingle/)를 Kotest 기반 테스트에서 쉽게 사용할 수 있도록 도와주는 확장입니다. `SpringRestDocsExtension`을 등록하면 각 테스트마다 `ManualRestDocumentation`이 자동으로 준비되고 종료 시 정리되어 스니펫이 안정적으로 생성됩니다.

### 특징
- Kotest `TestCaseExtension`으로 동작하며 테스트 시작과 종료 사이에서 `ManualRestDocumentation` 생명주기를 관리합니다.
- 기본 스니펫 출력 경로는 `build/generated-snippets` 입니다.
- `manualRestDocumentation()` 헬퍼를 통해 서스펜딩 코드 어디에서든 현재 활성화된 `ManualRestDocumentation` 인스턴스에 접근할 수 있습니다.

### 설치
#### Gradle (Kotlin DSL)
JitPack에 배포된 아티팩트를 사용하거나, 필요하다면 직접 `publishToMavenLocal`로 설치한 뒤 사용합니다.

```kotlin
repositories {
    mavenCentral()
    maven("https://jitpack.io")
    // 또는 필요 시 mavenLocal()
}

dependencies {
    testImplementation("com.github.xeounxzxu:kotest-spring-restdocs:1.0.0")
}
```

#### 로컬 설치

```bash
./gradlew clean :kotest-spring-restdocs:publishToMavenLocal
```

### 사용 예시
Spring MVC 슬라이스 테스트에서 REST Docs 스니펫을 생성하는 Kotest 예시입니다. 실제 예제 코드는 예제 모듈을 참고하세요.

```kotlin
@WebMvcTest(PingController::class)
class PingControllerTest(
    @Autowired private val mockMvc: MockMvc,
) : FunSpec({

    extensions(SpringExtension, SpringRestDocsExtension)

    context("GET /api/ping") {
        test("정상 응답") {
            mockMvc.get("/api/ping") {
                contentType = MediaType.APPLICATION_JSON
                accept = MediaType.APPLICATION_JSON
            }
                .andExpect { status { isOk() } }
                .andDo {
                    document(
                        "ping-controller-test",
                        responseFields {
                            "status" type JsonFieldType.STRING means "상태 값"
                        },
                    )
                }
        }
    }
})
```

### 확장 설정 커스터마이즈
`springRestDocsExtension { ... }` 팩토리를 사용하면 출력 경로나 스니펫 이름 포맷터를 원하는 방식으로 조정할 수 있습니다. `SpringRestDocsExtension` 상수는 이 팩토리를 기본 설정으로 생성한 결과입니다.

```kotlin
extensions(
    SpringExtension,
    springRestDocsExtension {
        outputDirectory = "build/custom-snippets"
        testNameFormatter = { case -> "${case.spec::class.simpleName}-${case.name.testName}" }
    }
)
```

### 수동 제어가 필요할 때
`manualRestDocumentation()` 헬퍼를 사용하면 현재 테스트 컨텍스트에 연결된 `ManualRestDocumentation` 인스턴스를 가져올 수 있습니다. 또는 `withManualRestDocumentation { ... }` 블록을 사용해 더 간결하게 접근할 수 있습니다.

```kotlin
suspend fun beforeEachTest() {
    manualRestDocumentation().apply {
        snippetConfigurer
            .withTemplateFormat(TemplateFormats.asciidoctor())
    }
}

suspend fun beforeEachTest() = withManualRestDocumentation {
    snippetConfigurer.withTemplateFormat(TemplateFormats.asciidoctor())
}
```

### 빌드 & 테스트

```bash
./gradlew check
```

생성된 스니펫은 각 모듈의 `build/generated-snippets` 아래에서 확인할 수 있습니다. REST Docs를 Asciidoctor와 연동하는 설정은 별도 문서를 참고하거나 예제 모듈을 기반으로 확장하면 됩니다.

### 필드 디스크립터를 위한 Kotlin DSL
반복적인 체이닝 대신 다음과 같은 DSL을 사용할 수 있습니다.

```kotlin
document(
    "todos-create",
    requestFields {
        "title" type JsonFieldType.STRING means "할 일 제목"
    },
    responseFields {
        "id" type JsonFieldType.NUMBER means "생성된 식별자"
        "message" type JsonFieldType.STRING optionalMeans "선택 메시지"
    }
)
```

`relaxedRequestFields { ... }`, `relaxedResponseFields { ... }`, `subsection(...)`, `optionalSubsection(...)` 등을 활용해 중첩 구조도 간단히 표현할 수 있습니다.
