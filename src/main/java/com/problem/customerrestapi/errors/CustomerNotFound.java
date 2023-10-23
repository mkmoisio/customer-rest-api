package com.problem.customerrestapi.errors;

public class CustomerNotFound extends RuntimeException {

    public CustomerNotFound(Long id) {
        super("Not found id " + id);
    }
}
