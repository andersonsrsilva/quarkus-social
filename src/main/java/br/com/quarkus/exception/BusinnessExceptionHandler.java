package br.com.quarkus.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BusinnessExceptionHandler implements ExceptionMapper<BusinessException> {

    public static final int UNPROCESSABLE_ENTITY_STATUS = 422;

    @Override
    public Response toResponse(BusinessException e) {
        return Response.status(UNPROCESSABLE_ENTITY_STATUS).entity(new ErrorMessage(e.getMessage())).build();
   }

}