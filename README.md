# Kotest Spring REST Docs Extensions

Kotest-friendly extensions and DSL helpers for building REST Docs snippets from Spring-based tests. Guidance is provided in both English and Korean.

## Overview
### English
Kotest Spring REST Docs streamlines Spring REST Docs in Kotest specs by managing `ManualRestDocumentation` lifecycles, offering a concise field-descriptor DSL, and shipping a runnable sample that demonstrates end-to-end usage.

### 한국어
Kotest Spring REST Docs는 Kotest 스펙에서 `ManualRestDocumentation` 생명주기를 자동으로 관리하고, 간결한 필드 디스크립터 DSL과 전체 연동 예제를 제공해 Spring REST Docs 사용을 단순화합니다.

## Modules
### English
- `kotest-spring-restdocs`: Core extension and DSL for Spring REST Docs inside Kotest.
- `kotest-spring-restdocs-sample`: Spring Boot to-do API that showcases the extension configuration and generated snippets.

### 한국어
- `kotest-spring-restdocs`: Kotest에서 Spring REST Docs를 사용하기 위한 핵심 확장 및 DSL.
- `kotest-spring-restdocs-sample`: 확장 설정과 스니펫 생성을 보여주는 Spring Boot 할 일 API 예제.

## Key Features
### English
- Coroutine-aware `SpringRestDocsExtension` that prepares and cleans up `ManualRestDocumentation` per test case.
- Kotlin DSL (`requestFields {}`, `responseFields {}`, `subsection(...)`, etc.) to describe payloads without repetitive builder calls.
- Works out of the box with `SpringExtension`, MockMvc, and REST Docs default snippet locations (`build/generated-snippets`).
- Helpers such as `manualRestDocumentation()` and `withManualRestDocumentation {}` for direct access when custom configuration is required.

### 한국어
- 각 테스트 케이스마다 `ManualRestDocumentation`을 준비하고 정리하는 코루틴 친화적 `SpringRestDocsExtension` 제공.
- `requestFields {}`, `responseFields {}`, `subsection(...)` 등 반복 체이닝 없이 페이로드를 기술할 수 있는 Kotlin DSL 지원.
- `SpringExtension`, MockMvc, REST Docs 기본 스니펫 경로(`build/generated-snippets`)와 자연스럽게 연동.
- 커스텀 설정이 필요할 때 `manualRestDocumentation()`, `withManualRestDocumentation {}` 헬퍼로 직접 제어 가능.

## Installation
### English
#### Gradle (Kotlin DSL)
```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        // mavenLocal() // include if you publish locally
    }
}

dependencies {
    testImplementation("com.github.xeounxzxu:kotest-spring-restdocs:1.0.2")
}
```

#### Gradle (Groovy DSL)
```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url "https://jitpack.io" }
        // mavenLocal() // include if you publish locally
    }
}

dependencies {
    testImplementation "com.github.xeounxzxu:kotest-spring-restdocs:1.0.2"
}
```

#### Maven
```xml
<repositories>
    <repository>
        <id>jitpack</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.xeounxzxu</groupId>
        <artifactId>kotest-spring-restdocs</artifactId>
        <version>1.0.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```
Add the dependency to `<dependencyManagement>` if you prefer managing versions centrally.

#### Publish Locally (Optional)
```bash
./gradlew clean :kotest-spring-restdocs:publishToMavenLocal
```

### 한국어
#### Gradle (Kotlin DSL)
```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        // mavenLocal() // 로컬 배포 시 포함
    }
}

dependencies {
    testImplementation("com.github.xeounxzxu:kotest-spring-restdocs:1.0.2")
}
```

#### Gradle (Groovy DSL)
```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url "https://jitpack.io" }
        // mavenLocal() // 로컬 배포 시 포함
    }
}

dependencies {
    testImplementation "com.github.xeounxzxu:kotest-spring-restdocs:1.0.2"
}
```

