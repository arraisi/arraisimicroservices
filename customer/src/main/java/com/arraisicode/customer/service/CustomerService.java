package com.arraisicode.customer.service;

import com.arraisicode.clients.fraud.FraudCheckResponse;
import com.arraisicode.clients.fraud.FraudClient;
import com.arraisicode.clients.notification.NotificationClient;
import com.arraisicode.clients.notification.NotificationRequest;
import com.arraisicode.customer.controller.request.CustomerRegistrationRequest;
import com.arraisicode.customer.model.Customer;
import com.arraisicode.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        // todo: check if email valid
        // todo: check if email not taken
        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        // todo: make it async. i.e. add to queue
        notificationClient.send(
                new NotificationRequest(
                        customer.getId(),
                        customer.getEmail(),
                        String.format("Hi %s, welcome to Arraisicode...", customer.getFirstName())
                )
        );

    }
}
