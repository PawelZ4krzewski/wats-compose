package com.example.wats_compose.core

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.appendIfNameAbsent
import kotlinx.serialization.json.Json

object KtorClient {

    private val serializeJson = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(json = serializeJson)
        }

        install(HttpRequestRetry) {
            //function enables retrying a request if a 5xx response is received from a server and specifies the number of retries.
//            retryOnServerErrors(5)
            //specifies an exponential delay between retries, which is calculated using the Exponential backoff algorithm.
            exponentialDelay()
        }

        install(DefaultRequest) {
            headers.appendIfNameAbsent("X-custom-header", "Some Value")
            contentType(ContentType.Application.Json)
        }

        ResponseObserver {
            val timeDifference = it.responseTime.timestamp - it.requestTime.timestamp
            val protocolVersion = it.version
        }
    }

}