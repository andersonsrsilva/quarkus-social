package br.com.quarkus.rest.dto.response;

import br.com.quarkus.domain.model.Follower;
import lombok.Data;

@Data
public class FollowerResponse {

    private Long id;
    private String name;

    public FollowerResponse() {
    }

    public FollowerResponse(Follower userFollower) {
        this(userFollower.getId(), userFollower.getFollower().getName());
    }

    public FollowerResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
