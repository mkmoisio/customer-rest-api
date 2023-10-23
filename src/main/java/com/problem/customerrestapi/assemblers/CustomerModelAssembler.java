package com.problem.customerrestapi.assemblers;

import com.problem.customerrestapi.controllers.CustomerController;
import com.problem.customerrestapi.entities.Customer;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerModelAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>> {


    @Override
    public EntityModel<Customer> toModel(Customer customer) {
        return EntityModel.of(customer, linkTo(methodOn(CustomerController.class).getCustomer(customer.getId())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).getCustomers()).withRel("customers"));
    }

}
