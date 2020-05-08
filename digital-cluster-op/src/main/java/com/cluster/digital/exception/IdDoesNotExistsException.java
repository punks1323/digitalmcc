package com.cluster.digital.exception;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-04
 */
public class IdDoesNotExistsException extends RuntimeException {
    public IdDoesNotExistsException(String message) {
        super(message);
    }
}
