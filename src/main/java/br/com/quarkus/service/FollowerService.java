package br.com.quarkus.service;

import br.com.quarkus.domain.model.Follower;
import br.com.quarkus.domain.model.User;
import br.com.quarkus.domain.repository.FollowerRepository;
import br.com.quarkus.domain.repository.UserRepository;
import br.com.quarkus.rest.dto.ResponseError;
import br.com.quarkus.rest.dto.request.CreateFollowerRequest;
import br.com.quarkus.rest.dto.request.DeleteFollowerRequest;
import br.com.quarkus.rest.dto.response.FollowerPerUserResponse;
import br.com.quarkus.rest.dto.response.FollowerResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class FollowerService {

    private final Validator validator;
    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;

    @Inject
    public FollowerService(Validator validator, UserRepository userRepository, FollowerRepository followerRepository) {
        this.validator = validator;
        this.userRepository = userRepository;
        this.followerRepository = followerRepository;
    }

    @Transactional
    public Response create(Long userId, CreateFollowerRequest createFollowerRequest) {
        Set<ConstraintViolation<CreateFollowerRequest>> violations = this.validator.validate(createFollowerRequest);

        if(!violations.isEmpty()) {
            return  ResponseError
                    .createFromValidation(violations).
                            withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }

        if(userId.equals(createFollowerRequest.getFollowerId())) {
            return Response
                    .status(Response.Status.CONFLICT.getStatusCode())
                    .entity("You can't follow yourself")
                    .build();
        }

        User user = this.userRepository.findById(userId);

        if(user == null) {
            return Response
                    .status(Response.Status.NOT_FOUND.getStatusCode())
                    .build();
        }

        User follower = this.userRepository.findById(createFollowerRequest.getFollowerId());
        boolean followers = this.followerRepository.followers(follower, user);

        if(!followers) {
            Follower userFollower = new Follower();
            userFollower.setUser(user);
            userFollower.setFollower(follower);
            this.followerRepository.persist(userFollower);
        }

        return Response
                .status(Response.Status.NO_CONTENT.getStatusCode())
                .build();
    }

    public Response list(Long userId) {
        User user = this.userRepository.findById(userId);

        if(user == null) {
            return Response
                    .status(Response.Status.NOT_FOUND.getStatusCode())
                    .build();
        }

        List<Follower> followers = this.followerRepository.findByUser(userId);

        FollowerPerUserResponse followerPerUserResponse = new FollowerPerUserResponse();
        followerPerUserResponse.setFollowersCount(followers.size());

        List<FollowerResponse> followerResponses = followers.stream().map(FollowerResponse::new).collect(Collectors.toList());
        followerPerUserResponse.setContent(followerResponses);

        return Response.ok(followerPerUserResponse).build();
    }

    @Transactional
    public Response unFollower(Long userId, DeleteFollowerRequest deleteFollowerRequest) {
        Set<ConstraintViolation<DeleteFollowerRequest>> violations = this.validator.validate(deleteFollowerRequest);

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

        User follower = this.userRepository.findById(deleteFollowerRequest.getFollowerId());

        if(follower == null) {
            return Response
                    .status(Response.Status.NOT_FOUND.getStatusCode())
                    .build();
        }

        this.followerRepository.deleteFollower(follower, user);

        return Response
                .status(Response.Status.NO_CONTENT.getStatusCode())
                .build();
    }

}
