package br.com.quarkus.rest.dto.resquest;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateUserPostRequest {

    @NotBlank(message = "Name is required")
    private String text;

}
