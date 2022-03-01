package com.setronica.intern.test.project.exception;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

public class ResourceQueryNotFoundException extends ResourceNotFoundException {

    public ResourceQueryNotFoundException(String message) {
        super(message);
    }
}
