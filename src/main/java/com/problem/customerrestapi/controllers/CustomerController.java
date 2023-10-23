package com.problem.customerrestapi.controllers;

import com.problem.customerrestapi.assemblers.CustomerModelAssembler;
import com.problem.customerrestapi.entities.Customer;
import com.problem.customerrestapi.entities.RequestCustomer;
import com.problem.customerrestapi.errors.CustomerNotFound;
import com.problem.customerrestapi.repositories.CustomerRepository;
import com.problem.customerrestapi.services.CustomerService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CustomerController {
    private final CustomerRepository repository;
    private final CustomerModelAssembler assembler;

    private final CustomerService customerService;


    public CustomerController(CustomerRepository repository, CustomerModelAssembler assembler, CustomerService customerService) {
        this.repository = repository;
        this.assembler = assembler;
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    public ResponseEntity<EntityModel<Customer>> postCustomer(@RequestBody RequestCustomer requestCustomer) {

        Customer saveableCustomer = this.customerService.createCustomer(requestCustomer);

        EntityModel<Customer> savedCustomer = assembler.toModel(repository.save(saveableCustomer));
        return ResponseEntity.created(savedCustomer.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(savedCustomer);
    }

    @GetMapping("/customers")
    public CollectionModel<EntityModel<Customer>> getCustomers() {
        List<EntityModel<Customer>> customerEntityModels = repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(customerEntityModels, linkTo(methodOn(CustomerController.class).getCustomers()).withSelfRel());
    }

    @GetMapping("/customers/{id}")
    public EntityModel<Customer> getCustomer(@PathVariable Long id) {
        Customer customer = repository.findById(id).orElseThrow(() -> new CustomerNotFound(id));
        return assembler.toModel(customer);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new CustomerNotFound(id);
        }

    }

}
