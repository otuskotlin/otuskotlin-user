package ru.otus.otuskotlin.user.backend.app.jetty

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import javax.ws.rs.ext.ContextResolver
import javax.ws.rs.ext.Provider

/**
 * Provide a custom ObjectMapper for JSON ser/deser.
 *
 * It is only necessary to write a provider if you want to configure your ObjectMapper; otherwise a default is used.
 */
@Provider
class KotlinSerializationProvider : ContextResolver<ObjectMapper> {
    private val objectMapper = ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT) // pretty-print
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // use iso-8601 for dates
            .registerModule(KotlinModule()) // this doesn't seem to be required to serialize our data class, but it is required to deserialize

    override fun getContext(type: Class<*>?): ObjectMapper = objectMapper
}


