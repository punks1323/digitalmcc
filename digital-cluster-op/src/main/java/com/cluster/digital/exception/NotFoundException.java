package com.cluster.digital.exception;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-04
 */
public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
