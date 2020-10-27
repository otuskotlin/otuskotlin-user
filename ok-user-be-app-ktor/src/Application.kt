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
import io.ktor.util.pipeline.*
import kotlinx.serialization.json.Json
import net.logstash.logback.argument.StructuredArguments.keyValue
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.otus.otuskotlin.backend.repository.cassandra.UserRepositoryCassandra
import ru.otus.otuskotlin.backend.repository.inmemory.UserRepositoryInMemoty
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.UserContextStatus
import ru.otus.otuskotlin.user.backend.common.errors.QueryParseError
import ru.otus.otuskotlin.user.backend.common.logger.doLoggingSusp
import ru.otus.otuskotlin.user.backend.logics.UserCrud
import ru.otus.otuskotlin.user.configs.CassandraConfig
import ru.otus.otuskotlin.user.transport.multiplatform.backend.resultIndex
import ru.otus.otuskotlin.user.transport.multiplatform.backend.resultItem
import ru.otus.otuskotlin.user.transport.multiplatform.models.*
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@OptIn(ExperimentalTime::class)
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    val cassandraConfig = CassandraConfig(environment)
    val userRepoTest = UserRepositoryInMemoty(ttl = 2.toDuration(DurationUnit.HOURS))
    val userRepoProd = UserRepositoryCassandra(
            keyspace = cassandraConfig.keyspace,
            hosts = cassandraConfig.hosts,
            port = cassandraConfig.port,
            user = cassandraConfig.user,
            pass = cassandraConfig.pass
    )
    val crud = UserCrud(
            userRepoTest = userRepoTest,
            userRepoProd = userRepoProd
    )
    val service = KmpUserService(crud = crud)
    val logger = LoggerFactory.getLogger(::main::class.java)

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
                request<KmpUserGet, KmpUserResponseItem>("user-get", logger) {
                    service.get(it)
                }
            }
            post("/index") {
                request<KmpUserIndex, KmpUserResponseIndex>("user-index", logger) {
                    service.index(it)
                }
            }
            post("/create") {
                request<KmpUserCreate, KmpUserResponseItem>("user-create", logger) {
                    service.create(it)
                }
            }
            post("/update") {
                request<KmpUserUpdate, KmpUserResponseItem>("user-update", logger) {
                    service.update(it)
                }
            }
            post("/delete") {
                request<KmpUserDelete, KmpUserResponseItem>("user-delete", logger) {
                    service.delete(it)
                }
            }
        }
    }
}

suspend inline fun <reified T: Any, reified K: KmpUserResponse> PipelineContext<Unit, ApplicationCall>.request(
        logId: String,
        logger: Logger,
        crossinline block: suspend (T) -> K
) {
    try {
        logger.doLoggingSusp(logId) {
            val query = call.receive<T>()
            logger.info("Query for $logId", keyValue("data", query))
            val response = block(query)
            call.respond(response)
            logger.info("Response for $logId", keyValue("data", response))
        }

    } catch (e: Throwable) {
        logger.doLoggingSusp("$logId-error") {
            val ctx = UserContext(
                    errors = mutableListOf(QueryParseError(code = "$logId-parse-error", e = e)),
                    status = UserContextStatus.ERROR
            )
            val res = when (K::class) {
                KmpUserResponseIndex::class -> ctx.resultIndex()
                else -> ctx.resultItem()
            }
            call.respond(res)
        }
    }
}
