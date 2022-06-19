package br.com.quarkus.rest.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateUserPostRequest {

    @NotBlank(message = "Name is required")
    private String text;

}
