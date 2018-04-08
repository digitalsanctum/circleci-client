package io.futz.circleci.model

data class Branch(val runningBuilds: Array<Build>,
                  val recentBuilds: Array<Build>,
                  val lastNonSuccess: Build? = null,
                  val lastSuccess: Build? = null) {
}