package io.futz.circleci.model

import java.time.OffsetDateTime

data class Action(val truncated: Boolean,
                  val index: Int,
                  val parallel: Boolean,
                  val failed: Boolean?,
                  val infrastructureFail: String? = null,
                  val name: String,
                  val bashCommand: String? = null,
                  val status: String? = null,
                  val timedout: Boolean?,
                  val `continue`: String? = null,
                  val endTime: OffsetDateTime,
                  val type: String? = null,
                  val allocationId: String? = null,
                  val outputUrl: String? = null,
                  val startTime: OffsetDateTime,
                  val background: Boolean,
                  val exitCode: String? = null,
                  val insignificant: Boolean,
                  val canceled: String? = null,
                  val step: Int,
                  val runTimeMillis: Int,
                  val hasOutput: Boolean)