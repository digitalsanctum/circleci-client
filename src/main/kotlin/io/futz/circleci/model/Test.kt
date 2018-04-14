package io.futz.circleci.model

data class Test(val message: String? = null,
                val file: String? = null,
                val source: String,
                val runTime: Number,
                val result: String,
                val name: String,
                val classname: String)