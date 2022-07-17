package com.example.digiseq.exceptions;

import com.example.digiseq.domain.Organisation;

public class OrganisationNotFoundException extends RuntimeException{
    public OrganisationNotFoundException(Long id) {
        super("Could not find organisation with ID: " + id);
    }
}