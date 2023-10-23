package com.problem.customerrestapi.errors;

public class ErrorResponse {

    public ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


    String error;

}
