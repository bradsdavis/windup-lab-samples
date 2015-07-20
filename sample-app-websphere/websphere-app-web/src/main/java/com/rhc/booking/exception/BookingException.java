package com.rhc.booking.exception;

public class BookingException extends RuntimeException {

	public BookingException(String message) {
		super(message);
	}
	
	public BookingException(String message, Throwable t) {
		super(message, t);
	}
}
