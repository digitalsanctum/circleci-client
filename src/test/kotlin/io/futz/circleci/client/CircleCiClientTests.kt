package io.futz.circleci.client

import io.futz.circleci.model.HerokuApiKey
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CircleCiClientTests {

  private val client = CircleCiClient(CircleCiClientFactory())

  private val validHerokuApiKey = System.getenv("HEROKU_API_KEY")

  @Before
  fun setup() {

  }

  @After
  fun tearDown() {

  }

  @Test
  fun buildsForProject() {
    val builds = client.buildsForProject("github", "digitalsanctum", "java-lib")
    assertNotNull(builds)
  }

  @Test
  fun projects() {
    val projects = client.projects()
    assertNotNull(projects)
  }

  @Test
  fun me() {
    val maybeUser = client.me()
    assertTrue(maybeUser.isPresent)
  }

  @Test
  fun buildDetails() {
    val maybeBuildDetailsWithSteps = client.buildDetails("github", "digitalsanctum", "java-lib", "5")
    assertTrue(maybeBuildDetailsWithSteps.isPresent)
  }

  @Test
  fun artifacts() {
    val artifacts = client.artifacts("github", "digitalsanctum", "java-lib", "7")
    assertNotNull(artifacts)
  }

  @Test
  fun checkoutKeys() {
    val checkoutKeys = client.checkoutKeys("github", "digitalsanctum", "java-lib")
    assertNotNull(checkoutKeys)
  }

  @Test
  fun checkoutKey() {
    val checkoutKey = client.checkoutKey("github", "digitalsanctum", "java-lib", "59:d9:39:62:8a:3f:ae:1c:aa:dc:37:1c:ac:2c:5d:8e")
    assertNotNull(checkoutKey)
    assertTrue(checkoutKey.isPresent)
  }

  @Test
  fun environmentVariables() {
    val vars = client.environmentVariables("github", "digitalsanctum", "java-lib")
    assertNotNull(vars)
  }

  @Test
  fun environmentVariable() {
    val environmentVariable = client.environmentVariable("github", "digitalsanctum", "java-lib", "foo")
    assertNotNull(environmentVariable)
  }

  @Test
  fun testMetadata() {
    val testMetadata = client.testMetadata("github", "digitalsanctum", "java-lib", "9")
    assertNotNull(testMetadata)
    assertTrue(testMetadata.isPresent)
  }

  @Test
  fun testMetadataWithNotFound() {
    val testMetadata = client.testMetadata("github", "digitalsanctum", "foo", "9")
    assertNotNull(testMetadata)
    assertTrue(testMetadata.isPresent)
  }

  @Test
  fun testMetadataWithException() {
    val testMetadata = client.testMetadata("github", "digitalsanctum", "java-lib", "10")
    assertNotNull(testMetadata)
    assertTrue(testMetadata.isPresent)
  }

  @Test
  fun addHerokuApiKeyWithValidKey() {
    val herokuApiKey = HerokuApiKey(validHerokuApiKey)
    val maybeResponse = client.addHerokuApiKey(herokuApiKey)
    assertNotNull(maybeResponse)
    assertFalse(maybeResponse.first.isPresent)
    assertFalse(maybeResponse.second.isPresent)
  }

  @Test
  fun addHerokuApiKeyWithBogusKey() {
    val herokuApiKey = HerokuApiKey("boguskey")
    val maybeResponse = client.addHerokuApiKey(herokuApiKey)
    assertNotNull(maybeResponse)
    val apiResponseError = maybeResponse.first
    assertTrue(apiResponseError.isPresent)
    assertEquals(403, apiResponseError.get().code)
    assertEquals("Your Heroku API key is invalid.", apiResponseError.get().message)
  }
}