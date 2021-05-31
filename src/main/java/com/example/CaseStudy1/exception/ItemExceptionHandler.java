package com.example.CaseStudy1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ItemExceptionHandler {
	@ExceptionHandler(value = ItemNotFoundException.class)
	public ResponseEntity<Object> exception(ItemNotFoundException exception) {
	      return new ResponseEntity<>(exception.toString(), HttpStatus.BAD_REQUEST);
	   }
	
	@ExceptionHandler(value=ImageNotFoundException.class)
	public ResponseEntity<?> exception(ImageNotFoundException e){
		return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value=ImageUploadingException.class)
	public ResponseEntity<?> exception(ImageUploadingException e){
		return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
	}

}
