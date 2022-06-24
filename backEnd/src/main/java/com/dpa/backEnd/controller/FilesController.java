package com.dpa.backEnd.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dpa.backEnd.message.ResponseMessage;
import com.dpa.backEnd.model.Greeting;
import com.dpa.backEnd.repository.GreetingRepository;

@Controller
@CrossOrigin("http://localhost:4200/send-file")
public class FilesController {
  
  @Autowired
  GreetingRepository greetingRepository;
  

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

    String message = "";
    
    List<Greeting> greetings = parseZipFile(file);
    
    
    
    if(file.getContentType().equals("application/zip")) {
    	try {
        	greetingRepository.insert(greetings);    	    	
        	message = "Uploaded the file successfully: " + file.getOriginalFilename();
        	return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        	} catch (Exception e) {
        		message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        	}
    } else {
    	message = "File selected is not compressed!";
    	return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseMessage(message));
    }

  }

  // METHODS

  private List<Greeting> parseZipFile(MultipartFile file) throws IOException {
		List<Greeting> greetings = new ArrayList<>();
		try (BufferedReader bufferedReaderFromZipFile = getBufferedReadeFromZipFile(file)){
			bufferedReaderFromZipFile.readLine();
			bufferedReaderFromZipFile.lines().forEach(line -> greetings.add(parseGreetingLine(line)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return greetings;
  }
  
  private BufferedReader getBufferedReadeFromZipFile(MultipartFile multipartZipFile) throws Exception {
	  File file = new File(multipartZipFile.getOriginalFilename());
	  try (OutputStream os = new FileOutputStream(file)){
		  os.write(multipartZipFile.getBytes());
		  ZipFile zipFile = new ZipFile(file);
		  Enumeration<? extends ZipEntry> fileEntries = zipFile.entries();
		  if (fileEntries.hasMoreElements()) {
			  InputStream inputStream = zipFile.getInputStream(fileEntries.nextElement());
			  BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			  return bufferedReader;
		  } else {
			  throw new Exception ("File is empty");
		  }
	  } catch (Exception e) {
		  throw e;
	  }
  }
  
  private Greeting parseGreetingLine(String line) {
	  String[] tokens = line.split(";");
	  Greeting greeting = new Greeting(tokens[1]);
	  greeting.setGreeting(tokens[0]);
	  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	  greeting.setDateTime(LocalDateTime.parse(tokens[2], formatter));
	  return greeting;
  }

}

/*

– @CrossOrigin is for configuring allowed origins.
– @Controller annotation is used to define a controller.
– @GetMapping and @PostMapping annotation is for mapping HTTP GET & POST requests onto specific handler methods:

POST /upload: uploadFile()
GET /files: getListFiles()
GET /files/[filename]: getFile()
– We use @Autowired to inject implementation of FilesStorageService bean to local variable.

 */