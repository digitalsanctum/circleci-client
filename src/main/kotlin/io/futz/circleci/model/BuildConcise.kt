package io.futz.circleci.model

data class BuildConcise(val buildNum: Int,
                        val status: String? = null,
                        val buildTimeMillis: Int)