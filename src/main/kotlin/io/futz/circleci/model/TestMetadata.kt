package io.futz.circleci.model

data class TestMetadata(val tests: Set<Test>? = emptySet(),
                        val exceptions: Set<Any>? = emptySet())