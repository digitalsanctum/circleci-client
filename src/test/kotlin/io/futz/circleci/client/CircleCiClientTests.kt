package io.futz.circleci.client

import io.futz.circleci.model.HerokuApiKey
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class CircleCiClientTests {

  companion object {

    lateinit var validHerokuApiKey: String
    lateinit var testUser: String
    lateinit var testProject: String
    lateinit var testVcsType: String
    lateinit var testFingerPrint: String

    lateinit var client: CircleCiClient

    @BeforeClass
    @JvmStatic
    fun setupClazz() {

      arrayOf(
          "HEROKU_API_KEY",
          "CIRCLECI_TEST_USER",
          "CIRCLECI_TEST_PROJECT",
          "CIRCLECI_TEST_VCS_TYPE",
          "CIRCLECI_TEST_FINGERPRINT"
      ).forEach { assertNotNull("Missing required environment variable: $it", System.getenv(it)) }

      testUser = System.getenv("CIRCLECI_TEST_USER")
      testProject = System.getenv("CIRCLECI_TEST_PROJECT")
      testVcsType = System.getenv("CIRCLECI_TEST_VCS_TYPE")
      testFingerPrint = System.getenv("CIRCLECI_TEST_FINGERPRINT")

      client = CircleCiClient(CircleCiClientFactory())
    }
  }

  @Before
  fun setup() {
    validHerokuApiKey = System.getenv("HEROKU_API_KEY")
  }

  @Test
  fun recentBuilds() {
    val recentBuilds = client.recentBuilds()
    assertNotNull(recentBuilds)
    assertTrue(recentBuilds!!.isNotEmpty())
  }

  @Test
  fun buildsForProject() {
    val builds = client.buildsForProject(testVcsType, testUser, testProject)
    assertNotNull(builds)
  }

  @Test
  fun projects() {
    val projects = client.projects()
    assertNotNull(projects)
  }

  @Test
  fun me() {
    val user = client.me()
    assertNotNull(user)
  }

  @Test
  fun buildDetails() {
    val buildDetailsWithSteps = client.buildDetails(testVcsType, testUser, testProject, "5")
    assertNotNull(buildDetailsWithSteps)
  }

  @Test
  fun artifacts() {
    val artifacts = client.artifacts(testVcsType, testUser, testProject, "7")
    assertNotNull(artifacts)
  }

  @Test
  fun checkoutKeys() {
    val checkoutKeys = client.checkoutKeys(testVcsType, testUser, testProject)
    assertNotNull(checkoutKeys)
  }

  @Test
  fun checkoutKey() {
    val checkoutKey = client.checkoutKey(testVcsType, testUser, testProject, testFingerPrint)
    assertNotNull(checkoutKey)
  }

  @Test
  fun environmentVariables() {
    val vars = client.environmentVariables(testVcsType, testUser, testProject)
    assertNotNull(vars)
  }

  @Test
  fun environmentVariable() {
    val environmentVariable = client.environmentVariable(testVcsType, testUser, testProject, "foo")
    assertNotNull(environmentVariable)
  }

  @Test
  fun testMetadata() {
    val testMetadata = client.testMetadata(testVcsType, testUser, testProject, "9")
    assertNotNull(testMetadata)
  }

  @Test
  fun testMetadataWithNotFound() {
    val testMetadata = client.testMetadata(testVcsType, testUser, "foo", "9")
    assertNull(testMetadata)
  }

  @Test
  fun testMetadataWithException() {
    val testMetadata = client.testMetadata(testVcsType, testUser, testProject, "10")
    assertNotNull(testMetadata)
  }

  @Test
  fun addHerokuApiKeyWithValidKey() {
    val herokuApiKey = HerokuApiKey(validHerokuApiKey)
    val response = client.addHerokuApiKey(herokuApiKey)
    assertNotNull(response)
    assertEquals("", response) // on success an empty String is returned
  }

  @Test
  fun addHerokuApiKeyWithBogusKey() {
    val herokuApiKey = HerokuApiKey("boguskey")
    val response = client.addHerokuApiKey(herokuApiKey)
    assertNull(response)
  }
}