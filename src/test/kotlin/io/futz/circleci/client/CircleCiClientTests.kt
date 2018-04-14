package io.futz.circleci.client

import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CircleCiClientTests {

  private val client = CircleCiClient(CircleCiClientFactory())

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

  }
}