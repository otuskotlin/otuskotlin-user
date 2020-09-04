package ru.otus.otuskotlin.user.backend.app.jetty

import org.eclipse.jetty.server.Server
import org.glassfish.jersey.jetty.JettyHttpContainerFactory
import org.glassfish.jersey.server.ResourceConfig
import javax.ws.rs.core.UriBuilder

fun main(args: Array<String>) {
    println("Starting up")

    val baseUri = UriBuilder.fromUri("http://localhost/").port(8080).build()
    val config = ResourceConfig()
            .register(CORSResponseFilter())
            .register(UserController())

    JettyHttpContainerFactory.createServer(baseUri, config).use { server ->
        server.join()
    }
}

private inline fun Server.use(block: (Server) -> Unit) = try {
    block(this)
} finally {
    this.destroy()
}
