package io.futz.circleci.model

data class CommitDetails(val authorName: String,
                         val commitUrl: String,
                         val authorLogin: String,
                         val committerEmail: String,
                         val committerLogin: String,
                         val subject: String,
                         val authorDate: String,
                         val commit: String,
                         val authorEmail: String,
                         val committerDate: String,
                         val body: String,
                         val committerName: String)