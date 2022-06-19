package br.com.quarkus.service;

import br.com.quarkus.domain.model.User;
import br.com.quarkus.domain.model.UserPost;
import br.com.quarkus.domain.repository.UserPostRepository;
import br.com.quarkus.domain.repository.UserRepository;
import br.com.quarkus.rest.dto.ResponseError;
import br.com.quarkus.rest.dto.response.UserPostResponse;
import br.com.quarkus.rest.dto.resquest.UpdateUserPostRequest;

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
    private final UserRepository userRepository;
    private final UserPostRepository userPostRepository;

    @Inject
    public UserPostService(Validator validator, UserRepository userRepository, UserPostRepository userPostRepository) {
        this.validator = validator;
        this.userRepository = userRepository;
        this.userPostRepository = userPostRepository;
    }

    public Response list(Long userId) {
        User user = this.userRepository.findById(userId);

        if(user == null) {
            return Response
                    .status(Response.Status.NOT_FOUND.getStatusCode())
                    .build();
        }

        var list= this.userPostRepository.postsByUser(user);
        List<UserPostResponse> userPostResponseList =  list.stream()
                //.map(post -> UserPostResponse.fromEntity(post))
                .map(UserPostResponse::fromEntity)
                .collect(Collectors.toList());
        return Response.ok(userPostResponseList).build();
    }

    @Transactional
    public Response create(Long userId, UpdateUserPostRequest updateUserPostRequest) {
        Set<ConstraintViolation<UpdateUserPostRequest>> violations = this.validator.validate(updateUserPostRequest);

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
        userPost.setText(updateUserPostRequest.getText());
        userPost.setDateTime(LocalDateTime.now());
        this.userPostRepository.persist(userPost);

        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .entity(userPost)
                .build();
    }

}
