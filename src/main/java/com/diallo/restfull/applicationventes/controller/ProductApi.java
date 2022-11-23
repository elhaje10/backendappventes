package com.diallo.restfull.applicationventes.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.diallo.restfull.applicationventes.model.Product;
import com.diallo.restfull.applicationventes.repository.ProductRepository;
import com.diallo.restfull.applicationventes.repository.UserRepository;
import com.diallo.restfull.applicationventes.service.ProductService;


@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:4200")
public class ProductApi {
	
	@Autowired ProductRepository productrepository;
	@Autowired UserRepository userrepository;
	@Autowired ProductService productservice;
	
	
	@GetMapping(path = "/products")
	public  List<Product> list(){
		return productrepository.findAll();
	}
	
	@GetMapping(path = "/users/{userId}/products")
	public  List<Product> list(@PathVariable (value = "userId") Integer userId){
		return productrepository.findByUserId(userId);
	}
	
	 @PutMapping("/users/{userId}/products/{productId}")
	    public Product updateProduct(@PathVariable (value = "userId") Integer userId,
	    		@PathVariable (value = "productId") Integer productId,
	    		@RequestBody @Valid Product product) {
	      if(!userrepository.existsById(userId)) {
	            throw new ResourceNotFoundException("User ID " + userId + " not found");
	        }
	      return productrepository.findById(productId).map(prod -> {
	    	  prod.setName(product.getName());
	    	  prod.setPrice(product.getPrice());
	    	  prod.setProducttype(product.getProducttype());
	    	  prod.setQtt(product.getQtt());
	            return productrepository.save(prod);
	        }).orElseThrow(() -> new ResourceNotFoundException("productId " + productId + "not found"));
		 
	 }
	    
	@GetMapping(path = "/products/{productId}")
		public Product getProduct( @PathVariable Integer productId){
			return productrepository.findById(productId).get();
			
		} 	      
	    	
	//@RequestMapping(value = "/users/{userId}/products", method = RequestMethod.POST)//@RequestParam("file") MultipartFile file
	@PostMapping(path="/users/{userId}/addproducts")
	public ResponseEntity<Product> createProduct (@PathVariable (value = "userId") Integer userId, @RequestBody @Valid Product product){
		//Product savep = productservice.saveProduct(product, file.getInputStream());	
		return userrepository.findById(userId).map( user -> {
			product.setUser(user);
			Product savedProduct = productrepository.save(product);
			URI productURI = URI.create("/products/" + savedProduct.getId());
	        return ResponseEntity.created(productURI).body(savedProduct);
		}).orElseThrow(() -> new ResourceNotFoundException("UserId " + userId + " not found"));
	}
	
	
	 @DeleteMapping("/users/{userId}/products/{productId}")
	    public ResponseEntity<?> deleteProduct(@PathVariable (value = "userId") Integer userId,
	    		@PathVariable (value = "productId") Integer productId) {
	      return productrepository.findByIdAndUserId(productId, userId).map(prod -> {
	    	  	productrepository.delete(prod);
	           return ResponseEntity.ok().build();
	        }).orElseThrow(() -> new ResourceNotFoundException("productId " + productId + "not found"));
		 
	 }

}
