package com.arraisicode.customer.service;

import com.arraisicode.customer.controller.request.CustomerRegistrationRequest;
import com.arraisicode.customer.model.Customer;
import com.arraisicode.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // todo: check if email valid
        // todo: check if email not taken
        // todo: store customer in db
        customerRepository.save(customer);
    }
}
