package br.com.quarkus.service;

import br.com.quarkus.domain.model.User;
import br.com.quarkus.domain.model.UserPost;
import br.com.quarkus.domain.repository.UserPostRepository;
import br.com.quarkus.domain.repository.UserRepository;
import br.com.quarkus.rest.dto.CreateUserPostRequest;
import br.com.quarkus.rest.dto.ResponseError;
import br.com.quarkus.rest.dto.UserPostResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserPostService {

    private final Validator validator;
    private final UserPostRepository userPostRepository;
    private final UserRepository userRepository;

    @Inject
    public UserPostService(Validator validator, UserPostRepository userPostRepository, UserRepository userRepository) {
        this.validator = validator;
        this.userPostRepository = userPostRepository;
        this.userRepository = userRepository;
    }

    public Response list(Long userId) {
        User user = this.userRepository.findById(userId);

        if(user == null) {
            return Response
                    .status(Response.Status.NOT_FOUND.getStatusCode())
                    .build();
        }

        var list= this.userPostRepository.postsByUser(user);
        var userPostResponseList =  list.stream()
                //.map(post -> UserPostResponse.fromEntity(post))
                .map(UserPostResponse::fromEntity)
                .collect(Collectors.toList());
        return Response.ok(userPostResponseList).build();
    }

    @Transactional
    public Response create(Long userId, CreateUserPostRequest userPostRequest) {
        Set<ConstraintViolation<CreateUserPostRequest>> violations = this.validator.validate(userPostRequest);

        if(!violations.isEmpty()) {
            return  ResponseError
                    .createFromValidation(violations).
                            withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }

        User user = this.userRepository.findById(userId);

        if(user == null) {
            return Response
                    .status(Response.Status.NOT_FOUND.getStatusCode())
                    .build();
        }

        UserPost userPost = new UserPost();
        userPost.setUser(user);
        userPost.setText(userPostRequest.getText());
        userPost.setDateTime(LocalDateTime.now());
        this.userPostRepository.persist(userPost);

        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .entity(userPost)
                .build();
    }

}
