package com.github.xeounxzxu.kotestextensionsspringrestdocsexample.api

import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension


class PingControllerTest : FunSpec() {

    init {
        extensions(SpringExtension)

        context("GET /api/ping") {

            test("정상적으로 ping 을 보낸다") {


            }
        }
    }
}

