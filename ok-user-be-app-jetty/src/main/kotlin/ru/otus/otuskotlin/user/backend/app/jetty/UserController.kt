package ru.otus.otuskotlin.user.backend.app.jetty

import ru.otus.otuskotlin.user.transport.multiplatform.models.*
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("user")
class UserController {

    private val service = JettyUserService()

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("get")
    fun get(query: KmpUserGet): KmpUserResponseItem = service.get(query)

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("index")
    fun index(query: KmpUserIndex): KmpUserResponseIndex = service.index(query)

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("create")
    fun create(query: KmpUserCreate): KmpUserResponseItem = service.create(query)

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("update")
    fun update(query: KmpUserUpdate): KmpUserResponseItem = service.update(query)

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("delete")
    fun delete(query: KmpUserDelete): KmpUserResponseItem = service.delete(query)
}
