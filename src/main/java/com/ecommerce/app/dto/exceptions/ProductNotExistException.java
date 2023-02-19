package com.ecommerce.app.dto.exceptions;

public class ProductNotExistException extends IllegalArgumentException {
    public ProductNotExistException(String msg){
        super(msg);
    }

}
