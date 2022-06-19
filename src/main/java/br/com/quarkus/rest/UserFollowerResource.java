package br.com.quarkus.rest;

import br.com.quarkus.rest.dto.request.CreateUserFollowerRequest;
import br.com.quarkus.rest.dto.request.DeleteUserFollowerRequest;
import br.com.quarkus.service.UserFollowerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserFollowerResource {

    private final UserFollowerService service;

    @Inject
    public UserFollowerResource(UserFollowerService service) {
        this.service = service;
    }

    @PUT
    public Response create(@PathParam("userId") Long userId, CreateUserFollowerRequest createUserFollowerRequest) {
        return this.service.create(userId, createUserFollowerRequest);
    }

    @GET
    public Response list(@PathParam("userId") Long userId) {
        return this.service.list(userId);
    }

    @DELETE
    public Response unFollower(@PathParam("userId") Long userId, DeleteUserFollowerRequest deleteUserFollowerRequest) {
        return this.service.unFollower(userId, deleteUserFollowerRequest);
    }

}
