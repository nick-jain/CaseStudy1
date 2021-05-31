package com.example.CaseStudy1.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.CaseStudy1.entity.Item;
import com.example.CaseStudy1.exception.ImageNotFoundException;
import com.example.CaseStudy1.exception.ImageUploadingException;
import com.example.CaseStudy1.exception.ItemNotFoundException;
import com.example.CaseStudy1.repo.ItemRepo;
import com.example.CaseStudy1.util.ImageHandler;


//import static org.sfw.hateoas.server.mvc.WebMvcLinkBuilder.*;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@RestController
public class ItemController {
	static final String uploadDir="uploads";
	
		Logger logger=LoggerFactory.getLogger(ItemController.class);
		
		
		@Autowired
		ItemRepo itemRepo;
		
		
		
		@GetMapping("/items")
		public List<Item> getAllItems(){
			List<Item> list= itemRepo.findAll();
			if(list.size()==0) {
				logger.info("No Item Present");
			}
			logger.info("Retrived  all Items Sucessfully");
			return list;
		}
		
		@GetMapping("/items/{id}")
		@ResponseBody
		public Optional<Item> getItemById(@PathVariable("id") long id) throws ItemNotFoundException{
			
			Optional<Item> item=  itemRepo.findById(id);
			if(!item.isPresent()) {
				logger.error("Item Not Found");
				throw new ItemNotFoundException("Unable to find Item with id : "+id);
			}
			
			logger.info("Item found Sucessfully ");
			return item;	
		}
		
		@DeleteMapping("/items/{id}")
		public ResponseEntity<Object> deleteItem(@PathVariable("id") long id) {
			Optional<Item> item=itemRepo.findById(id);
			if(item.isPresent()) {
					itemRepo.delete(item.get());
					logger.info("Item deleted Sucessfully");
			}
			else {
				logger.error("Item Not Found");
				throw new ItemNotFoundException("Unable to find Item with id : "+id);
			}
			
			return new ResponseEntity<>("Item Deleted Sucessfully", HttpStatus.OK);
		}
		
		@PostMapping("/items")
		public Item addItem(@RequestBody Item item) {
			itemRepo.save(item);
			logger.info("Item Added Sucessfully : "+item);
			return item;
		}
		
		@PutMapping("/items")
		public Item updateItem(@RequestBody Item item) {
			if(itemRepo.findById(item.getId()).isPresent()) {
				itemRepo.save(item);
				logger.info("Item Updated Sucessfully : "+item);
			}
			else {
				logger.error("Item Not Found");
				throw new ItemNotFoundException("Unable to find Item with id : "+item.getId());
			}
			return item;
		}
		
		@PutMapping("/item/{id}/image")
		@ResponseBody
		public ResponseEntity<?> uploadItemImage(@PathVariable("id") long id,@RequestParam("image") MultipartFile file) {
			logger.info("Uploading Image...");
			Item item;
			try {
				item=itemRepo.getById(id);
			}
			catch(Exception e) {
				logger.error("Item not found");
				throw new ItemNotFoundException("Unable to find Item with id : "+id);
			}
			
			String imageName=file.getOriginalFilename();
	//		String type=file.getContentType();	
			String dir=uploadDir + "/"+ item.getId();
			
			
			
			try {
				ImageHandler.saveImage(dir,imageName,file);
				item.setImage("/items/"+ id+"/"+imageName);
				itemRepo.save(item);
				logger.info("Uploading Image Sucessful");
			} catch (IOException e) {
				logger.error("Error in Uploading image : " +e);
				throw new ImageUploadingException(e.toString()); 
			}
			
			//return item;
			return new ResponseEntity<>("Image Uploaded Sucessfully", HttpStatus.OK);
		}
		@GetMapping(value="/items/{id}/{imageName}",produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_GIF_VALUE,MediaType.IMAGE_PNG_VALUE})
		public byte[] uploadItemImage(@PathVariable("id") long id,@PathVariable("imageName") String imageName)  {
			       
			Path path =   Paths.get(uploadDir + "/"+ id+"/"+imageName);// retrieve the image by its name
			byte []data=null;
			try {
	        	data=ImageHandler.getImage(path);
	        	logger.info("Image Retrived Sucessfully");
	        }
	        catch(IOException ioe) {
	        	logger.error("Error in retriving Image : "+ioe);
	        	throw new ImageNotFoundException("Image is Not Available" + ioe);
	        }
	        return data;
			
		}	
	
		
		
}
