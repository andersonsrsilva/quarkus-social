package br.com.quarkus.service;

import br.com.quarkus.domain.model.User;
import br.com.quarkus.domain.model.Post;
import br.com.quarkus.domain.repository.FollowerRepository;
import br.com.quarkus.domain.repository.PostRepository;
import br.com.quarkus.domain.repository.UserRepository;
import br.com.quarkus.rest.dto.ResponseError;
import br.com.quarkus.rest.dto.response.PostResponse;
import br.com.quarkus.rest.dto.request.UpdatePostRequest;

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
public class PostService {

    private final Validator validator;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FollowerRepository followerRepository;

    @Inject
    public PostService(Validator validator,
                       UserRepository userRepository,
                       PostRepository postRepository,
                       FollowerRepository followerRepository) {
        this.validator = validator;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.followerRepository = followerRepository;
    }

    public Response list(Long userId, Long followerId) {
        if(followerId == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST.getStatusCode())
                    .build();
        }

        User user = this.userRepository.findById(userId);

        if(user == null) {
            return Response
                    .status(Response.Status.NOT_FOUND.getStatusCode())
                    .build();
        }

        User follower = userRepository.findById(followerId);

        if(follower == null) {
            return Response
                    .status(Response.Status.NOT_FOUND.getStatusCode())
                    .build();
        }

        boolean followers = followerRepository.followers(follower, user);

        if(!followers) {
            return Response
                    .status(Response.Status.FORBIDDEN.getStatusCode())
                    .build();
        }

        var list= this.postRepository.postsByUser(user);
        List<PostResponse> userPostResponseList =  list.stream()
                //.map(post -> UserPostResponse.fromEntity(post))
                .map(PostResponse::fromEntity)
                .collect(Collectors.toList());
        return Response.ok(userPostResponseList).build();
    }

    @Transactional
    public Response create(Long userId, UpdatePostRequest updatePostRequest) {
        Set<ConstraintViolation<UpdatePostRequest>> violations = this.validator.validate(updatePostRequest);

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

        Post userPost = new Post();
        userPost.setUser(user);
        userPost.setText(updatePostRequest.getText());
        userPost.setDateTime(LocalDateTime.now());
        this.postRepository.persist(userPost);

        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .entity(userPost)
                .build();
    }

}
