package io.futz.circleci.client

import io.futz.circleci.model.Artifact
import io.futz.circleci.model.BuildDetail
import io.futz.circleci.model.BuildDetailWithSteps
import io.futz.circleci.model.CheckoutKey
import io.futz.circleci.model.EnvironmentVariable
import io.futz.circleci.model.HerokuApiKey
import io.futz.circleci.model.Project
import io.futz.circleci.model.TestMetadata
import io.futz.circleci.model.User
import okhttp3.logging.HttpLoggingInterceptor

class CircleCiClient(factory: CircleCiClientFactory) {

  private val client = factory.create(HttpLoggingInterceptor.Level.BODY)

  fun me(): User? = client.me().execute().body()
  fun projects(): Set<Project>? = client.projects().execute().body()

  fun recentBuilds(limit: Int? = 30,
                   offset: Int? = 0): Set<BuildDetail>?
      = client.recentBuilds(limit, offset).execute().body()

  fun buildsForProject(vcsType: String,
                       username: String,
                       project: String,
                       limit: Int? = 30,
                       offset: Int? = 0,
                       filter: String? = null): Set<BuildDetail>?
      = client.buildsForProject(vcsType, username, project, limit, offset, filter).execute().body()

  fun buildDetails(vcsType: String,
                   username: String,
                   project: String,
                   buildNum: String): BuildDetailWithSteps?
      = client.buildDetails(vcsType, username, project, buildNum).execute().body()

  fun artifacts(vcsType: String,
                username: String,
                project: String,
                buildNum: String): Set<Artifact>?
      = client.artifacts(vcsType, username, project, buildNum).execute().body()

  fun checkoutKeys(vcsType: String,
                   username: String,
                   project: String): Set<CheckoutKey>?
      = client.checkoutKeys(vcsType, username, project).execute().body()

  fun checkoutKey(vcsType: String,
                  username: String,
                  project: String,
                  fingerprint: String): CheckoutKey?
      = client.checkoutKey(vcsType, username, project, fingerprint).execute().body()

  fun environmentVariables(vcsType: String,
                           username: String,
                           project: String): Set<EnvironmentVariable>?
      = client.environmentVariables(vcsType, username, project).execute().body()

  fun environmentVariable(vcsType: String,
                          username: String,
                          project: String,
                          name: String): EnvironmentVariable?
      = client.environmentVariable(vcsType, username, project, name).execute().body()

  fun testMetadata(vcsType: String,
                   username: String,
                   project: String,
                   buildNum: String): TestMetadata?
      = client.testMetadata(vcsType, username, project, buildNum).execute().body()

  fun addHerokuApiKey(herokuApiKey: HerokuApiKey): String?
      = client.addHerokuApiKey(herokuApiKey).execute().body()
}