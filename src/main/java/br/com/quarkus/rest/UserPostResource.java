package br.com.quarkus.rest;

import br.com.quarkus.rest.dto.request.UpdateUserPostRequest;
import br.com.quarkus.service.UserPostService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserPostResource {

    private final UserPostService service;

    @Inject
    public UserPostResource(UserPostService service) {
        this.service = service;
    }

    @POST
    public Response create(@PathParam("userId") Long userId, UpdateUserPostRequest updateUserPostRequest) {
        return this.service.create(userId, updateUserPostRequest);
    }

    @GET
    public Response list(@PathParam("userId") Long userId, @HeaderParam("followerId") Long followerId) {
        return this.service.list(userId, followerId);
    }

}
