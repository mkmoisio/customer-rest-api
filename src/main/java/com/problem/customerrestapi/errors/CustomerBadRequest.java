package com.problem.customerrestapi.errors;

public class CustomerBadRequest extends RuntimeException{

    public CustomerBadRequest(String field) {
        super(field);
    }
}
