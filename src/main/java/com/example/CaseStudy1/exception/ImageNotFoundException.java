package com.example.CaseStudy1.exception;

public class ImageNotFoundException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;
	private String msg;
	public ImageNotFoundException() {
		super();
	}
	
	public ImageNotFoundException(String msg) {
		this.msg=msg;
	}

	@Override
	public String toString() {
		return " [ " + msg + " ]";
	}
	

}
