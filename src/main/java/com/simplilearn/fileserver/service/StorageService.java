package com.simplilearn.fileserver.service;
//service class to peform business operations 
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.simplilearn.fileserver.exception.StorageException;

@Service
public class StorageService 
//method to upload a file 
{
	
	@Value("${upload.path}")//"upload.path" is the key which takes input from application.property file 
	private String path;
	//path - the path we stored in the application.properties section
	//upload file logic
	public void uploadFile(MultipartFile file) 
	//taking multipart file input here 
	{
		// verify file is empty
		if(file.isEmpty()) {
			throw new StorageException("Falied to upload a file { file is empty !} ");
			// StorageException - its catched from self generated ezception class 
		}
		//here if file is not empty 
		// file upload logic
		String fileName = file.getOriginalFilename();
		//to create a new file 
		
		try {
			InputStream inStream = file.getInputStream();//reading a data as a multipart file
														 // fetched as input stream 
			Files.copy(inStream, Paths.get(path+fileName),StandardCopyOption.REPLACE_EXISTING);
			//source is "inStream" that we want to copy 
			// o/p will be path value "Paths.get(path+fileName)" , wherever we want to copy 
			// "StandardCopyOption.REPLACE_EXISTING" - it is a type of caopy we wnat to make 
		} catch (IOException e) {
			throw new StorageException("Falied to upload a file !");
		}
	}

	//download file
	public String getFile(String filename) {
		return path+filename;
	}
}
