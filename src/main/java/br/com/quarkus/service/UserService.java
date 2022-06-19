package br.com.quarkus.service;

import br.com.quarkus.domain.model.User;
import br.com.quarkus.domain.repository.UserRepository;
import br.com.quarkus.rest.dto.CreateUserRequest;
import br.com.quarkus.rest.dto.ResponseError;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class UserService extends BaseService {

    private final UserRepository repository;

    @Inject
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Response list() {
        List<User> users = this.repository.listAll();
        return Response.ok(users).build();
    }

    @Transactional
    public Response create(CreateUserRequest userRequest) {
        Set<ConstraintViolation<CreateUserRequest>> violations = this.validator.validate(userRequest);

        if(!violations.isEmpty()) {
            return  ResponseError
                    .createFromValidation(violations).
                            withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }

        User user = new User();
        user.setAge(userRequest.getAge());
        user.setName(userRequest.getName());
        this.repository.persist(user);

        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .entity(user)
                .build();
    }

    @Transactional
    public Response delete(Long id) {
        User user = this.repository.findById(id);

        if(user != null) {
            this.repository.delete(user);
            return Response.noContent().build();
        }

        return Response
                .status(Response.Status.NOT_FOUND.getStatusCode())
                .build();
    }

    @Transactional
    public Response update(Long id, CreateUserRequest userRequest) {
        Set<ConstraintViolation<CreateUserRequest>> violations = this.validator.validate(userRequest);

        if(!violations.isEmpty()) {
            return  ResponseError
                    .createFromValidation(violations).
                            withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }

        User user = this.repository.findById(id);

        if(user != null) {
            user.setName(userRequest.getName());
            user.setAge(userRequest.getAge());
            this.repository.persist(user);
            return Response.noContent().build();
        }

        return Response
                .status(Response.Status.NOT_FOUND.getStatusCode())
                .build();
        }

}
