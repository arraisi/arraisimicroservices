package com.arraisicode.customer;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email
) {
}
