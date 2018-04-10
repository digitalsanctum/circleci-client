package io.futz.circleci.model

data class BuildStep(val name: String,
                     val actions: Set<Action>)