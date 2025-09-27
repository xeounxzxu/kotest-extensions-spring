# Kotest Spring Restdocs

## English Overview
- **Modules**
  - `kotest-spring-restdocs`: Kotest extension and DSL helpers for Spring REST Docs.
  - `kotest-spring-restdocs-sample`: Spring Boot to-do API showcasing the extension in practice.
- **Key Features**
  - Coroutine-aware `ManualRestDocumentation` management for Kotest.
  - Kotlin DSL to describe request/response fields and subsections succinctly.
  - Works seamlessly with `SpringExtension` and MockMvc for REST Docs.
- **Getting Started**
  - Install with `./gradlew clean :kotest-spring-restdocs:publishToMavenLocal` or use JitPack coordinates `com.github.xeounxzxu:kotest-spring-restdocs`.
  - Run `./gradlew ktlintCheck` to ensure formatting and `./gradlew :kotest-spring-restdocs-sample:test` to regenerate sample snippets.

## 한국어 개요
- **모듈 구성**
  - `kotest-spring-restdocs`: Spring REST Docs를 위한 Kotest 확장 및 DSL 헬퍼 제공.
  - `kotest-spring-restdocs-sample`: 확장을 이용한 Spring Boot 할 일 API 예제.
- **주요 특징**
  - Kotest에서 `ManualRestDocumentation` 생명주기 자동 관리.
  - 요청/응답 필드를 간결하게 기술할 수 있는 Kotlin DSL 지원.
  - `SpringExtension` 및 MockMvc와 자연스럽게 연동되는 REST Docs 설정.
- **시작하기**
  - `./gradlew clean :kotest-spring-restdocs:publishToMavenLocal` 실행 또는 JitPack 좌표 `com.github.xeounxzxu:kotest-spring-restdocs` 사용.
  - 코드 포맷 검사는 `./gradlew ktlintCheck`, 예제 스니펫 생성은 `./gradlew :kotest-spring-restdocs-sample:test` 실행.
