package io.futz.circleci.model

data class Picard(val buildAgent: BuildAgent? = null,
                  val resourceClass: ResourceClass? = null,
                  val executor: String? = null)