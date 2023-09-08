package io.codelex.flightplanner.Errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class ValidationException extends RuntimeException {
    public ValidationException(HttpStatusCode httpStatus, String message) {
        super(message);
    }
}
