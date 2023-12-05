package com.example.pawsicare.business.exceptions;


public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException(){super("UNAUTHORIZED_USER");}
}
