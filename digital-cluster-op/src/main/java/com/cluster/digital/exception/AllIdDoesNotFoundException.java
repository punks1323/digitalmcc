package com.cluster.digital.exception;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-04
 */
public class AllIdDoesNotFoundException extends RuntimeException {
    public AllIdDoesNotFoundException(String message) {
        super(message);
    }
}
