package com.ecommerce.app.dto.exceptions;

public class AuthentificationFailException extends IllegalArgumentException{
    public AuthentificationFailException(String msg){
        super(msg);
    }
}
