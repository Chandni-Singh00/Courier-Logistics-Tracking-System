package com.springboot.CourierTracking.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.springboot.CourierTracking.dto.ResponseStructure;



@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleIDNF(IdNotFoundException e){
	   ResponseStructure<String> res=new ResponseStructure<>();
	   res.setStatusCode(HttpStatus.NOT_FOUND.value());
	   res.setMessage(e.getMessage());
	   res.setData("Failure");
	   return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.NOT_FOUND);
		
		
		
	}
	
	@ExceptionHandler(NoRecordAvailableException.class)
	public ResponseEntity<ResponseStructure<String>> handleNRAE(NoRecordAvailableException e){
	   ResponseStructure<String> res=new ResponseStructure<>();
	   res.setStatusCode(HttpStatus.NOT_FOUND.value());
	   res.setMessage(e.getMessage());
	   res.setData("Failure");
	   return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.NOT_FOUND);
		
		
		
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ResponseStructure<String>> handleDIVE(DataIntegrityViolationException e){
	   ResponseStructure<String> res=new ResponseStructure<>();
	   res.setStatusCode(HttpStatus.BAD_REQUEST.value());
	   res.setMessage(e.getMessage());
	   res.setData("Failure");
	   return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.BAD_REQUEST);
	
	}
	
	
	@ExceptionHandler(OperationNotAllowedException.class)
	public ResponseEntity<ResponseStructure<String>> handleONAE(OperationNotAllowedException e){
		   ResponseStructure<String> res=new ResponseStructure<>();
		   res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		   res.setMessage(e.getMessage());
		   res.setData("Failure");
		   return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.BAD_REQUEST);		
			
		}
	
	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<ResponseStructure<String>> handleIBE(InsufficientBalanceException e){
		   ResponseStructure<String> res=new ResponseStructure<>();
		   res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		   res.setMessage(e.getMessage());
		   res.setData("Failure");
		   return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.BAD_REQUEST);		
			
		}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleRNFE(ResourceNotFoundException e){
		   ResponseStructure<String> res=new ResponseStructure<>();
		   res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		   res.setMessage(e.getMessage());
		   res.setData("Failure");
		   return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.BAD_REQUEST);		
			
		}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ResponseStructure<String>> handleRNFE(IllegalArgumentException e){
		   ResponseStructure<String> res=new ResponseStructure<>();
		   res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		   res.setMessage(e.getMessage());
		   res.setData("Failure");
		   return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.BAD_REQUEST);		
			
		}
	
	
	
	

}
