package ru.otus.otuskotlin.user.backend.app.jetty

import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter

class CORSResponseFilter: ContainerResponseFilter {

    override fun filter(requestContext: ContainerRequestContext, responseContext: ContainerResponseContext) {

        responseContext.getHeaders().apply {
            add("Access-Control-Allow-Origin", "*");
            //headers.add("Access-Control-Allow-Origin", "http://podcastpedia.org"); //allows CORS requests only coming from podcastpedia.org
            add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
            add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type");
        }

    }
}
