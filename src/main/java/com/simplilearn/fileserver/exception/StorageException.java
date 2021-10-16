//this is our exception handler
package com.simplilearn.fileserver.exception;
public class StorageException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	
	public StorageException(String message) //i/p param as string message 
	{
		super(message);//initialised as "super"
	}//constructor 

	//adding "public" ensures its visibility everywhere 
	public StorageException(String message,Throwable cause) {
		super(message,cause);
	}

}
