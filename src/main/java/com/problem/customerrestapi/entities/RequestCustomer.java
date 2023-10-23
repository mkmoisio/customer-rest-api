package com.problem.customerrestapi.entities;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RequestCustomer {

    @NotBlank()
    private String name;

    @Email
    private String email;

    private String website;

    private String businessId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }


    @Override
    public String toString() {
        return "RequestCustomer{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", website='" + website + '\'' +
                ", businessId='" + businessId + '\'' +
                '}';
    }
}
