package com.example.CaseStudy1.exception;

public class ImageUploadingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;
		public ImageUploadingException() {
			super();
			// TODO Auto-generated constructor stub
		}
		public ImageUploadingException(String message) {
			super(message);
			msg=message;
		}
		@Override
		public String toString() {
			return "[ " + msg + "]";
		}
   
}
