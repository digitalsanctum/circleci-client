import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
  var kotlinVersion: String by extra
  kotlinVersion = "1.2.41"

  repositories {
    jcenter()
    mavenCentral()
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    maven("https://kotlin.bintray.com/kotlinx")
  }
  dependencies {
    classpath(kotlinModule("gradle-plugin", kotlinVersion))
  }
}

group = "io.futz"
version = "0.1.0"

plugins {
  application
  maven
}

apply {
  plugin("kotlin")
  plugin("maven")
}

repositories {
  maven { setUrl("http://dl.bintray.com/kotlin/kotlin-eap") }
  mavenCentral()
}

dependencies {
  val kotlinVersion: String by extra
  compile(kotlinModule("stdlib-jdk8", kotlinVersion))

  compile("org.slf4j:slf4j-api:1.7.25")
  compile("com.squareup.okhttp3:logging-interceptor:3.10.0")
  compile("com.squareup.retrofit2:retrofit:2.4.0")
  compile("com.squareup.retrofit2:converter-jackson:2.4.0")
  compile("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.9.5")
  compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.9.5")
  compile("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.5")

  testCompile("org.slf4j:slf4j-log4j12:1.7.25")
  testCompile("junit:junit:4.12")
}

configure<JavaPluginConvention> {
  sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
  jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
  jvmTarget = "1.8"
}

application {
  mainClassName = "io.futz.circleci.client.MainKt"
}