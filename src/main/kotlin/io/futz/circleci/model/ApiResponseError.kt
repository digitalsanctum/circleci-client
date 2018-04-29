package io.futz.circleci.model

data class ApiResponseError(var code: Int,
                            val message: String?,
                            val documentationUrl: String?)