#### Maven
```xml
<repositories>
    <repository>
        <id>jitpack</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.xeounxzxu</groupId>
        <artifactId>kotest-spring-restdocs</artifactId>
        <version>1.0.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```
버전을 중앙에서 관리하고 싶다면 `<dependencyManagement>`에 추가하세요.

#### 로컬 배포 (선택)
```bash
./gradlew clean :kotest-spring-restdocs:publishToMavenLocal
```

## Quick Start
### English
Register both the Spring and REST Docs extensions inside your Kotest spec, then document requests and responses as usual.
```kotlin
@WebMvcTest(PingController::class)
class PingControllerTest(
    @Autowired private val mockMvc: MockMvc,
) : FunSpec({

    extensions(SpringExtension, SpringRestDocsExtension)

    context("GET /api/ping") {
        test("responds with 200") {
            mockMvc.get("/api/ping") {
                accept = MediaType.APPLICATION_JSON
            }
                .andExpect { status { isOk() } }
                .andDo {
                    document(
                        "ping",
                        responseFields {
                            "status" type JsonFieldType.STRING means "Status message"
                        },
                    )
                }
        }
    }
})
```
Use `springRestDocsExtension { ... }` if you want to customise the output directory or test-name strategy.

### 한국어
Kotest 스펙에서 Spring, REST Docs 확장을 함께 등록한 뒤 일반적인 방식으로 요청과 응답을 문서화하면 됩니다.
```kotlin
extensions(
    SpringExtension,
    springRestDocsExtension {
        outputDirectory = "build/custom-snippets"
        testNameFormatter = { case -> "${case.spec::class.simpleName}-${case.name.testName}" }
    }
)
```
필요 시 `manualRestDocumentation()` 또는 `withManualRestDocumentation {}`를 호출해 템플릿 포맷 등 세부 설정을 조정할 수 있습니다.

## DSL Helpers
### English
- `requestFields { "title" type JsonFieldType.STRING means "Todo title" }`
- `responseFields { "id" type JsonFieldType.NUMBER means "Generated identifier" }`
- Relaxed variants (`relaxedRequestFields`, `relaxedResponseFields`) and subsection helpers (`subsection`, `optionalSubsection`) cover nested payloads.

### 한국어
- `requestFields { "title" type JsonFieldType.STRING means "할 일 제목" }`
- `responseFields { "id" type JsonFieldType.NUMBER means "생성된 식별자" }`
- 중첩 구조는 `relaxedRequestFields`, `relaxedResponseFields`, `subsection`, `optionalSubsection` 등으로 간단히 표현할 수 있습니다.

## Local Development
### English
- `./gradlew ktlintCheck`: Kotlin style checks.
- `./gradlew :kotest-spring-restdocs-sample:test`: Regenerates the sample snippets.
- `./gradlew check`: Full verification before release.

### 한국어
- `./gradlew ktlintCheck`: Kotlin 스타일 검사.
- `./gradlew :kotest-spring-restdocs-sample:test`: 예제 스니펫 재생성.
- `./gradlew check`: 릴리스 전 전체 검증.

## Release Tips
### English
1. Update `gradle.properties` so `kotestSpringRestdocsVersion` reflects the target release.
2. Tag the commit (`git tag 1.0.2 && git push origin 1.0.2`).
3. GitHub Actions will run the release workflow and trigger JitPack.
4. Confirm the build on JitPack, then consume the new version.

### 한국어
1. `gradle.properties`의 `kotestSpringRestdocsVersion`을 릴리스 버전으로 맞춥니다.
2. 동일한 이름의 태그를 생성하고 푸시합니다. (`git tag 1.0.2 && git push origin 1.0.2`)
3. GitHub Actions가 릴리스 워크플로를 실행하고 JitPack 빌드를 시작합니다.
4. JitPack에서 빌드 성공을 확인한 뒤 새 버전을 사용합니다.

## License
This project is distributed under the MIT License. / 본 프로젝트는 MIT 라이선스로 배포됩니다.
