package com.problem.customerrestapi.advice;

import com.problem.customerrestapi.errors.CustomerBadRequest;
import com.problem.customerrestapi.errors.CustomerNotFound;
import com.problem.customerrestapi.errors.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class CustomerControllerAdvice {

    @ExceptionHandler(CustomerBadRequest.class)
    ResponseEntity<ErrorResponse> customerBadRequestHandler(CustomerBadRequest ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ErrorResponse> customerBadRequestHandler(ConstraintViolationException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getConstraintViolations().stream().map((violation) -> violation.getPropertyPath().toString()).collect(Collectors.joining(", "))), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerNotFound.class)
    ResponseEntity<ErrorResponse> customerNotFoundHandler(CustomerNotFound ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
