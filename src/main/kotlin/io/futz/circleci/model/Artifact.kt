package io.futz.circleci.model

data class Artifact(val path: String,
                    val prettyPath: String,
                    val nodeIndex: Int,
                    val url: String)