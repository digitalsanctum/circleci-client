package io.futz.circleci.model

import java.time.OffsetDateTime

data class CheckoutKey(val publicKey: String,
                       val type: String,
                       val fingerprint: String,
                       val preferred: Boolean,
                       val login: String? = null,
                       val time: OffsetDateTime)