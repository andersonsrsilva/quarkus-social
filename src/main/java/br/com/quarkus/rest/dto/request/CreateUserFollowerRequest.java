package br.com.quarkus.rest.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateUserFollowerRequest {

    @NotBlank(message = "Id follower is required")
    private Long followerId;

}
