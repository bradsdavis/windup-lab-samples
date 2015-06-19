package com.rhc.booking.services;

public class ValidationException extends RuntimeException
{

    public ValidationException(String message)
    {
        super(message);
    }
    
    public ValidationException(String message, Throwable t)
    {
        super(message, t);
    }
}
