package com.skettle.record_shop_api.exceptions;

public class AlbumNotFoundException extends RuntimeException {
    public AlbumNotFoundException(String message){
        super(message);
    }
}
