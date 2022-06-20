package br.com.quarkus.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ResponseError {

    public static final int UNPROCESSABLE_ENTITY_STATUS = 422;
    public static final String UNPROCESSABLE_ENTITY_MESSAGE= "Validation Error";

    private String message;
    private Collection<FieldError> errors;

    public static <T> ResponseError createFromValidation(Set<ConstraintViolation<T>> violations) {
        List<FieldError> errors = violations
                .stream()
                .map(value -> new FieldError(value.getPropertyPath().toString(), value.getMessage()))
                .collect(Collectors.toList());

        ResponseError responseError = new ResponseError(ResponseError.UNPROCESSABLE_ENTITY_MESSAGE, errors);
        return responseError;
    }

    public Response withStatusCode(int code) {
        return Response.status(code).entity(this).build();
    }

}
