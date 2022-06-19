package br.com.quarkus.service;

import br.com.quarkus.rest.dto.resquest.CreateUserRequest;
import br.com.quarkus.rest.dto.ResponseError;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.core.Response;
import java.util.Set;

@ApplicationScoped
public class BaseService {

    @Inject
    protected Validator validator;

    protected Response validatorRequest(CreateUserRequest userRequest) {
        Set<ConstraintViolation<CreateUserRequest>> violations = this.validator.validate(userRequest);

        if(!violations.isEmpty()) {
            return  ResponseError
                    .createFromValidation(violations).
                            withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }
        return null;
    }

}
