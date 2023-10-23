package com.problem.customerrestapi.services;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.problem.customerrestapi.entities.Customer;
import com.problem.customerrestapi.entities.KLApiResponse;
import com.problem.customerrestapi.entities.RequestCustomer;
import com.problem.customerrestapi.errors.CustomerBadRequest;
import com.problem.customerrestapi.utils.Validate;
import org.jetbrains.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;


@Service
public class CustomerService {

    private final WebClient webClient;
    private final ModelMapper modelMapper = new ModelMapper();
    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    public CustomerService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://www.kauppalehti.fi/company-api/basic-info").build();
        modelMapper.typeMap(RequestCustomer.class, Customer.class).addMappings(mapper -> mapper.skip(Customer::setId));
    }

    private KLApiResponse retrieve(String businessId) {
        return this.webClient.get().uri("/{businessId}", businessId).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(KLApiResponse.class).block();
    }


    public Customer createCustomer(RequestCustomer requestCustomer) {

        if (requestCustomer.getBusinessId() == null) {
            return modelMapper.map(requestCustomer, Customer.class);
        } else if (!Validate.validateBusinessId(requestCustomer.getBusinessId())) {
            throw new CustomerBadRequest("businessId");
        }
        Customer customer = modelMapper.map(requestCustomer, Customer.class);

        KLApiResponse response;
        try {
            response = this.retrieve(customer.getBusinessId());
        } catch (WebClientException ex) {
            // If request fails we assume businessId was wrong and thus return an error
            throw new CustomerBadRequest("businessId");
        }

        String streetAddress = response.getStreetAddressActual();
        String phoneNumber = response.getPhoneNumber();
        customer.setStreetAddress(streetAddress);
        customer.setPhoneNumber(convertPhoneNumber(phoneNumber));
        return customer;

    }

    private @Nullable String convertPhoneNumber(String phoneNumber) {
        try {
            Phonenumber.PhoneNumber number = new Phonenumber.PhoneNumber();
            PhoneNumberUtil.getInstance().parse(phoneNumber, "FI", number);
            return phoneNumberUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
        } catch (NumberParseException ex) {
            // Number was garbage, dismiss
            return null;
        }
    }

}






