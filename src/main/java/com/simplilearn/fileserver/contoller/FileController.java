//file controller accepts the multipart request 
package com.simplilearn.fileserver.contoller;
import java.io.File;
import java.io.FileInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.simplilearn.fileserver.exception.StorageException;
import com.simplilearn.fileserver.service.StorageService;

@Controller
public class FileController {
	
	@Autowired
	StorageService storageService; //injected a service dependancy 
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	//since we have used @Controller , we will use request maping 
	public String index() {
		return "index.html";
		//index.html fill will be loaded by the springboot application 
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST, consumes= {"multipart/form-data"})
	//post request to upload files 
	public String upload(@RequestParam MultipartFile file) {
		//logic to upload a file
		storageService.uploadFile(file);// called a method of storage service 
										// we have generated an "upload" method 
										//upload file as multipart request 
		return "redirect:/success.html"; //redirect request to return response 
										 // "redirect:/success.html" will redirect the request to appropriate page  
	}
	
	@RequestMapping(value="/download", method=RequestMethod.GET)
	public ResponseEntity<InputStreamResource> downloadFile(@RequestParam(value="filename") String filename) {
		File file = null;
		InputStreamResource resource = null;
		try {
			//read file
			file = new File(storageService.getFile(filename));
			// download resource stream
			resource = new InputStreamResource(new FileInputStream(file));
		} catch (Exception e) {
			throw new StorageException("Falied to download a file !");
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
		
		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/text")).body(resource);
	}
	
	
	@ExceptionHandler(StorageException.class)
	public String handleStorageException() {
		return "redirect:/failure.html";
	}
}
