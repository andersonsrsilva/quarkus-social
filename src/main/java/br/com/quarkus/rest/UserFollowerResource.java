package br.com.quarkus.rest;

import br.com.quarkus.rest.dto.resquest.CreateUserFollowerRequest;
import br.com.quarkus.rest.dto.resquest.UpdateUserPostRequest;
import br.com.quarkus.service.UserFollowerService;
import br.com.quarkus.service.UserPostService;

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
    public Response list(@PathParam("userId") Long userId, CreateUserFollowerRequest createUserFollowerRequest) {
        return this.service.create(userId, createUserFollowerRequest);
    }

    @DELETE
    public Response delete(@PathParam("userId") Long userId, CreateUserFollowerRequest createUserFollowerRequest) {
        return this.service.create(userId, createUserFollowerRequest);
    }

}
