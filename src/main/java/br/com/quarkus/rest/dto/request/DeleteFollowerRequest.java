package br.com.quarkus.rest.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeleteFollowerRequest {

    @NotBlank(message = "Id follower is required")
    private Long followerId;

}