package br.com.quarkus.rest;

import br.com.quarkus.rest.dto.request.UpdatePostRequest;
import br.com.quarkus.service.PostService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    private final PostService service;

    @Inject
    public PostResource(PostService service) {
        this.service = service;
    }

    @POST
    public Response create(@PathParam("userId") Long userId, UpdatePostRequest updatePostRequest) {
        return this.service.create(userId, updatePostRequest);
    }

    @GET
    public Response list(@PathParam("userId") Long userId, @HeaderParam("followerId") Long followerId) {
        return this.service.list(userId, followerId);
    }

}
