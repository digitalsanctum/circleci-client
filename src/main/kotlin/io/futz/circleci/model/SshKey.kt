package io.futz.circleci.model

data class SshKey(val hostname: String? = null,
                  val publicKey: String,
                  val fingerprint: String)