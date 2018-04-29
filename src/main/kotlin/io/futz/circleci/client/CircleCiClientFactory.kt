package io.futz.circleci.client

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.futz.circleci.model.ApiResponseError
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory


class CircleCiClientFactory {

  fun getClient(): CircleCi = getClient(HttpLoggingInterceptor.Level.BASIC, getObjectMapper())

  fun getClient(loggingLevel: HttpLoggingInterceptor.Level): CircleCi
      = getClient(loggingLevel, getObjectMapper())

  private fun getObjectMapper(): ObjectMapper {
    val objectMapper = ObjectMapper()
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
    objectMapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
    objectMapper.registerModule(JavaTimeModule())
    objectMapper.registerModule(KotlinModule())
    return objectMapper
  }

  private fun getClient(loggingLevel: HttpLoggingInterceptor.Level,
                        objectMapper: ObjectMapper): CircleCi {
    val retrofit = getRetrofit(loggingLevel, objectMapper)
    return retrofit.create(CircleCi::class.java)
  }

  private fun getRetrofit(): Retrofit = getRetrofit(HttpLoggingInterceptor.Level.BODY, getObjectMapper())

  private fun getRetrofit(loggingLevel: HttpLoggingInterceptor.Level, objectMapper: ObjectMapper): Retrofit {

    val token = System.getenv("CIRCLECI_TOKEN")
        ?: throw IllegalStateException("Missing token. Is CIRCLECI_TOKEN environment variable set?")

    val logging = HttpLoggingInterceptor()
    logging.level = loggingLevel

    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor { chain ->
          val originalRequest = chain.request()
          val url = originalRequest.url().newBuilder().addQueryParameter("circle-token", token).build()
          val augmentedRequest = originalRequest.newBuilder()
              .header("Accept", "application/json")
              .header("Content-Type", "application/json")
              .method(originalRequest.method(), originalRequest.body())
              .url(url)
              .build()
          chain.proceed(augmentedRequest)
        }
        .build()

    return Retrofit.Builder()
        .baseUrl("https://circleci.com/api/v1.1/")
        .client(client)
        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
        .build()
  }

  fun getErrorConverter(): Converter<ResponseBody, ApiResponseError> {
    return getRetrofit().responseBodyConverter<ApiResponseError>(ApiResponseError::class.java, arrayOf())
  }
}