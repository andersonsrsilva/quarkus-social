package br.com.quarkus.rest.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DeleteFollowerRequest {

    @NotNull(message = "Id follower is required")
    private Long followerId;

}
