package ru.otus.otuskotlin.user.transport.frontend.multiplatform

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.serializer
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.reflect.KClass

actual class KmpUserBackendRestService {

    private val jsonMapper = Json(JsonConfiguration.Default)
    private val httpClient = HttpClient.newHttpClient()

    actual suspend inline fun <reified T : Any, reified U : Any> request(endpoint: String, query: T): U = requestFull(endpoint, query, T::class, U::class)

    @OptIn(ImplicitReflectionSerializer::class)
    actual suspend fun <T : Any, U : Any> requestFull(endpoint: String, query: T, queryClass: KClass<T>, responseClass: KClass<U>): U {
        val jsonString = jsonMapper.stringify(queryClass.serializer(), query)
        val request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .setHeader("Content-Type", "application/json; charset=utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build()
        return suspendCoroutine { cont ->
            try {
                val response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())!!
                val responseString = response.get().body()
                cont.resume(jsonMapper.parse<U>(responseClass.serializer(), responseString))
            } catch (e: Throwable) {
                cont.resumeWithException(e)
            }
        }
    }
}
