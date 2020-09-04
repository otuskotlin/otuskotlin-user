package ru.otus.otuskotlin.user

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import kotlinx.serialization.json.Json
import ru.otus.otuskotlin.user.transport.multiplatform.models.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    val service = KmpUserService()

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }
    install(ContentNegotiation) {
        json(
                contentType = Json,
                json = Json {
                    prettyPrint = true
                }
        )
    }

    routing {
        static("/") {
            defaultResource("static/index.html")
            resources("static")
        }

        route("/api") {
            post("/get") {
                val query = call.receive<KmpUserGet>()
                call.respond(service.get(query))
            }
            post("/index") {
                val query = call.receive<KmpUserIndex>()
                call.respond(service.index(query))
            }
            post("/create") {
                val query = call.receive<KmpUserCreate>()
                call.respond(service.create(query))
            }
            post("/update") {
                val query = call.receive<KmpUserUpdate>()
                call.respond(service.update(query))
            }
            post("/delete") {
                val query = call.receive<KmpUserDelete>()
                call.respond(service.delete(query))
            }
        }
    }
}

