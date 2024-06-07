package com.skettle.record_shop_api.exceptions;

public class AlbumAlreadyExistsException extends RuntimeException {
    public AlbumAlreadyExistsException(String message){
        super(message);
    }
}
