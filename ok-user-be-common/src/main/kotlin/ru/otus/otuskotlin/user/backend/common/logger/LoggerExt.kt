package ru.otus.otuskotlin.user.backend.common.logger

import net.logstash.logback.argument.StructuredArguments.keyValue
import org.slf4j.Logger
import org.slf4j.Marker
import org.slf4j.event.Level

suspend fun <T> Logger.doLoggingSusp(blockId: String, requestId: String, level: Level = Level.DEBUG, marker: Marker? = null, block: suspend (Logger) -> T): T {
    log(level, marker, "Entering {}, requestId {}", blockId, keyValue("requestId", requestId))
    return try {
        val res = block(this)
        log(level, marker, "Finished {}, requestId {}", blockId, keyValue("requestId", requestId))
        res
    } catch (e: Throwable) {
        error(marker, "FAILED {}, requestId {}", blockId, keyValue("requestId", requestId))
        throw e
    }
}

fun Logger.log(level: Level, marker: Marker?, message: String, vararg args: Any) = when(level) {
    Level.TRACE -> trace(marker, message, *args)
    Level.DEBUG -> debug(marker, message, *args)
    Level.INFO -> info(marker, message, *args)
    Level.WARN -> warn(marker, message, *args)
    Level.ERROR -> error(marker, message, *args)
}
