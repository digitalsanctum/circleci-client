package io.futz.circleci.client

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory


class CircleCiClientFactory {
  fun getClient(): CircleCi {
    return getClient(HttpLoggingInterceptor.Level.BASIC, getObjectMapper())
  }

  fun getClient(loggingLevel: HttpLoggingInterceptor.Level): CircleCi {
    return getClient(loggingLevel, getObjectMapper())
  }

  private fun getObjectMapper(): ObjectMapper {
    val objectMapper = ObjectMapper()
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
    objectMapper.propertyNamingStrategy = PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES
    objectMapper.registerModule(JavaTimeModule())
    objectMapper.registerModule(KotlinModule())
    return objectMapper
  }

  private fun getClient(loggingLevel: HttpLoggingInterceptor.Level,
                        objectMapper: ObjectMapper): CircleCi {

    val logging = HttpLoggingInterceptor()
    logging.level = loggingLevel

    val token = System.getenv("CIRCLECI_TOKEN")
        ?: throw IllegalStateException("Missing token. Is CIRCLECI_TOKEN environment variable set?")

    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor { chain ->
          val originalRequest = chain.request()
          val url = originalRequest.url().newBuilder().addQueryParameter("circle-token", token).build()
          val augmentedRequest = originalRequest.newBuilder()
              .header("Accept", "application/json")
              .method(originalRequest.method(), originalRequest.body())
              .url(url)
              .build()
          chain.proceed(augmentedRequest)
        }
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://circleci.com/api/v1.1/")
        .client(client)
        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
        .build()

    return retrofit.create(CircleCi::class.java)
  }
}