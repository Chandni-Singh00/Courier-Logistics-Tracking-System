package com.springboot.CourierTracking.exception;

public class OperationNotAllowedException  extends RuntimeException{
	public OperationNotAllowedException(String msg) {
		super(msg);
		
	}

}
