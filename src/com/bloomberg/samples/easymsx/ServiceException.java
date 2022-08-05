package com.bloomberg.samples.easymsx;

@SuppressWarnings("serial")
public class ServiceException extends Exception {
    public ServiceException(String message) {
        super(message);
    }
}