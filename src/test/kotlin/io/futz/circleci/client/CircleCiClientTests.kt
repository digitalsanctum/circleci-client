package io.futz.circleci.client

import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CircleCiClientTests {

  val client = CircleCiClient(CircleCiClientFactory())

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
}