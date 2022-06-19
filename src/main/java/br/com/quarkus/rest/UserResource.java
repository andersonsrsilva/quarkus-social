package br.com.quarkus.rest;

import br.com.quarkus.rest.dto.CreateUserRequest;
import br.com.quarkus.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService service;

    @Inject
    public UserResource(UserService service) {
        this.service = service;
    }

    @POST
    public Response create(CreateUserRequest userRequest) {
        return this.service.create(userRequest);
    }

    @GET
    public Response list() {
        return this.service.list();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        return this.service.delete(id);
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, CreateUserRequest userRequest) {
        return this.service.update(id, userRequest);
    }

}
