package io.futz.circleci.client

import io.futz.circleci.model.Artifact
import io.futz.circleci.model.BuildDetail
import io.futz.circleci.model.BuildDetailWithSteps
import io.futz.circleci.model.CheckoutKey
import io.futz.circleci.model.EnvironmentVariable
import io.futz.circleci.model.Project
import io.futz.circleci.model.TestMetadata
import io.futz.circleci.model.User
import okhttp3.logging.HttpLoggingInterceptor
import java.util.*

class CircleCiClient(factory: CircleCiClientFactory) {

  private val client = factory.getClient(HttpLoggingInterceptor.Level.BODY)

  fun me(): Optional<User> {
    val call = client.me()
    val resp = call.execute()
    return when {
      resp.isSuccessful -> Optional.ofNullable(resp.body())
      else -> Optional.empty()
    }
  }

  fun projects(): Set<Project> {
    val call = client.projects()
    val resp = call.execute()
    return when {
      resp.isSuccessful -> resp.body()!!
      else -> setOf()
    }
  }

  fun recentBuilds(limit: Int? = 30,
                   offset: Int? = 0): Set<BuildDetail> {
    val call = client.recentBuilds(limit, offset)
    val resp = call.execute()
    return when {
      resp.isSuccessful -> resp.body()!!
      else -> setOf()
    }
  }

  fun buildsForProject(vcsType: String,
                       username: String,
                       project: String,
                       limit: Int? = 30,
                       offset: Int? = 0,
                       filter: String? = null): Set<BuildDetail> {
    val call = client.buildsForProject(vcsType, username, project, limit, offset, filter)
    val resp = call.execute()
    return when {
      resp.isSuccessful -> resp.body()!!
      else -> setOf()
    }
  }

  fun buildDetails(vcsType: String,
                   username: String,
                   project: String,
                   buildNum: String): Optional<BuildDetailWithSteps> {
    val call = client.buildDetails(vcsType, username, project, buildNum)
    val resp = call.execute()
    return when {
      resp.isSuccessful -> Optional.ofNullable(resp.body())
      else -> Optional.empty()
    }
  }

  fun artifacts(vcsType: String,
                username: String,
                project: String,
                buildNum: String): Set<Artifact> {
    val call = client.artifacts(vcsType, username, project, buildNum)
    val resp = call.execute()
    return when {
      resp.isSuccessful -> resp.body()!!
      else -> setOf()
    }
  }

  fun checkoutKeys(vcsType: String,
                   username: String,
                   project: String): Set<CheckoutKey> {
    val call = client.checkoutKeys(vcsType, username, project)
    val resp = call.execute()
    return when {
      resp.isSuccessful -> resp.body()!!
      else -> setOf()
    }
  }

  fun checkoutKey(vcsType: String,
                  username: String,
                  project: String,
                  fingerprint: String): Optional<CheckoutKey> {
    val call = client.checkoutKey(vcsType, username, project, fingerprint)
    val resp = call.execute()
    return when {
      resp.isSuccessful -> Optional.ofNullable(resp.body())
      else -> Optional.empty()
    }
  }

  fun environmentVariables(vcsType: String,
                           username: String,
                           project: String): Set<EnvironmentVariable> {
    val call = client.environmentVariables(vcsType, username, project)
    val resp = call.execute()
    return when {
      resp.isSuccessful -> resp.body()!!
      else -> setOf()
    }
  }

  fun environmentVariable(vcsType: String,
                          username: String,
                          project: String,
                          name: String): Optional<EnvironmentVariable> {
    val call = client.environmentVariable(vcsType, username, project, name)
    val resp = call.execute()
    return when {
      resp.isSuccessful -> Optional.ofNullable(resp.body())
      else -> Optional.empty()
    }
  }

  fun testMetadata(vcsType: String,
                   username: String,
                   project: String,
                   buildNum: String): Optional<TestMetadata> {
    val call = client.testMetadata(vcsType, username, project, buildNum)
    val resp = call.execute()
    return when {
      resp.isSuccessful -> Optional.ofNullable(resp.body())
      else -> Optional.empty()
    }
  }
}