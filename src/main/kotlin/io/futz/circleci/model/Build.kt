package io.futz.circleci.model

import java.time.OffsetDateTime

data class Build(val outcome: String? = null,
                 val status: String? = null,
                 val buildNum: Int,
                 val vcsRevision: String? = null,
                 val pushedAt: OffsetDateTime? = null,
                 val addedAt: OffsetDateTime? = null) {
}