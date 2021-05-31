package com.example.CaseStudy1.exception;

public class ItemNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private String msg;
	
	public ItemNotFoundException(String msg)
	{
		this.msg=msg;
	}
	
	public ItemNotFoundException() {
		super();
	}

	public String getMsg() {
		return msg;
	}

	@Override
	public String toString() {
		return "[" + msg + "]";
	}


    
	

}
