package com.example.pawsicare.business.exceptions;

public class RefreshTokenExpiredException extends RuntimeException{
    public RefreshTokenExpiredException(String msg){
        super(msg);
    }
}
