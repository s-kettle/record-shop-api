package com.skettle.record_shop_api.exceptions;

public class GenreNotAllowedException extends RuntimeException {
    public GenreNotAllowedException(String message){
        super(message);
    }
}
