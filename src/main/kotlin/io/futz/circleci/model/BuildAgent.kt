package io.futz.circleci.model

data class BuildAgent(val image: String? = null,
                      val properties: Map<String, String> = mapOf())