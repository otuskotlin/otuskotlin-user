package ru.otus.otuskotlin.user.app.kotless

import io.kotless.dsl.lang.KotlessContext
import io.kotless.dsl.lang.http.Post
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import ru.otus.otuskotlin.user.transport.multiplatform.backend.KmpUserService
import ru.otus.otuskotlin.user.transport.multiplatform.models.*

private val service = KmpUserService()
private val log = LoggerFactory.getLogger("UserController")

@UnstableDefault
@ImplicitReflectionSerializer
@Post("/api/get")
fun get(): String? = handle { query: KmpUserGet ->
    service.get(query)
}

@UnstableDefault
@ImplicitReflectionSerializer
@Post("/api/index")
fun index(): String? = handle { query: KmpUserIndex ->
    service.index(query)
}

@UnstableDefault
@ImplicitReflectionSerializer
@Post("/api/create")
fun create(): String? = handle { query: KmpUserCreate ->
    service.create(query)
}

@UnstableDefault
@ImplicitReflectionSerializer
@Post("/api/update")
fun update(): String? = handle { query: KmpUserUpdate ->
    service.update(query)
}

@UnstableDefault
@ImplicitReflectionSerializer
@Post("/api/delete")
fun delete(): String? = handle { query: KmpUserDelete ->
    service.delete(query)
}

@ImplicitReflectionSerializer
@UnstableDefault
@OptIn(InternalSerializationApi::class)
private inline fun <reified T: Any, reified R: Any> handle(crossinline block: suspend (T) -> R): String? = KotlessContext
        .HTTP
        .request
        .myBody
        ?.let { q ->
            runBlocking {
                log.debug("Handling query: {}", q)
                val query = Json.parse<T>(q)
                val result = block(query)
                Json.stringify(result).also { r ->
                    log.debug("Sending response: {}", r)
                }
            }
        }
