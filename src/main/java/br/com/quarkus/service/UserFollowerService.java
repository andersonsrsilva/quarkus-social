package br.com.quarkus.service;

import br.com.quarkus.domain.model.User;
import br.com.quarkus.domain.model.UserFollower;
import br.com.quarkus.domain.repository.UserFollowerRepository;
import br.com.quarkus.domain.repository.UserRepository;
import br.com.quarkus.rest.dto.response.FollowerPerUserResponse;
import br.com.quarkus.rest.dto.response.FollowerResponse;
import br.com.quarkus.rest.dto.request.CreateUserFollowerRequest;
import br.com.quarkus.rest.dto.request.DeleteUserFollowerRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserFollowerService {

    private final Validator validator;
    private final UserRepository userRepository;
    private final UserFollowerRepository userFollowerRepository;

    @Inject
    public UserFollowerService(Validator validator, UserRepository userRepository, UserFollowerRepository userFollowerRepository) {
        this.validator = validator;
        this.userRepository = userRepository;
        this.userFollowerRepository = userFollowerRepository;
    }

    @Transactional
    public Response create(Long userId, CreateUserFollowerRequest createUserFollowerRequest) {

        if(userId.equals(createUserFollowerRequest.getFollowerId())) {
            return Response
                    .status(Response.Status.CONFLICT.getStatusCode())
                    .build();
        }

        User user = this.userRepository.findById(userId);

        if(user == null) {
            return Response
                    .status(Response.Status.NOT_FOUND.getStatusCode())
                    .build();
        }

        User follower = this.userRepository.findById(createUserFollowerRequest.getFollowerId());
        boolean followers = this.userFollowerRepository.followers(follower, user);

        if(!followers) {
            UserFollower userFollower = new UserFollower();
            userFollower.setUser(user);
            userFollower.setFollower(follower);
            this.userFollowerRepository.persist(userFollower);
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

        List<UserFollower> followers = this.userFollowerRepository.findByUser(userId);

        FollowerPerUserResponse followerPerUserResponse = new FollowerPerUserResponse();
        followerPerUserResponse.setFollowersCount(followers.size());

        List<FollowerResponse> followerResponses = followers.stream().map(FollowerResponse::new).collect(Collectors.toList());
        followerPerUserResponse.setContent(followerResponses);

        return Response.ok(followerPerUserResponse).build();
    }

    @Transactional
    public Response unFollower(Long userId, DeleteUserFollowerRequest deleteUserFollowerRequest) {
        User user = this.userRepository.findById(userId);

        if(user == null) {
            return Response
                    .status(Response.Status.NOT_FOUND.getStatusCode())
                    .build();
        }

        this.userFollowerRepository.deleteFollower(deleteUserFollowerRequest.getFollowerId(), userId);

        return Response
                .status(Response.Status.NO_CONTENT.getStatusCode())
                .build();
    }

}
