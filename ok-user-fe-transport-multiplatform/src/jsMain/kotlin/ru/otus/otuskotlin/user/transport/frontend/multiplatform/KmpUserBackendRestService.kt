package ru.otus.otuskotlin.user.transport.frontend.multiplatform

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.serializer
import org.w3c.dom.events.Event
import org.w3c.fetch.*
import org.w3c.xhr.XMLHttpRequest
import kotlin.browser.window
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.reflect.KClass

actual class KmpUserBackendRestService {

    private val jsonMapper = Json(JsonConfiguration.Default)

    actual suspend inline fun <reified T : Any, reified U : Any> request(endpoint: String, query: T): U = requestFull<T, U>(endpoint, query, T::class, U::class)

    @OptIn(ImplicitReflectionSerializer::class)
    actual suspend fun <T : Any, U : Any> requestFull(endpoint: String, query: T, queryClass: KClass<T>, responseClass: KClass<U>): U = suspendCoroutine { cont ->
//        println(jsonMapper.stringify<T>(queryClass.serializer(), query))
//        window
//                .fetch(
//                        endpoint,
//                        RequestInit(
//                                method = "POST",
////                                headers = mapOf("Content-Type" to "application/json; charset=utf-8"),
//                                body = jsonMapper.stringify<T>(queryClass.serializer(), query),
//                                mode = RequestMode.NO_CORS,
//                                cache = RequestCache.NO_CACHE,
//                                credentials = RequestCredentials.OMIT,
//                                redirect = RequestRedirect.FOLLOW,
//                                referrerPolicy = "no-referrer"
//                        )
//                )
//                .then { response ->
//                    println(response.ok)
//                    println(response.status)
//                    println(response.statusText)
//                    println(response.text())
////                    if (response.ok) {
////                        cont.resume(json.parse(responseClass.serializer(), (response.text()) .toString()))
////                    } else {
////                        cont.resumeWithException(RuntimeException(response.statusText))
////                    }
//                    response.text()
//                }
//                .then {
//                    println(it)
//                }
//                .catch { e ->
//                    cont.resumeWithException(e)
//                }

        val jsonString = jsonMapper.stringify<T>(queryClass.serializer(), query)
        XMLHttpRequest().apply {
            open("POST", endpoint, true)
            setRequestHeader("Content-type", "application/json");

            onreadystatechange = {
                //Call a function when the state changes.
                if (readyState == 4.toShort()) {
                    if (status == 200.toShort()) {
                        val result = jsonMapper.parse(responseClass.serializer(), responseText)
                        cont.resume(result)
                    } else {
                        cont.resumeWithException(RuntimeException("Error server query"))
                    }
                }
            }
            send(jsonString)
        }
    }
}
