package com.microservice.microservicebus.handlerexception;

import com.microservice.microservicebus.exception.BadRequestException;
import com.microservice.microservicebus.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {HttpServerErrorException.InternalServerError.class})
    protected ResponseEntity<Object> handleConflictInternalServerError(HttpServerErrorException.InternalServerError exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {HttpClientErrorException.BadRequest.class})
    protected ResponseEntity<Object> handleConflictBadRequest(HttpClientErrorException.BadRequest exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    protected ResponseEntity<Object> handleConflictBadRequest(BadRequestException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {HttpClientErrorException.Unauthorized.class})
    protected ResponseEntity<Object> handleConflictUnauthorized(HttpClientErrorException.Unauthorized exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {HttpClientErrorException.Forbidden.class})
    protected ResponseEntity<Object> handleConflictForbidden(HttpClientErrorException.Forbidden exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {HttpClientErrorException.NotFound.class})
    protected ResponseEntity<Object> handleConflictNotFound(HttpClientErrorException.NotFound exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<Object> handleConflictNotFound(NotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {HttpServerErrorException.BadGateway.class})
    protected ResponseEntity<Object> handleConflictBadGateway(HttpServerErrorException.BadGateway exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(value = {ResourceAccessException.class})
    protected ResponseEntity<Object> handleConflictResourceAccessException(ResourceAccessException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }

}
