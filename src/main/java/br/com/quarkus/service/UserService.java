package br.com.quarkus.service;

import br.com.quarkus.domain.model.User;
import br.com.quarkus.domain.repository.UserRepository;
import br.com.quarkus.rest.dto.resquest.CreateUserRequest;
import br.com.quarkus.rest.dto.ResponseError;
import br.com.quarkus.rest.dto.resquest.UpdateUserRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class UserService extends BaseService {

    private final Validator validator;
    private final UserRepository userRepository;

    @Inject
    public UserService(Validator validator, UserRepository userRepository) {
        this.validator = validator;
        this.userRepository = userRepository;
    }

    public Response list() {
        List<User> users = this.userRepository.listAll();
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
        this.userRepository.persist(user);

        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .entity(user)
                .build();
    }

    @Transactional
    public Response delete(Long id) {
        User user = this.userRepository.findById(id);

        if(user != null) {
            this.userRepository.delete(user);
            return Response.noContent().build();
        }

        return Response
                .status(Response.Status.NOT_FOUND.getStatusCode())
                .build();
    }

    @Transactional
    public Response update(Long id, UpdateUserRequest updateUserRequest) {
        Set<ConstraintViolation<UpdateUserRequest>> violations = this.validator.validate(updateUserRequest);

        if(!violations.isEmpty()) {
            return  ResponseError
                    .createFromValidation(violations).
                            withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }

        User user = this.userRepository.findById(id);

        if(user != null) {
            user.setName(updateUserRequest.getName());
            user.setAge(updateUserRequest.getAge());
            this.userRepository.persist(user);
            return Response.noContent().build();
        }

        return Response
                .status(Response.Status.NOT_FOUND.getStatusCode())
                .build();
        }

}
