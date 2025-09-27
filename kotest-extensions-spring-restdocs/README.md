# kotest-extensions-spring-restdocs

Kotest 기반의 테스트에서 [Spring REST Docs](https://docs.spring.io/spring-restdocs/docs/current/reference/htmlsingle/)를 간단하게 사용할 수 있도록 도와주는 Kotest Extension 입니다. `SpringRestDocsExtension`을 등록하면 테스트마다 `ManualRestDocumentation`이 자동으로 준비되고 종료 시 정리되어 스니펫을 안정적으로 생성할 수 있습니다.

## 특징
- Kotest `TestCaseExtension`으로 동작하며 테스트 시작과 종료 사이에서 `ManualRestDocumentation` 생명주기를 관리합니다.
- 기본 스니펫 출력 경로는 `build/generated-snippets` 입니다.
- `manualRestDocumentation()` 헬퍼를 통해 어디서든 `ManualRestDocumentation` 인스턴스에 접근할 수 있습니다.

## 설치
### Gradle (Kotlin DSL)
JitPack을 통해 배포된 아티팩트를 사용하거나, 필요하다면 직접 `publishToMavenLocal`로 설치한 뒤 사용합니다.

```kotlin
repositories {
    mavenCentral()
    maven("https://jitpack.io")
    // 또는 필요 시 mavenLocal()
}

dependencies {
    testImplementation("com.github.xeounxzxu:kotest-extensions-spring-restdocs:1.0.0")
}
```

### 로컬 설치
프로젝트 루트에서 아래 명령을 실행하면 `~/.m2/repository/com/github/xeounxzxu/kotest-extensions-spring-restdocs/1.0.0/` 경로에 설치됩니다.

```bash
./gradlew clean :kotest-extensions-spring-restdocs:publishToMavenLocal
```

## 사용 예시
Spring MVC 슬라이스 테스트에서 REST Docs 스니펫을 생성하는 Kotest 예시입니다. 실제 예제 코드는 `kotest-extensions-spring-restdocs-example` 모듈을 참고하세요.

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
                        responseFields(
                            fieldWithPath("status")
                                .type(JsonFieldType.STRING)
                                .description("상태 값")
                        )
                    )
                }
        }
    }
})
```

## 확장 설정 커스터마이즈
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

## 수동 제어가 필요할 때
`manualRestDocumentation()` 헬퍼를 사용하면 현재 테스트 컨텍스트에 연결된 `ManualRestDocumentation` 인스턴스를 가져올 수 있습니다. 예를 들어 커스텀 RestDocs 설정이 필요한 경우 다음과 같이 사용할 수 있습니다.

```kotlin
suspend fun beforeEachTest() {
    manualRestDocumentation().apply {
        snippetConfigurer
            .withTemplateFormat(TemplateFormats.asciidoctor())
    }
}
```

또는 `withManualRestDocumentation { ... }` 헬퍼를 사용하면 더 간결하게 작성할 수 있습니다.

```kotlin
suspend fun beforeEachTest() = withManualRestDocumentation {
    snippetConfigurer.withTemplateFormat(TemplateFormats.asciidoctor())
}
```

## 빌드 & 테스트
```bash
./gradlew check
```

생성된 스니펫은 각 모듈의 `build/generated-snippets` 아래에서 확인할 수 있습니다. REST Docs를 Asciidoctor와 연동하는 설정은 별도 문서를 참고하거나 예제 모듈을 기반으로 확장하면 됩니다.
