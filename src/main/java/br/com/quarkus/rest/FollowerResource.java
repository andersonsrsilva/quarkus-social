package br.com.quarkus.rest;

import br.com.quarkus.rest.dto.request.CreateFollowerRequest;
import br.com.quarkus.rest.dto.request.DeleteFollowerRequest;
import br.com.quarkus.service.FollowerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {

    private final FollowerService service;

    @Inject
    public FollowerResource(FollowerService service) {
        this.service = service;
    }

    @PUT
    public Response create(@PathParam("userId") Long userId, CreateFollowerRequest createUserFollowerRequest) {
        return this.service.create(userId, createUserFollowerRequest);
    }

    @GET
    public Response list(@PathParam("userId") Long userId) {
        return this.service.list(userId);
    }

    @DELETE
    public Response unFollower(@PathParam("userId") Long userId, DeleteFollowerRequest deleteUserFollowerRequest) {
        return this.service.unFollower(userId, deleteUserFollowerRequest);
    }

}
