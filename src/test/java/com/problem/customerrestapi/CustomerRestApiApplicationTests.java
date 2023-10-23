package com.problem.customerrestapi;

import static org.assertj.core.api.Assertions.assertThat;

import com.problem.customerrestapi.controllers.CustomerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerRestApiApplicationTests {
    @Autowired
    private CustomerController controller;

    @Test
    void contextLoads() {

        assertThat(controller).isNotNull();
    }





}
