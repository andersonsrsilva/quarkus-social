package br.com.quarkus.rest.dto.resquest;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateUserPostRequest {

    @NotBlank(message = "Name is required")
    private String text;

}
